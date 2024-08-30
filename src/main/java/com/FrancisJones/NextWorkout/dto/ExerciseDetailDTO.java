package com.FrancisJones.NextWorkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExerciseDetailDTO {
    private String exerciseName;
    private String duration;
    private String reps;
    private String sets;
    private String instructions;
    private String restTimeBetweenSets;
}
