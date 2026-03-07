package pt.agap2.ordermanager.order.dto;

public class OrderRequestDTO {

	private Long userId;
	private Long itemId;
	private Integer quantity;

	public Long getUserId() {
		return userId;
	}

	public Long getItemId() {
		return itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}
}