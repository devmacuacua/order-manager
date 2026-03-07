package pt.agap2.ordermanager.order.strategy;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public class AllocationDecision {

	private final OrderEntity order;
	private final StockMovementEntity stockMovement;
	private final int quantityUsed;

	public AllocationDecision(OrderEntity order, StockMovementEntity stockMovement, int quantityUsed) {
		this.order = order;
		this.stockMovement = stockMovement;
		this.quantityUsed = quantityUsed;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public StockMovementEntity getStockMovement() {
		return stockMovement;
	}

	public int getQuantityUsed() {
		return quantityUsed;
	}
}