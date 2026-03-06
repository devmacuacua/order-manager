package pt.agap2.ordermanager.item.service;

import java.util.List;
import javax.persistence.EntityManager;

import pt.agap2.ordermanager.item.dto.ItemRequestDTO;
import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.item.mapper.ItemMapper;
import pt.agap2.ordermanager.item.repository.IItemRepository;
import pt.agap2.ordermanager.shared.Jpa;

public class ItemService implements IItemService {

	private final IItemRepository repository;

	public ItemService(IItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public ItemEntity create(ItemEntity item) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();
			repository.persist(em, item);
			em.getTransaction().commit();
			return item;
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
	public List<ItemEntity> list() {
		EntityManager em = Jpa.em();
		try {
			return repository.findAll(em);
		} finally {
			em.close();
		}
	}

	@Override
	public ItemEntity get(Long id) {
		EntityManager em = Jpa.em();
		try {
			return repository.findById(em, id);
		} finally {
			em.close();
		}
	}

	@Override
	public ItemEntity update(Long id, ItemRequestDTO dto) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();

			ItemEntity existing = repository.findById(em, id);
			if (existing == null) {
				em.getTransaction().rollback();
				return null;
			}

			ItemMapper.copyToEntity(dto, existing);

			em.getTransaction().commit();
			return existing;
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
	public boolean delete(Long id) {
		EntityManager em = Jpa.em();
		try {
			em.getTransaction().begin();

			ItemEntity existing = repository.findById(em, id);
			if (existing == null) {
				em.getTransaction().rollback();
				return false;
			}

			repository.remove(em, existing);

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
}