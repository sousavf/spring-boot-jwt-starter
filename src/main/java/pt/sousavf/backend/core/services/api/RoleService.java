package pt.sousavf.backend.core.services.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.model.mappers.user.UserMapper;
import pt.sousavf.backend.core.repository.UserRepository;

@Service
public class RoleService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public boolean hasRole(final String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserDto userDto = userMapper.toDto(userRepository.findByEmailAddress(userDetails.getUsername()).get());

        return userDto.getRole().name().equalsIgnoreCase(role);
    }
}
