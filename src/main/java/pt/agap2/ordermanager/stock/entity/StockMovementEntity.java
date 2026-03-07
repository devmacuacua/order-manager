package pt.agap2.ordermanager.stock.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.shared.domain.valueobject.Quantity;

@Entity
@Table(name = "stock_movements")
public class StockMovementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private ItemEntity item;

	public Long getId() {
		return id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public ItemEntity getItem() {
		return item;
	}

	public void setItem(ItemEntity item) {
		this.item = item;
	}

	// =========================
	// Domain behavior
	// =========================

	public Quantity totalQuantity() {
		return Quantity.of(quantity);
	}

	public Quantity usedQuantity(int alreadyUsed) {
		validateTrackedQuantity(alreadyUsed);
		return Quantity.of(alreadyUsed);
	}

	public Quantity availableQuantityValue(int alreadyUsed) {
		validateTrackedQuantity(alreadyUsed);
		return totalQuantity().subtract(usedQuantity(alreadyUsed));
	}

	public int availableQuantity(int alreadyUsed) {
		return availableQuantityValue(alreadyUsed).value();
	}

	public boolean isFullyAllocated(int alreadyUsed) {
		return usedQuantity(alreadyUsed).greaterThanOrEqual(totalQuantity());
	}

	public boolean hasAvailableQuantity(int alreadyUsed) {
		return availableQuantityValue(alreadyUsed).isPositive();
	}

	private void validateTrackedQuantity(int alreadyUsed) {
		if (alreadyUsed < 0) {
			throw new IllegalArgumentException("Tracked used quantity cannot be negative");
		}

		if (alreadyUsed > quantity) {
			throw new IllegalArgumentException("Tracked used quantity cannot exceed stock movement quantity");
		}
	}
}