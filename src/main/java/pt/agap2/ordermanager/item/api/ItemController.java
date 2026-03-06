package pt.agap2.ordermanager.item.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.agap2.ordermanager.item.dto.ItemRequestDTO;
import pt.agap2.ordermanager.item.dto.ItemResponseDTO;
import pt.agap2.ordermanager.item.entity.ItemEntity;
import pt.agap2.ordermanager.item.mapper.ItemMapper;
import pt.agap2.ordermanager.item.repository.ItemRepository;
import pt.agap2.ordermanager.item.service.IItemService;
import pt.agap2.ordermanager.item.service.ItemService;

@Path("/items")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemController {

	private final IItemService service = new ItemService(new ItemRepository());

	@POST
	public Response create(ItemRequestDTO dto) {
		if (dto == null || dto.getName() == null || dto.getName().trim().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Name is required").build();
		}

		try {
			ItemEntity created = service.create(ItemMapper.toEntity(dto));
			return Response.status(Response.Status.CREATED).entity(ItemMapper.toResponse(created)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Could not create item (maybe name already exists)")
					.build();
		}
	}

	@GET
	public List<ItemResponseDTO> list() {
		return service.list().stream()
				.map(ItemMapper::toResponse)
				.collect(Collectors.toList());
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		ItemEntity item = service.get(id);
		if (item == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(ItemMapper.toResponse(item)).build();
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, ItemRequestDTO dto) {
		if (dto == null || dto.getName() == null || dto.getName().trim().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Name is required").build();
		}

		try {
			ItemEntity updated = service.update(id, dto);
			if (updated == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.ok(ItemMapper.toResponse(updated)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Could not update item (maybe name already exists)")
					.build();
		}
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