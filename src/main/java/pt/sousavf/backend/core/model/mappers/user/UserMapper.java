package pt.sousavf.backend.core.model.mappers.user;

import pt.sousavf.backend.core.model.dto.UserDto;
import pt.sousavf.backend.core.model.entity.UserEntity;

public interface UserMapper {
    UserDto toDto(UserEntity userEntity);

    UserEntity toEntity(UserDto userDto);

}
