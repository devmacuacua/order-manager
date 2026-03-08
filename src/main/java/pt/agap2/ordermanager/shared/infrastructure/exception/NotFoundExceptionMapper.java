package pt.agap2.ordermanager.shared.infrastructure.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
            "RESOURCE_NOT_FOUND",
            "The requested resource was not found"
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
}