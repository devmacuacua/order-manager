package pt.agap2.ordermanager.order.repository;

import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;

public interface IOrderRepository {

	void persist(EntityManager em, OrderEntity entity);

	OrderEntity findById(EntityManager em, Long id);

	List<OrderEntity> findAll(EntityManager em);
}