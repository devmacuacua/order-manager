package pt.agap2.ordermanager.shared.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import pt.agap2.ordermanager.shared.domain.exception.DomainException;

@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException ex) {

        ErrorResponse error = new ErrorResponse(
            ex.getCode(),
            ex.getMessage()
        );

        return Response
                .status(ex.getStatus())
                .entity(error)
                .build();
    }
}