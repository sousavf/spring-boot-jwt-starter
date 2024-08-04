package pt.sousavf.backend.core.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.sousavf.backend.api.v1.dto.authentication.LoginRequest;
import pt.sousavf.backend.api.v1.dto.authentication.RegistrationRequest;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.model.entity.UserEntity;
import pt.sousavf.backend.core.model.enums.UserRole;
import pt.sousavf.backend.core.model.mappers.user.UserMapper;
import pt.sousavf.backend.core.repository.UserRepository;
import pt.sousavf.backend.core.services.api.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        final List<UserDto> usersList;

        usersList = userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        return usersList;
    }

    public UserDto createUser(final RegistrationRequest request) {
        final UserDto userDto = UserDto.builder()
                .uuid(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailAddress(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_USER)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true) // TODO Email activation to be used here - User should be created inactive
                .build();

        final UserEntity userEntity = userMapper.toEntity(userDto);

        userRepository.save(userEntity);
        return userDto;
    }

    public UserDto getOrCreateUser(final UserDto userDto) {
        Optional<UserEntity> existingUserEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (existingUserEntity.isPresent()) {
            return userMapper.toDto(existingUserEntity.get());
        }

        final UserEntity userEntity = userMapper.toEntity(userDto);

        userRepository.save(userEntity);
        return userDto;

    }

    public UserDto createUser(final UserDto userDto) {
        final UserEntity userEntity = userMapper.toEntity(userDto);

        userRepository.save(userEntity);
        return userDto;
    }

    public UserDto getUserDto(final LoginRequest request) {
        return getUserDto(request.getEmail());
    }

    public UserDto getUserDto(final String email) {
        final UserEntity userEntity = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        final UserDto userDto = userMapper.toDto(userEntity);
        return userDto;
    }

    @Override
    public UserEntity getUserEntity(String email) {
        return userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

    public boolean emailExists(final String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).isPresent();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                final UserEntity userEntity = userRepository.findByEmailAddress(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return userMapper.toDto(userEntity);
            }
        };
    }

    public UserDto updateUserPassword(final String email, final String password) {
        final Optional<UserEntity> userEntity = userRepository.findByEmailAddress(email);

        if (userEntity.isPresent()) {
            userEntity.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity.get());
        }

        return userMapper.toDto(userEntity.get());
    }

    public void deactivateUser(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAddress(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setEnabled(false);
            userRepository.save(userEntity);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
