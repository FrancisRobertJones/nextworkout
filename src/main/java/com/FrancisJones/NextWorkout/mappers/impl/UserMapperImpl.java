package com.FrancisJones.NextWorkout.mappers.impl;

import com.FrancisJones.NextWorkout.dto.UserDTO;
import com.FrancisJones.NextWorkout.entities.UserEntity;
import com.FrancisJones.NextWorkout.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDTO> {
    private ModelMapper modelMapper;

    @Override
    public UserDTO mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserEntity mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
