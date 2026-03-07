package pt.agap2.ordermanager.order.service;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.order.repository.IOrderRepository;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;
import pt.agap2.ordermanager.stock.repository.IStockMovementRepository;

public class OrderFulfillmentService implements IOrderFulfillmentService {

	private final IOrderRepository orderRepository;
	private final IStockMovementRepository stockRepository;
	private final IOrderStockMovementRepository trackingRepository;

	public OrderFulfillmentService(
			IOrderRepository orderRepository,
			IStockMovementRepository stockRepository,
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

			order.setFulfilledQuantity(order.getFulfilledQuantity() + usedNow);
			missing -= usedNow;
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

			order.setFulfilledQuantity(order.getFulfilledQuantity() + usedNow);
		}
	}
}