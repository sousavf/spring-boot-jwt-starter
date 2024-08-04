package pt.sousavf.backend.core.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the JWT authentication response.
 * It contains a single field, the token, which is the JWT token.
 *
 * @Data annotation generates getters and setters for all fields, a useful toString method, and hashCode and equals implementations.
 * @Builder annotation produces complex builder APIs for your classes.
 * @NoArgsConstructor annotation generates a no-args constructor.
 * @AllArgsConstructor annotation generates a constructor with 1 parameter for each field in your class.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    /**
     * Represents the JWT token.
     */
    private String token;

    /**
     * Represents the error message.
     */
    private String errorMessage;
}