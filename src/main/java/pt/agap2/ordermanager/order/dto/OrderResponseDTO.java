package pt.agap2.ordermanager.order.dto;

import java.time.LocalDateTime;

public class OrderResponseDTO {

	private Long id;
	private Long userId;
	private Long itemId;
	private Integer quantity;
	private Integer fulfilledQuantity;
	private boolean completed;
	private LocalDateTime creationDate;

	public OrderResponseDTO(
			Long id,
			Long userId,
			Long itemId,
			Integer quantity,
			Integer fulfilledQuantity,
			boolean completed,
			LocalDateTime creationDate) {

		this.id = id;
		this.userId = userId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.fulfilledQuantity = fulfilledQuantity;
		this.completed = completed;
		this.creationDate = creationDate;
	}

	public Long getId() { return id; }
	public Long getUserId() { return userId; }
	public Long getItemId() { return itemId; }
	public Integer getQuantity() { return quantity; }
	public Integer getFulfilledQuantity() { return fulfilledQuantity; }
	public boolean isCompleted() { return completed; }
	public LocalDateTime getCreationDate() { return creationDate; }
}