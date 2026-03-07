package pt.agap2.ordermanager.stock.dto;

import java.time.LocalDateTime;

public class StockMovementResponseDTO {

	private Long id;
	private Long itemId;
	private String itemName;
	private Integer quantity;
	private LocalDateTime creationDate;

	public StockMovementResponseDTO() {
	}

	public StockMovementResponseDTO(Long id, Long itemId, String itemName, Integer quantity, LocalDateTime creationDate) {
		this.id = id;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}
}