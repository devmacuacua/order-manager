package pt.agap2.ordermanager.stock.mapper;

import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.stock.dto.StockMovementResponseDTO;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public final class StockMovementMapper {

	private StockMovementMapper() {
	}

	public static StockMovementEntity toEntity(ItemEntity item, Integer quantity) {
		StockMovementEntity entity = new StockMovementEntity();
		entity.setItem(item);
		entity.setQuantity(quantity);
		return entity;
	}

	public static StockMovementResponseDTO toResponse(StockMovementEntity entity) {
		return new StockMovementResponseDTO(
				entity.getId(),
				entity.getItem().getId(),
				entity.getItem().getName(),
				entity.getQuantity(),
				entity.getCreationDate()
		);
	}
}