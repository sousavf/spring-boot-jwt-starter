package pt.sousavf.backend.admin.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.services.api.UserService;
import pt.sousavf.backend.core.services.payloads.ServiceResponseDto;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/v1")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/find/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponseDto<Model> getAllUsers() {

        List<UserDto> users = userService.findAll();

        return ServiceResponseDto.success(users);
    }
}
