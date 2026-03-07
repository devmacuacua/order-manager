package pt.agap2.ordermanager.stock.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.order.service.IOrderFulfillmentService;
import pt.agap2.ordermanager.shared.Jpa;
import pt.agap2.ordermanager.shared.Log;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;
import pt.agap2.ordermanager.stock.mapper.StockMovementMapper;
import pt.agap2.ordermanager.stock.repository.IStockMovementRepository;

public class StockMovementService implements IStockMovementService {

	private static final Logger logger = Log.getLogger(StockMovementService.class);

	private final IStockMovementRepository repository;
	private final IOrderFulfillmentService fulfillmentService;
	private final IOrderStockMovementRepository trackingRepository;

	public StockMovementService(
			IStockMovementRepository repository,
			IOrderFulfillmentService fulfillmentService,
			IOrderStockMovementRepository trackingRepository) {
		this.repository = repository;
		this.fulfillmentService = fulfillmentService;
		this.trackingRepository = trackingRepository;
	}

	@Override
	public StockMovementEntity create(Long itemId, Integer quantity) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();

			ItemEntity item = em.find(ItemEntity.class, itemId);
			if (item == null) {
				em.getTransaction().rollback();
				return null;
			}

			StockMovementEntity entity = StockMovementMapper.toEntity(item, quantity);
			entity.setCreationDate(LocalDateTime.now());

			repository.persist(em, entity);

			fulfillmentService.fulfillPendingOrdersWithMovement(em, entity);

			logger.info(
					"STOCK_MOVEMENT_CREATED id={} itemId={} quantity={}",
					entity.getId(),
					entity.getItem().getId(),
					entity.getQuantity()
			);

			em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			logger.error("ERROR_CREATING_STOCK_MOVEMENT itemId={} quantity={}", itemId, quantity, e);
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public List<StockMovementEntity> list() {
		EntityManager em = Jpa.em();
		try {
			return repository.findAll(em);
		} finally {
			em.close();
		}
	}

	@Override
	public StockMovementEntity get(Long id) {
		EntityManager em = Jpa.em();
		try {
			return repository.findById(em, id);
		} finally {
			em.close();
		}
	}

	@Override
	public boolean delete(Long id) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();

			StockMovementEntity entity = repository.findById(em, id);
			if (entity == null) {
				em.getTransaction().rollback();
				return false;
			}

			repository.remove(em, entity);

			em.getTransaction().commit();
			return true;
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
	public List<OrderStockMovementEntity> getAllocations(Long stockMovementId) {
		EntityManager em = Jpa.em();
		try {
			return trackingRepository.findByStockMovementId(em, stockMovementId);
		} finally {
			em.close();
		}
	}
}