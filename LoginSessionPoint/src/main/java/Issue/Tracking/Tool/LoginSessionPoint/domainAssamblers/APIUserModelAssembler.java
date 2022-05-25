package Issue.Tracking.Tool.LoginSessionPoint.domainAssamblers;


import Issue.Tracking.Tool.LoginSessionPoint.Api.UserServiceClass;
import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserService;
import Issue.Tracking.Tool.LoginSessionPoint.util.PageableInput;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class APIUserModelAssembler implements RepresentationModelAssembler<APIUser, EntityModel<APIUser>> {



    Integer PageIndex, PageSize, SortDirection;
    String SortBy;
    @Override
    public EntityModel<APIUser> toModel(APIUser user) {

        return EntityModel.of(user, //
                linkTo(methodOn(UserServiceClass.class).getUserByName(user.getUsername())).withSelfRel(),
                linkTo(methodOn(UserServiceClass.class).getUsers(PageIndex,PageSize,SortBy,SortDirection)).withRel("users/"),
                linkTo(methodOn(UserServiceClass.class).getUsersALL()).withRel("users/")
        );
    }


}
