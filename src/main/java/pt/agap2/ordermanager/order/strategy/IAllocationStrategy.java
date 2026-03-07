package pt.agap2.ordermanager.order.strategy;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public interface IAllocationStrategy {

	List<AllocationDecision> allocateOrder(
			EntityManager em,
			OrderEntity order,
			List<StockMovementEntity> stockMovements
	);

	List<AllocationDecision> allocateMovement(
			EntityManager em,
			StockMovementEntity movement,
			List<OrderEntity> pendingOrders
	);
}