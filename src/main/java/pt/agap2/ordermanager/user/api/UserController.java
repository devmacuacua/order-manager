package pt.agap2.ordermanager.user.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.agap2.ordermanager.ApplicationContext;
import pt.agap2.ordermanager.user.dto.UserRequestDTO;
import pt.agap2.ordermanager.user.dto.UserResponseDTO;
import pt.agap2.ordermanager.user.entity.UserEntity;
import pt.agap2.ordermanager.user.mapper.UserMapper;
import pt.agap2.ordermanager.user.service.IUserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

	private final IUserService service;

	public UserController() {
		this.service = ApplicationContext.userService();
	}

	@POST
	public Response create(UserRequestDTO dto) {
		if (dto == null || dto.getName() == null || dto.getEmail() == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Name and email required")
					.build();
		}

		try {
			UserEntity created = service.create(UserMapper.toEntity(dto));
			return Response.status(Response.Status.CREATED)
					.entity(UserMapper.toResponse(created))
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Could not create user (maybe email already exists)")
					.build();
		}
	}

	@GET
	public List<UserResponseDTO> list() {
		return service.list().stream()
				.map(UserMapper::toResponse)
				.collect(Collectors.toList());
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		UserEntity user = service.get(id);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(UserMapper.toResponse(user)).build();
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, UserRequestDTO dto) {
		if (dto == null || dto.getName() == null || dto.getEmail() == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Name and email required")
					.build();
		}

		try {
			UserEntity updated = service.update(id, dto);
			if (updated == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.ok(UserMapper.toResponse(updated)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Could not update user (maybe email already exists)")
					.build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		service.delete(id);
		return Response.noContent().build();
	}
}