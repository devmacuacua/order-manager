package pt.agap2.ordermanager.order.mapper;

import pt.agap2.ordermanager.order.dto.OrderResponseDTO;
import pt.agap2.ordermanager.order.entity.OrderEntity;

public class OrderMapper {

	public static OrderResponseDTO toResponse(OrderEntity entity) {
		return new OrderResponseDTO(
				entity.getId(),
				entity.getUser().getId(),
				entity.getItem().getId(),
				entity.getQuantity(),
				entity.getFulfilledQuantity(),
				entity.isCompleted(),
				entity.status(),
				entity.getCreationDate()
		);
	}
}