
package pt.agap2.ordermanager.stock.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public interface IStockMovementRepository {

	void persist(EntityManager em, StockMovementEntity entity);

	StockMovementEntity findById(EntityManager em, Long id);

	List<StockMovementEntity> findAll(EntityManager em);

	void remove(EntityManager em, StockMovementEntity entity);
	
	List<StockMovementEntity> findByItem(EntityManager em, Long itemId);
	
}