package pt.sousavf.backend.core.model.mappers.user;

import org.springframework.stereotype.Component;
import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.model.entity.UserEntity;

import java.util.UUID;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUuid(UUID.fromString(userEntity.getUuid()));

        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setPassword(userEntity.getPassword());
        userDto.setEmailAddress(userEntity.getEmailAddress());

        userDto.setEnabled(userEntity.isEnabled());
        userDto.setAccountNonLocked(userEntity.isAccountNonLocked());
        userDto.setAccountNonExpired(userEntity.isAccountNonExpired());
        userDto.setCredentialsNonExpired(userEntity.isCredentialsNonExpired());

        userDto.setCreationDate(userEntity.getCreationDate());

        userDto.setRole(userEntity.getRole());

        return userDto;
    }

    @Override
    public UserEntity toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(userDto.getUuid().toString());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmailAddress(userDto.getEmailAddress());

        userEntity.setEnabled(userDto.isEnabled());
        userEntity.setAccountNonLocked(userDto.isAccountNonLocked());
        userEntity.setAccountNonExpired(userDto.isAccountNonExpired());
        userEntity.setCredentialsNonExpired(userDto.isCredentialsNonExpired());

        userEntity.setCreationDate(userDto.getCreationDate());

        userEntity.setRole(userDto.getRole());

        return userEntity;
    }
}
