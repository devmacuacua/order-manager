package pt.agap2.ordermanager.item.repository;

import java.util.List;
import javax.persistence.EntityManager;
import pt.agap2.ordermanager.item.entity.ItemEntity;

public class ItemRepository implements IItemRepository {

	@Override
	public void persist(EntityManager em, ItemEntity item) {
		em.persist(item);
	}

	@Override
	public ItemEntity findById(EntityManager em, Long id) {
		return em.find(ItemEntity.class, id);
	}

	@Override
	public List<ItemEntity> findAll(EntityManager em) {
		return em.createQuery("SELECT i FROM ItemEntity i ORDER BY i.id DESC", ItemEntity.class).getResultList();
	}

	@Override
	public void remove(EntityManager em, ItemEntity item) {
		em.remove(item);
	}
}