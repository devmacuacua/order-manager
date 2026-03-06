package pt.agap2.ordermanager.item.service;

import java.util.List;
import pt.agap2.ordermanager.item.dto.ItemRequestDTO;
import pt.agap2.ordermanager.item.entity.ItemEntity;

public interface IItemService {

	ItemEntity create(ItemEntity item);

	List<ItemEntity> list();

	ItemEntity get(Long id);

	ItemEntity update(Long id, ItemRequestDTO dto);

	boolean delete(Long id);
}