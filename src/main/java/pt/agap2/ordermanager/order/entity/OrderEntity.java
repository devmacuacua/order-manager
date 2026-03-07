package pt.agap2.ordermanager.order.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.user.entity.UserEntity;

@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "fulfilled_quantity")
	private Integer fulfilledQuantity = 0;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "item_id")
	private ItemEntity item;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
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

	public boolean isCompleted() {
		return fulfilledQuantity >= quantity;
	}
}