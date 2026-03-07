package pt.agap2.ordermanager.order.entity;

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
import pt.agap2.ordermanager.user.entity.UserEntity;

@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "fulfilled_quantity", nullable = false)
	private Integer fulfilledQuantity = 0;

	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private ItemEntity item;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	public Long getId() {
		return id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getFulfilledQuantity() {
		return fulfilledQuantity;
	}

	public void setFulfilledQuantity(Integer fulfilledQuantity) {
		this.fulfilledQuantity = fulfilledQuantity;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	// =========================
	// Domain behavior
	// =========================

	public Quantity totalQuantity() {
		return Quantity.of(quantity);
	}

	public Quantity fulfilledQuantityValue() {
		return Quantity.of(fulfilledQuantity);
	}

	public Quantity remainingQuantityValue() {
		return totalQuantity().subtract(fulfilledQuantityValue());
	}

	public int remainingQuantity() {
		return remainingQuantityValue().value();
	}

	public boolean isPending() {
		return fulfilledQuantityValue().isZero();
	}

	public boolean isPartial() {
		return fulfilledQuantityValue().isPositive()
				&& fulfilledQuantityValue().lessThan(totalQuantity());
	}

	public boolean isCompleted() {
		return fulfilledQuantityValue().greaterThanOrEqual(totalQuantity());
	}

	public OrderStatus status() {
		if (isCompleted()) {
			return OrderStatus.COMPLETED;
		}
		if (isPartial()) {
			return OrderStatus.PARTIAL;
		}
		return OrderStatus.PENDING;
	}

	public void allocate(int quantityToAllocate) {
		Quantity allocation = Quantity.positive(quantityToAllocate);

		if (isCompleted()) {
			throw new IllegalStateException("Order is already completed");
		}

		if (allocation.greaterThan(remainingQuantityValue())) {
			throw new IllegalArgumentException("Allocation quantity exceeds remaining order quantity");
		}

		this.fulfilledQuantity = fulfilledQuantityValue().add(allocation).value();
	}
	
	public void complete() {

	    if (status == OrderStatus.COMPLETED) {
	        throw new OrderAlreadyCompletedException();
	    }

	    status = OrderStatus.COMPLETED;
	}
}