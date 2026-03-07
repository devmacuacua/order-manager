package pt.agap2.ordermanager.stock.service;

import java.util.List;

import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public interface IStockMovementService {

	StockMovementEntity create(Long itemId, Integer quantity);

	List<StockMovementEntity> list();

	StockMovementEntity get(Long id);

	boolean delete(Long id);
	
	List<OrderStockMovementEntity> getAllocations(Long stockMovementId);
}