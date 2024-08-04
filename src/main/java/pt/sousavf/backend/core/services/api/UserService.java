package pt.sousavf.backend.core.services.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import pt.sousavf.backend.api.v1.dto.authentication.LoginRequest;
import pt.sousavf.backend.api.v1.dto.authentication.RegistrationRequest;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto getOrCreateUser(final UserDto userDto);
    UserDto createUser(final RegistrationRequest request);
    UserDto createUser(final UserDto userDto);

    UserDto updateUserPassword(final String email, final String password);

    UserDto getUserDto(final LoginRequest request);
    UserDto getUserDto(final String email);

    UserEntity getUserEntity(final String email);

    boolean emailExists(final String emailAddress);

    UserDetailsService userDetailsService();

}
