package com.FrancisJones.NextWorkout.mappers.impl;

import com.FrancisJones.NextWorkout.dto.StructuredWorkoutDTO;
import com.FrancisJones.NextWorkout.dto.WorkoutDTO;
import com.FrancisJones.NextWorkout.entities.WorkoutEntity;
import com.FrancisJones.NextWorkout.mappers.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkoutMapperImpl implements Mapper<WorkoutEntity, WorkoutDTO> {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    public WorkoutMapperImpl(ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public WorkoutDTO mapTo(WorkoutEntity workoutEntity) {
        WorkoutDTO workoutDTO = modelMapper.map(workoutEntity, WorkoutDTO.class);
        if(workoutEntity.getStructuredWorkoutJSON() != null) {
            String json = workoutEntity.getStructuredWorkoutJSON();
            try {
                StructuredWorkoutDTO structuredWorkoutDTO = objectMapper.readValue(json, StructuredWorkoutDTO.class);
                workoutDTO.setStructuredWorkout(structuredWorkoutDTO);
            } catch (JsonProcessingException e ) {
                throw new RuntimeException("Failed to convert structuredWorkoutJSON to structuredWorkoutDTO", e);
            }
        }
        return workoutDTO;
    }

    @Override
    public WorkoutEntity mapFrom(WorkoutDTO workoutDTO) {
        WorkoutEntity workoutEntity = modelMapper.map(workoutDTO, WorkoutEntity.class);
        try {
            if (workoutDTO.getStructuredWorkout() != null) {

                workoutEntity.setStructuredWorkoutJSON(objectMapper.writeValueAsString(workoutDTO.getStructuredWorkout()));
            }
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert structuredWorkout to JSON", e);

        }
        return workoutEntity;
    }
}
