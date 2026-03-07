package pt.agap2.ordermanager.order.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.agap2.ordermanager.order.dto.OrderCompletionResponseDTO;
import pt.agap2.ordermanager.order.dto.OrderRequestDTO;
import pt.agap2.ordermanager.order.dto.OrderStockMovementResponseDTO;
import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.mapper.OrderMapper;
import pt.agap2.ordermanager.order.mapper.OrderStockMovementMapper;
import pt.agap2.ordermanager.order.repository.OrderRepository;
import pt.agap2.ordermanager.order.service.IOrderService;
import pt.agap2.ordermanager.order.service.OrderService;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

	private final IOrderService service = new OrderService(new OrderRepository());

	@POST
	public Response create(OrderRequestDTO dto) {

		if (dto == null || dto.getItemId() == null || dto.getUserId() == null || dto.getQuantity() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		OrderEntity created = service.create(dto.getUserId(), dto.getItemId(), dto.getQuantity());

		if (created == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User or Item not found").build();
		}

		return Response.status(Response.Status.CREATED).entity(OrderMapper.toResponse(created)).build();
	}

	@GET
	public List<?> list() {
		return service.list().stream().map(OrderMapper::toResponse).collect(Collectors.toList());
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {

		OrderEntity entity = service.get(id);

		if (entity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(OrderMapper.toResponse(entity)).build();
	}

	@GET
	@Path("/{id}/completion")
	public Response getCompletion(@PathParam("id") Long id) {
		OrderCompletionResponseDTO completion = service.getCompletion(id);

		if (completion == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(completion).build();
	}

	@GET
	@Path("/{id}/allocations")
	public Response getAllocations(@PathParam("id") Long id) {
		OrderEntity order = service.get(id);
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		List<OrderStockMovementResponseDTO> allocations = service.getAllocations(id).stream()
				.map(OrderStockMovementMapper::toResponse).collect(Collectors.toList());

		return Response.ok(allocations).build();
	}
}