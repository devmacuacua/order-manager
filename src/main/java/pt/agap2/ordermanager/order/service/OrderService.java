package pt.agap2.ordermanager.order.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.repository.IOrderRepository;
import pt.agap2.ordermanager.shared.Jpa;
import pt.agap2.ordermanager.user.entity.UserEntity;

public class OrderService implements IOrderService {

	private final IOrderRepository repository;

	public OrderService(IOrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public OrderEntity create(Long userId, Long itemId, Integer quantity) {

		EntityManager em = Jpa.em();

		try {
			em.getTransaction().begin();

			UserEntity user = em.find(UserEntity.class, userId);
			ItemEntity item = em.find(ItemEntity.class, itemId);

			if (user == null || item == null) {
				em.getTransaction().rollback();
				return null;
			}

			OrderEntity order = new OrderEntity();
			order.setUser(user);
			order.setItem(item);
			order.setQuantity(quantity);
			order.setCreationDate(LocalDateTime.now());

			repository.persist(em, order);

			em.getTransaction().commit();

			return order;

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			throw e;

		} finally {
			em.close();
		}
	}

	@Override
	public List<OrderEntity> list() {

		EntityManager em = Jpa.em();

		try {
			return repository.findAll(em);
		} finally {
			em.close();
		}
	}

	@Override
	public OrderEntity get(Long id) {

		EntityManager em = Jpa.em();

		try {
			return repository.findById(em, id);
		} finally {
			em.close();
		}
	}
}