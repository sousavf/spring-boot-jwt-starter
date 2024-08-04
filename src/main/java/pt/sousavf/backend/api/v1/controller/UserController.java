package pt.sousavf.backend.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pt.sousavf.backend.api.v1.dto.user.UpdateUserRequest;
import pt.sousavf.backend.core.services.UserServiceImpl;
import pt.sousavf.backend.core.services.payloads.ServiceResponseDto;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @PutMapping
    public ServiceResponseDto<String> updateUser(UpdateUserRequest updateUserRequest) {

        return ServiceResponseDto.success(""); //TODO
    }

    @PostMapping(path = "/deactivate")
    public ServiceResponseDto<String> deactivateUser(UpdateUserRequest deleteUserRequest) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        userService.deactivateUser(email);

        return ServiceResponseDto.success(""); //TODO
    }

}
