package pt.sousavf.backend.core.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pt.sousavf.backend.core.model.dto.UserDto;

public class BaseService {

    public String getConnectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String getConnectedUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto principal = (UserDto) authentication.getPrincipal();

        return principal.getUuid().toString();
    }
}
