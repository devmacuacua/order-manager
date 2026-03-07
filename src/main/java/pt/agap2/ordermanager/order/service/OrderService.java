package pt.agap2.ordermanager.order.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.order.dto.OrderCompletionResponseDTO;
import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.order.repository.IOrderRepository;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.shared.Jpa;
import pt.agap2.ordermanager.shared.Log;
import pt.agap2.ordermanager.user.entity.UserEntity;

public class OrderService implements IOrderService {

	private static final Logger logger = Log.getLogger(OrderService.class);

	private final IOrderRepository repository;
	private final IOrderFulfillmentService fulfillmentService;
	private final IOrderStockMovementRepository trackingRepository;

	public OrderService(
			IOrderRepository repository,
			IOrderFulfillmentService fulfillmentService,
			IOrderStockMovementRepository trackingRepository) {
		this.repository = repository;
		this.fulfillmentService = fulfillmentService;
		this.trackingRepository = trackingRepository;
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
			order.setFulfilledQuantity(0);
			order.setCreationDate(LocalDateTime.now());

			repository.persist(em, order);

			fulfillmentService.fulfillOrder(em, order);

			logger.info(
					"ORDER_CREATED id={} userId={} itemId={} quantity={}",
					order.getId(),
					user.getId(),
					item.getId(),
					order.getQuantity()
			);

			em.getTransaction().commit();
			return order;

		} catch (Exception e) {
			logger.error("ERROR_CREATING_ORDER userId={} itemId={} quantity={}", userId, itemId, quantity, e);
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

	@Override
	public OrderCompletionResponseDTO getCompletion(Long orderId) {
		EntityManager em = Jpa.em();
		try {
			OrderEntity order = repository.findById(em, orderId);
			if (order == null) {
				return null;
			}

			int quantity = order.getQuantity();
			int fulfilled = order.getFulfilledQuantity();
			int remaining = quantity - fulfilled;
			boolean completed = fulfilled >= quantity;
			double percentage = quantity == 0 ? 0.0 : (fulfilled * 100.0) / quantity;

			return new OrderCompletionResponseDTO(
					order.getId(),
					quantity,
					fulfilled,
					remaining,
					completed,
					percentage
			);
		} finally {
			em.close();
		}
	}

	@Override
	public List<OrderStockMovementEntity> getAllocations(Long orderId) {
		EntityManager em = Jpa.em();
		try {
			return trackingRepository.findByOrderId(em, orderId);
		} finally {
			em.close();
		}
	}
}