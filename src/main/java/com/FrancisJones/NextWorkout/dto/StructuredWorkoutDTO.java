package com.FrancisJones.NextWorkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StructuredWorkoutDTO {
    private List<ExerciseDetailDTO> warmup;
    private List<ExerciseDetailDTO> mainWorkout;
    private List<ExerciseDetailDTO> cooldown;
}
