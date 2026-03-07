package pt.agap2.ordermanager.stock.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public class StockMovementRepository implements IStockMovementRepository {

	@Override
	public void persist(EntityManager em, StockMovementEntity entity) {
		em.persist(entity);
	}

	@Override
	public StockMovementEntity findById(EntityManager em, Long id) {
		return em.find(StockMovementEntity.class, id);
	}

	@Override
	public List<StockMovementEntity> findAll(EntityManager em) {
		return em.createQuery(IStockMovementQueries.GET_STOCK_MOVEMENTS_ORDER_BY_CREATION_DATE_DESC,
				StockMovementEntity.class).getResultList();
	}

	@Override
	public void remove(EntityManager em, StockMovementEntity entity) {
		em.remove(entity);
	}

	@Override
	public List<StockMovementEntity> findByItem(EntityManager em, Long itemId) {
		return em.createQuery(IStockMovementQueries.GET_STOCK_MOVEMENTS_BY_ITEM_ID_ORDER, StockMovementEntity.class)
				.setParameter("itemId", itemId).getResultList();
	}
}