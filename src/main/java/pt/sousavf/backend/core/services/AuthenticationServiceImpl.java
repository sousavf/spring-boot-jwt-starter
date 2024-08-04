package pt.sousavf.backend.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pt.sousavf.backend.api.v1.dto.authentication.LoginRequest;
import pt.sousavf.backend.api.v1.dto.authentication.RegistrationRequest;
import pt.sousavf.backend.api.v1.dto.authentication.ResetPasswordRequest;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.security.JwtAuthenticationResponse;
import pt.sousavf.backend.core.services.api.AuthenticationService;
import pt.sousavf.backend.core.services.api.JwtService;
import pt.sousavf.backend.core.services.api.UserService;
import pt.sousavf.backend.core.services.payloads.ServiceResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ServiceResponseDto<JwtAuthenticationResponse> registration(RegistrationRequest request) {

        if (!isEmailValid(request.getEmail())) {
            return null;
        }

        final UserDto userDto = userService.createUser(request);

        var jwt = jwtService.generateToken(userDto);
        ServiceResponseDto.success(jwt);
        return ServiceResponseDto.success();
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            log.error("Error generating token for user {}: {}", request.getEmail(), e.getMessage());
            return JwtAuthenticationResponse.builder().errorMessage("User not found or incorrect credentials.").build();
        }

        UserDto userDto = userService.getUserDto(request);

        final String jwt = jwtService.generateToken(userDto);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private Boolean checkIfEmailExists(String emailAddress) {
        return userService.emailExists(emailAddress);
    }

    public Boolean isEmailValid(final String emailAddress) {

        if (!EmailValidation.patternMatches(emailAddress)) {
            return false;
        }

        return !checkIfEmailExists(emailAddress);
    }

    // Other methods...

    public ServiceResponseDto resetPassword(ResetPasswordRequest request) {
        final String email = request.getEmail();
        if (!EmailValidation.patternMatches(email)) {
            return ServiceResponseDto.failed(false, HttpStatus.NOT_ACCEPTABLE);
        }

        boolean emailExists = userService.emailExists(email);
        if (!emailExists) {
            return ServiceResponseDto.failed(false, HttpStatus.NOT_ACCEPTABLE);
        }

        userService.updateUserPassword(email, request.getNewPassword());

        return ServiceResponseDto.success(true);
    }
}