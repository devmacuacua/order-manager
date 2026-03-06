package pt.agap2.ordermanager.item.repository;

import java.util.List;
import javax.persistence.EntityManager;
import pt.agap2.ordermanager.item.entity.ItemEntity;

public interface IItemRepository {

	void persist(EntityManager em, ItemEntity item);

	ItemEntity findById(EntityManager em, Long id);

	List<ItemEntity> findAll(EntityManager em);

	void remove(EntityManager em, ItemEntity item);
}