package com.scm.module.Mapper;

import com.scm.module.DTO.UserDTO;
import com.scm.module.Model.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity convertToUserEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public UserDTO convertToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        // Clear password from DTO to not expose it
        userDTO.setPassword(null);
        return userDTO;
    }
}
