package pt.sousavf.backend.core.services.payloads;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ServiceResponseDto<K extends Object> {

    private K responsePayload;

    private HttpStatus errorCode;

    public static ServiceResponseDto success() {
        return ServiceResponseDto.builder()
                .errorCode(HttpStatus.resolve(200))
                .build();
    }

    public static ServiceResponseDto success(Object responsePayload) {
        return ServiceResponseDto.builder()
                .responsePayload(responsePayload)
                .errorCode(HttpStatus.resolve(200))
                .build();
    }

    public static ServiceResponseDto failed(Object responsePayload, HttpStatus statusCode) {
        return ServiceResponseDto.builder()
                .responsePayload(responsePayload)
                .errorCode(statusCode)
                .build();
    }
}
