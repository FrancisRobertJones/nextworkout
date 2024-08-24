package com.FrancisJones.NextWorkout.mappers.impl;

import com.FrancisJones.NextWorkout.dto.WorkoutDTO;
import com.FrancisJones.NextWorkout.entities.WorkoutEntity;
import com.FrancisJones.NextWorkout.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkoutMapperImpl implements Mapper<WorkoutEntity, WorkoutDTO> {
    private final ModelMapper modelMapper;

    public WorkoutMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkoutDTO mapTo(WorkoutEntity workoutEntity) {
        return modelMapper.map(workoutEntity, WorkoutDTO.class);
    }

    @Override
    public WorkoutEntity mapFrom(WorkoutDTO workoutDTO) {
        return modelMapper.map(workoutDTO, WorkoutEntity.class);
    }
}
