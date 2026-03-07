package pt.agap2.ordermanager.order.service;

import java.util.List;

import pt.agap2.ordermanager.order.dto.OrderCompletionResponseDTO;
import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.entity.OrderStockMovementEntity;

public interface IOrderService {

	OrderEntity create(Long userId, Long itemId, Integer quantity);

	List<OrderEntity> list();

	OrderEntity get(Long id);
	
	OrderCompletionResponseDTO getCompletion(Long orderId);

	List<OrderStockMovementEntity> getAllocations(Long orderId);
}