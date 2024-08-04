package pt.sousavf.backend.core.services.api;

import pt.sousavf.backend.api.v1.dto.authentication.LoginRequest;
import pt.sousavf.backend.api.v1.dto.authentication.RegistrationRequest;
import pt.sousavf.backend.api.v1.dto.authentication.ResetPasswordRequest;
import pt.sousavf.backend.core.security.JwtAuthenticationResponse;
import pt.sousavf.backend.core.services.payloads.ServiceResponseDto;

public interface AuthenticationService {
    ServiceResponseDto<JwtAuthenticationResponse> registration(RegistrationRequest request);

    JwtAuthenticationResponse login(LoginRequest request);

    Boolean isEmailValid(final String emailAddress);

    ServiceResponseDto<ResetPasswordRequest> resetPassword(ResetPasswordRequest request);
}
