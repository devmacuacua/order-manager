package pt.agap2.ordermanager.stock.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.agap2.ordermanager.ApplicationContext;
import pt.agap2.ordermanager.order.dto.OrderStockMovementResponseDTO;
import pt.agap2.ordermanager.order.mapper.OrderStockMovementMapper;
import pt.agap2.ordermanager.shared.domain.exception.InvalidRequestException;
import pt.agap2.ordermanager.stock.dto.StockMovementRequestDTO;
import pt.agap2.ordermanager.stock.dto.StockMovementResponseDTO;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;
import pt.agap2.ordermanager.stock.mapper.StockMovementMapper;
import pt.agap2.ordermanager.stock.service.IStockMovementService;

@Path("/stock-movements")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockMovementController {

    private final IStockMovementService service;

    public StockMovementController() {
        this.service = ApplicationContext.stockMovementService();
    }

    @POST
    public Response create(StockMovementRequestDTO dto) {
        validateCreateRequest(dto);

        StockMovementEntity created = service.create(dto.getItemId(), dto.getQuantity());

        return Response.status(Response.Status.CREATED)
                .entity(StockMovementMapper.toResponse(created))
                .build();
    }

    @GET
    public List<StockMovementResponseDTO> list() {
        return service.list()
                .stream()
                .map(StockMovementMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        StockMovementEntity entity = service.get(id);

        return Response.ok(StockMovementMapper.toResponse(entity)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);

        return Response.noContent().build();
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

    private void validateCreateRequest(StockMovementRequestDTO dto) {
        if (dto == null) {
            throw new InvalidRequestException("Stock movement payload is required");
        }

        if (dto.getItemId() == null) {
            throw new InvalidRequestException("Stock movement itemId is required");
        }

        if (dto.getQuantity() == null) {
            throw new InvalidRequestException("Stock movement quantity is required");
        }

        if (dto.getQuantity() <= 0) {
            throw new InvalidRequestException("Stock movement quantity must be greater than zero");
        }
    }
}