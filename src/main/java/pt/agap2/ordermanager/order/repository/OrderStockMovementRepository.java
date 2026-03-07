package pt.agap2.ordermanager.order.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public class OrderStockMovementRepository implements IOrderStockMovementRepository {

	@Override
	public void persist(EntityManager em, OrderStockMovementEntity entity) {
		em.persist(entity);
	}

	@Override
	public List<OrderStockMovementEntity> findByOrderId(EntityManager em, Long orderId) {
		return em.createQuery(
				"SELECT osm FROM OrderStockMovementEntity osm WHERE osm.order.id = :orderId",
				OrderStockMovementEntity.class)
			.setParameter("orderId", orderId)
			.getResultList();
	}

	@Override
	public List<OrderStockMovementEntity> findByStockMovementId(EntityManager em, Long stockMovementId) {
		return em.createQuery(
				"SELECT osm FROM OrderStockMovementEntity osm WHERE osm.stockMovement.id = :stockMovementId",
				OrderStockMovementEntity.class)
			.setParameter("stockMovementId", stockMovementId)
			.getResultList();
	}

	@Override
	public Integer sumUsedByStockMovement(EntityManager em, StockMovementEntity stockMovement) {
		Long result = em.createQuery(
				"SELECT COALESCE(SUM(osm.quantityUsed), 0) FROM OrderStockMovementEntity osm WHERE osm.stockMovement = :stockMovement",
				Long.class)
			.setParameter("stockMovement", stockMovement)
			.getSingleResult();

		return result.intValue();
	}
}