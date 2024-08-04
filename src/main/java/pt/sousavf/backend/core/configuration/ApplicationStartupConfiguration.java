package pt.sousavf.backend.core.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pt.sousavf.backend.core.event.ApplicationStartupEvent;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.model.enums.UserRole;
import pt.sousavf.backend.core.services.api.UserService;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartupConfiguration {

    final private UserService userService;
    final private PasswordEncoder passwordEncoder;

    @EventListener
    public ApplicationStartupEvent onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Application started");

        final UserDto userDto = UserDto.builder()
                .uuid(UUID.randomUUID())
                .firstName("Firstname")
                .lastName("Surname")
                .emailAddress("admin@email.com")
                .password(passwordEncoder.encode("password"))
                .role(UserRole.ROLE_ADMIN)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        userService.getOrCreateUser(userDto);

        return new ApplicationStartupEvent();
    }
}
