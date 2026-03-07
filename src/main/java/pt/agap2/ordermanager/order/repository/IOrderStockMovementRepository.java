package pt.agap2.ordermanager.order.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public interface IOrderStockMovementRepository {

	void persist(EntityManager em, OrderStockMovementEntity entity);

	List<OrderStockMovementEntity> findByOrderId(EntityManager em, Long orderId);

	List<OrderStockMovementEntity> findByStockMovementId(EntityManager em, Long stockMovementId);

	Integer sumUsedByStockMovement(EntityManager em, StockMovementEntity stockMovement);
}