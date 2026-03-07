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
}