package pt.agap2.ordermanager.order.strategy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public class FifoAllocationStrategy implements IAllocationStrategy {

	private final IOrderStockMovementRepository trackingRepository;

	public FifoAllocationStrategy(IOrderStockMovementRepository trackingRepository) {
		this.trackingRepository = trackingRepository;
	}

	@Override
	public List<AllocationDecision> allocateOrder(
			EntityManager em,
			OrderEntity order,
			List<StockMovementEntity> stockMovements) {

		List<AllocationDecision> decisions = new ArrayList<>();
		int missing = order.getQuantity() - order.getFulfilledQuantity();

		if (missing <= 0) {
			return decisions;
		}

		for (StockMovementEntity movement : stockMovements) {
			if (missing <= 0) {
				break;
			}

			int alreadyUsed = trackingRepository.sumUsedByStockMovement(em, movement);
			int available = movement.getQuantity() - alreadyUsed;

			if (available <= 0) {
				continue;
			}

			int usedNow = Math.min(available, missing);

			decisions.add(new AllocationDecision(order, movement, usedNow));
			missing -= usedNow;
		}

		return decisions;
	}

	@Override
	public List<AllocationDecision> allocateMovement(
			EntityManager em,
			StockMovementEntity movement,
			List<OrderEntity> pendingOrders) {

		List<AllocationDecision> decisions = new ArrayList<>();

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

			decisions.add(new AllocationDecision(order, movement, usedNow));
		}

		return decisions;
	}
}