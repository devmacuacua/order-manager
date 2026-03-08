package pt.agap2.ordermanager.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

@Entity
@Table(name = "order_stock_movements")
public class OrderStockMovementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@ManyToOne(optional = false)
	@JoinColumn(name = "stock_movement_id", nullable = false)
	private StockMovementEntity stockMovement;

	@Column(name = "quantity_used", nullable = false)
	private Integer quantityUsed;

	public Long getId() {
		return id;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public StockMovementEntity getStockMovement() {
		return stockMovement;
	}

	public void setStockMovement(StockMovementEntity stockMovement) {
		this.stockMovement = stockMovement;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
}