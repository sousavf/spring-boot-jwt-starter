package pt.sousavf.backend.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sousavf.backend.api.v1.dto.authentication.EmailValidationRequest;
import pt.sousavf.backend.api.v1.dto.authentication.LoginRequest;
import pt.sousavf.backend.api.v1.dto.authentication.RegistrationRequest;
import pt.sousavf.backend.api.v1.dto.authentication.ResetPasswordRequest;
import pt.sousavf.backend.core.security.JwtAuthenticationResponse;
import pt.sousavf.backend.core.services.api.AuthenticationService;
import pt.sousavf.backend.core.services.payloads.ServiceResponseDto;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ServiceResponseDto> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registration(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    /**
     * This method handles password reset requests.
     * It takes a ResetPasswordRequest object as input and returns a ServiceResponseDto object.
     *
     * @param request The password reset request containing the user's email.
     * @return A ServiceResponseDto object containing the status of the password reset operation.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ServiceResponseDto> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.resetPassword(request));
    }

    // TODO Check for user existance

    /**
     * This method checks if an email is valid.
     * It takes an EmailValidationRequest object as input and returns a Boolean value.
     *
     * @param request The email validation request containing the email to be checked.
     * @return A Boolean value indicating whether the email is valid or not.
     */
    @GetMapping("/email/valid")
    ResponseEntity<Boolean> checkEmail(@RequestBody EmailValidationRequest request) {
        final Boolean emailExists = authenticationService.isEmailValid(request.getEmail());

        return ResponseEntity.ok(emailExists);
    }
}