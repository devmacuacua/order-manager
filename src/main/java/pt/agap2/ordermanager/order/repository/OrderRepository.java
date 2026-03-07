package pt.agap2.ordermanager.order.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;

public class OrderRepository implements IOrderRepository {

	@Override
	public void persist(EntityManager em, OrderEntity entity) {
		em.persist(entity);
	}

	@Override
	public OrderEntity findById(EntityManager em, Long id) {
		return em.find(OrderEntity.class, id);
	}

	@Override
	public List<OrderEntity> findAll(EntityManager em) {
		return em.createQuery(
			"SELECT o FROM OrderEntity o ORDER BY o.creationDate DESC",
			OrderEntity.class
		).getResultList();
	}
	
	@Override
	public List<OrderEntity> findPendingByItem(EntityManager em, Long itemId) {
		return em.createQuery(
				"SELECT o FROM OrderEntity o " +
				"WHERE o.item.id = :itemId AND o.fulfilledQuantity < o.quantity " +
				"ORDER BY o.creationDate ASC",
				OrderEntity.class)
			.setParameter("itemId", itemId)
			.getResultList();
	}
}