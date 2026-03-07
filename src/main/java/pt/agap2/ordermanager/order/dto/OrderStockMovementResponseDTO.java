package pt.agap2.ordermanager.order.dto;

public class OrderStockMovementResponseDTO {

	private Long orderId;
	private Long stockMovementId;
	private Integer quantityUsed;

	public OrderStockMovementResponseDTO() {
	}

	public OrderStockMovementResponseDTO(Long orderId, Long stockMovementId, Integer quantityUsed) {
		this.orderId = orderId;
		this.stockMovementId = stockMovementId;
		this.quantityUsed = quantityUsed;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Long getStockMovementId() {
		return stockMovementId;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}
}