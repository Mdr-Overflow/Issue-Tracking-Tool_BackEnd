package Issue.Tracking.Tool.LoginSessionPoint.domainAssamblers;

import Issue.Tracking.Tool.LoginSessionPoint.Api.UserServiceClass;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>> {

    @Override
    public EntityModel<Role> toModel(Role role) {

        return EntityModel.of(role, //
                linkTo(methodOn(UserServiceClass.class).getRole(role.getName())).withSelfRel(),
                linkTo(methodOn(UserServiceClass.class).getALLRoles()).withRel("role"));
    }
}

