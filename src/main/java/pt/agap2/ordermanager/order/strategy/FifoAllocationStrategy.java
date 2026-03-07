package pt.agap2.ordermanager.order.strategy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.shared.domain.valueobject.Quantity;
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
		Quantity missing = order.remainingQuantityValue();

		if (!missing.isPositive()) {
			return decisions;
		}

		for (StockMovementEntity movement : stockMovements) {
			if (!missing.isPositive()) {
				break;
			}

			int alreadyUsed = trackingRepository.sumUsedByStockMovement(em, movement);

			if (!movement.hasAvailableQuantity(alreadyUsed)) {
				continue;
			}

			Quantity available = movement.availableQuantityValue(alreadyUsed);
			Quantity usedNow = available.min(missing);

			decisions.add(new AllocationDecision(order, movement, usedNow.value()));
			missing = missing.subtract(usedNow);
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

			if (!movement.hasAvailableQuantity(alreadyUsed)) {
				break;
			}

			Quantity missing = order.remainingQuantityValue();
			if (!missing.isPositive()) {
				continue;
			}

			Quantity available = movement.availableQuantityValue(alreadyUsed);
			Quantity usedNow = available.min(missing);

			decisions.add(new AllocationDecision(order, movement, usedNow.value()));
		}

		return decisions;
	}
}