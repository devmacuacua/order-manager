package pt.agap2.ordermanager.item.mapper;

import pt.agap2.ordermanager.item.dto.ItemRequestDTO;
import pt.agap2.ordermanager.item.dto.ItemResponseDTO;
import pt.agap2.ordermanager.item.entity.ItemEntity;

public final class ItemMapper {

	private ItemMapper() {
	}

	public static ItemEntity toEntity(ItemRequestDTO dto) {
		ItemEntity entity = new ItemEntity();
		entity.setName(dto.getName());
		return entity;
	}

	public static void copyToEntity(ItemRequestDTO dto, ItemEntity entity) {
		entity.setName(dto.getName());
	}

	public static ItemResponseDTO toResponse(ItemEntity entity) {
		return new ItemResponseDTO(entity.getId(), entity.getName());
	}
}