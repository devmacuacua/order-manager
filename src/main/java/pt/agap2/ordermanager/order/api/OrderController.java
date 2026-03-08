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

import pt.agap2.ordermanager.ApplicationContext;
import pt.agap2.ordermanager.order.dto.OrderCompletionResponseDTO;
import pt.agap2.ordermanager.order.dto.OrderRequestDTO;
import pt.agap2.ordermanager.order.dto.OrderResponseDTO;
import pt.agap2.ordermanager.order.dto.OrderStockMovementResponseDTO;
import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.order.mapper.OrderMapper;
import pt.agap2.ordermanager.order.mapper.OrderStockMovementMapper;
import pt.agap2.ordermanager.order.service.IOrderService;
import pt.agap2.ordermanager.shared.domain.exception.InvalidRequestException;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

    private final IOrderService service;

    public OrderController() {
        this.service = ApplicationContext.orderService();
    }

    @POST
    public Response create(OrderRequestDTO dto) {
        validateCreateRequest(dto);

        OrderEntity created = service.create(dto.getUserId(), dto.getItemId(), dto.getQuantity());

        return Response.status(Response.Status.CREATED)
                .entity(OrderMapper.toResponse(created))
                .build();
    }

    @GET
    public List<OrderResponseDTO> list() {
        return service.list()
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        OrderEntity entity = service.get(id);

        return Response.ok(OrderMapper.toResponse(entity)).build();
    }

    @GET
    @Path("/{id}/completion")
    public Response getCompletion(@PathParam("id") Long id) {
        OrderCompletionResponseDTO completion = service.getCompletion(id);

        return Response.ok(completion).build();
    }

    @GET
    @Path("/{id}/allocations")
    public Response getAllocations(@PathParam("id") Long id) {
        List<OrderStockMovementResponseDTO> allocations = service.getAllocations(id)
                .stream()
                .map(OrderStockMovementMapper::toResponse)
                .collect(Collectors.toList());

        return Response.ok(allocations).build();
    }

    private void validateCreateRequest(OrderRequestDTO dto) {
        if (dto == null) {
            throw new InvalidRequestException("Order payload is required");
        }

        if (dto.getItemId() == null) {
            throw new InvalidRequestException("Order itemId is required");
        }

        if (dto.getUserId() == null) {
            throw new InvalidRequestException("Order userId is required");
        }

        if (dto.getQuantity() == null) {
            throw new InvalidRequestException("Order quantity is required");
        }
    }
}