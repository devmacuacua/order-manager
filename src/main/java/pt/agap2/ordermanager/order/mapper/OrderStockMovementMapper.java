package pt.agap2.ordermanager.order.mapper;

import pt.agap2.ordermanager.order.dto.OrderStockMovementResponseDTO;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;

public class OrderStockMovementMapper {

	public static OrderStockMovementResponseDTO toResponse(OrderStockMovementEntity entity) {
		return new OrderStockMovementResponseDTO(
				entity.getOrder().getId(),
				entity.getStockMovement().getId(),
				entity.getQuantityUsed()
		);
	}
}