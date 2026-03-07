package pt.agap2.ordermanager.order.dto;

import pt.agap2.ordermanager.order.entity.OrderStatus;

public class OrderCompletionResponseDTO {

	private Long orderId;
	private Integer quantity;
	private Integer fulfilledQuantity;
	private Integer remainingQuantity;
	private boolean completed;
	private Double completionPercentage;
	private OrderStatus status;

	public OrderCompletionResponseDTO(
			Long orderId,
			Integer quantity,
			Integer fulfilledQuantity,
			Integer remainingQuantity,
			boolean completed,
			Double completionPercentage,
			OrderStatus status) {
		this.orderId = orderId;
		this.quantity = quantity;
		this.fulfilledQuantity = fulfilledQuantity;
		this.remainingQuantity = remainingQuantity;
		this.completed = completed;
		this.completionPercentage = completionPercentage;
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getFulfilledQuantity() {
		return fulfilledQuantity;
	}

	public Integer getRemainingQuantity() {
		return remainingQuantity;
	}

	public boolean isCompleted() {
		return completed;
	}

	public Double getCompletionPercentage() {
		return completionPercentage;
	}

	public OrderStatus getStatus() {
		return status;
	}
}