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
		return em.createQuery(IOrderQueries.GET_ORDER_ORDER_BY_O_CREATION_DATE_DESC, OrderEntity.class).getResultList();
	}

	@Override
	public List<OrderEntity> findPendingByItem(EntityManager em, Long itemId) {
		return em.createQuery(IOrderQueries.GET_ORDERS_BY_ITEM_AND_QUANTITY, OrderEntity.class)
				.setParameter("itemId", itemId).getResultList();
	}
}