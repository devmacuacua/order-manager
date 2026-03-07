package pt.agap2.ordermanager.stock.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.agap2.ordermanager.stock.dto.StockMovementRequestDTO;
import pt.agap2.ordermanager.stock.dto.StockMovementResponseDTO;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;
import pt.agap2.ordermanager.stock.mapper.StockMovementMapper;
import pt.agap2.ordermanager.stock.repository.StockMovementRepository;
import pt.agap2.ordermanager.stock.service.IStockMovementService;
import pt.agap2.ordermanager.stock.service.StockMovementService;

@Path("/stock-movements")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockMovementController {

	private final IStockMovementService service = new StockMovementService(new StockMovementRepository());

	@POST
	public Response create(StockMovementRequestDTO dto) {
		if (dto == null || dto.getItemId() == null || dto.getQuantity() == null || dto.getQuantity() <= 0) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("itemId and positive quantity are required")
					.build();
		}

		try {
			StockMovementEntity created = service.create(dto.getItemId(), dto.getQuantity());
			if (created == null) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Item not found")
						.build();
			}
			return Response.status(Response.Status.CREATED)
					.entity(StockMovementMapper.toResponse(created))
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Could not create stock movement")
					.build();
		}
	}

	@GET
	public List<StockMovementResponseDTO> list() {
		return service.list().stream()
				.map(StockMovementMapper::toResponse)
				.collect(Collectors.toList());
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		StockMovementEntity entity = service.get(id);
		if (entity == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(StockMovementMapper.toResponse(entity)).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		boolean removed = service.delete(id);
		if (!removed) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.noContent().build();
	}
}