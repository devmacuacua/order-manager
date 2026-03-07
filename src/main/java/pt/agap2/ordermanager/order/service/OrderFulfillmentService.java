package pt.agap2.ordermanager.order.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.order.repository.IOrderRepository;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.shared.EmailService;
import pt.agap2.ordermanager.shared.Log;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;
import pt.agap2.ordermanager.stock.repository.IStockMovementRepository;

public class OrderFulfillmentService implements IOrderFulfillmentService {

	private final IOrderRepository orderRepository;
	private final IStockMovementRepository stockRepository;
	private final IOrderStockMovementRepository trackingRepository;

	private static final Logger logger = Log.getLogger(OrderFulfillmentService.class);
	private final EmailService emailService = new EmailService();

	public OrderFulfillmentService(IOrderRepository orderRepository, IStockMovementRepository stockRepository,
			IOrderStockMovementRepository trackingRepository) {
		this.orderRepository = orderRepository;
		this.stockRepository = stockRepository;
		this.trackingRepository = trackingRepository;
	}

	@Override
	public void fulfillOrder(EntityManager em, OrderEntity order) {
		int missing = order.getQuantity() - order.getFulfilledQuantity();
		if (missing <= 0) {
			return;
		}

		List<StockMovementEntity> movements = stockRepository.findByItem(em, order.getItem().getId());

		for (StockMovementEntity movement : movements) {
			if (missing <= 0) {
				break;
			}

			int alreadyUsed = trackingRepository.sumUsedByStockMovement(em, movement);
			int available = movement.getQuantity() - alreadyUsed;

			if (available <= 0) {
				continue;
			}

			int usedNow = Math.min(available, missing);

			OrderStockMovementEntity tracking = new OrderStockMovementEntity();
			tracking.setOrder(order);
			tracking.setStockMovement(movement);
			tracking.setQuantityUsed(usedNow);
			trackingRepository.persist(em, tracking);

			logger.info("STOCK_ALLOCATED orderId={} stockMovementId={} quantityUsed={}", order.getId(),
					movement.getId(), usedNow);

			boolean completedBefore = order.isCompleted();

			order.setFulfilledQuantity(order.getFulfilledQuantity() + usedNow);
			missing -= usedNow;

			boolean completedAfter = order.isCompleted();

			if (!completedBefore && completedAfter) {
				logger.info("ORDER_COMPLETED id={} userId={} itemId={} quantity={} fulfilledQuantity={}", order.getId(),
						order.getUser().getId(), order.getItem().getId(), order.getQuantity(),
						order.getFulfilledQuantity());

				emailService.sendOrderCompletedEmail(order.getUser().getEmail(), order.getId());
			}
		}
	}

	@Override
	public void fulfillPendingOrdersWithMovement(EntityManager em, StockMovementEntity movement) {
		List<OrderEntity> pendingOrders = orderRepository.findPendingByItem(em, movement.getItem().getId());

		for (OrderEntity order : pendingOrders) {
			int alreadyUsed = trackingRepository.sumUsedByStockMovement(em, movement);
			int available = movement.getQuantity() - alreadyUsed;

			if (available <= 0) {
				break;
			}

			int missing = order.getQuantity() - order.getFulfilledQuantity();
			if (missing <= 0) {
				continue;
			}

			int usedNow = Math.min(available, missing);

			OrderStockMovementEntity tracking = new OrderStockMovementEntity();
			tracking.setOrder(order);
			tracking.setStockMovement(movement);
			tracking.setQuantityUsed(usedNow);
			trackingRepository.persist(em, tracking);

			logger.info("STOCK_ALLOCATED orderId={} stockMovementId={} quantityUsed={}", order.getId(),
					movement.getId(), usedNow);

			boolean completedBefore = order.isCompleted();

			order.setFulfilledQuantity(order.getFulfilledQuantity() + usedNow);

			boolean completedAfter = order.isCompleted();

			if (!completedBefore && completedAfter) {
				logger.info("ORDER_COMPLETED id={} userId={} itemId={} quantity={} fulfilledQuantity={}", order.getId(),
						order.getUser().getId(), order.getItem().getId(), order.getQuantity(),
						order.getFulfilledQuantity());

				emailService.sendOrderCompletedEmail(order.getUser().getEmail(), order.getId());
			}
		}
	}
}