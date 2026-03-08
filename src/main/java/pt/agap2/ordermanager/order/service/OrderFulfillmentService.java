package pt.agap2.ordermanager.order.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.order.repository.IOrderRepository;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.order.strategy.AllocationDecision;
import pt.agap2.ordermanager.order.strategy.IAllocationStrategy;
import pt.agap2.ordermanager.shared.domain.exception.InvalidOrderAllocationException;
import pt.agap2.ordermanager.shared.domain.exception.StockMovementNotAllowedException;
import pt.agap2.ordermanager.shared.infrastructure.IEmailService;
import pt.agap2.ordermanager.shared.infrastructure.Log;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;
import pt.agap2.ordermanager.stock.repository.IStockMovementRepository;

public class OrderFulfillmentService implements IOrderFulfillmentService {

    private final IOrderRepository orderRepository;
    private final IStockMovementRepository stockRepository;
    private final IOrderStockMovementRepository trackingRepository;
    private final IEmailService emailService;
    private final IAllocationStrategy allocationStrategy;

    private static final Logger logger = Log.getLogger(OrderFulfillmentService.class);

    public OrderFulfillmentService(
            IOrderRepository orderRepository,
            IStockMovementRepository stockRepository,
            IOrderStockMovementRepository trackingRepository,
            IEmailService emailService,
            IAllocationStrategy allocationStrategy) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
        this.trackingRepository = trackingRepository;
        this.emailService = emailService;
        this.allocationStrategy = allocationStrategy;
    }

    @Override
    public void fulfillOrder(EntityManager em, OrderEntity order) {
        List<StockMovementEntity> movements = stockRepository.findByItem(em, order.getItem().getId());
        List<AllocationDecision> decisions = allocationStrategy.allocateOrder(em, order, movements);

        for (AllocationDecision decision : decisions) {
            applyAllocation(em, decision);
        }
    }

    @Override
    public void fulfillPendingOrdersWithMovement(EntityManager em, StockMovementEntity movement) {
        List<OrderEntity> pendingOrders = orderRepository.findPendingByItem(em, movement.getItem().getId());
        List<AllocationDecision> decisions = allocationStrategy.allocateMovement(em, movement, pendingOrders);

        for (AllocationDecision decision : decisions) {
            applyAllocation(em, decision);
        }
    }

    private void applyAllocation(EntityManager em, AllocationDecision decision) {
        OrderEntity order = decision.getOrder();
        StockMovementEntity movement = decision.getStockMovement();
        int usedNow = decision.getQuantityUsed();

        validateDecision(em, order, movement, usedNow);

        boolean completedBefore = order.isCompleted();

        order.allocate(usedNow);

        OrderStockMovementEntity tracking = new OrderStockMovementEntity();
        tracking.setOrder(order);
        tracking.setStockMovement(movement);
        tracking.setQuantityUsed(usedNow);
        trackingRepository.persist(em, tracking);

        logger.info(
            "STOCK_ALLOCATED orderId={} stockMovementId={} quantityUsed={}",
            order.getId(),
            movement.getId(),
            usedNow
        );

        boolean completedAfter = order.isCompleted();

        if (!completedBefore && completedAfter) {
            logger.info(
                "ORDER_COMPLETED id={} userId={} itemId={} quantity={} fulfilledQuantity={}",
                order.getId(),
                order.getUser().getId(),
                order.getItem().getId(),
                order.getQuantity(),
                order.getFulfilledQuantity()
            );

            emailService.sendOrderCompletedEmail(order.getUser().getEmail(), order.getId());
        }
    }

    private void validateDecision(EntityManager em, OrderEntity order, StockMovementEntity movement, int usedNow) {
        if (usedNow <= 0) {
            throw new InvalidOrderAllocationException(usedNow, order.remainingQuantity());
        }

        int orderRemaining = order.remainingQuantity();
        if (usedNow > orderRemaining) {
            throw new InvalidOrderAllocationException(usedNow, orderRemaining);
        }

        int movementAvailable = calculateMovementAvailableQuantity(em, movement);
        if (usedNow > movementAvailable) {
            throw new StockMovementNotAllowedException(
                "Used quantity cannot be greater than available stock movement quantity"
            );
        }
    }

    private int calculateMovementAvailableQuantity(EntityManager em, StockMovementEntity movement) {
        List<OrderStockMovementEntity> trackings = trackingRepository.findByStockMovementId(em, movement.getId());

        int alreadyUsed = trackings.stream()
                .mapToInt(OrderStockMovementEntity::getQuantityUsed)
                .sum();

        return movement.getQuantity() - alreadyUsed;
    }
}