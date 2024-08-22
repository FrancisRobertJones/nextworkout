package com.FrancisJones.NextWorkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkoutDTO {
    private String workoutType;
    private int totalSessionLength;
    private int workoutLength;
    private String muscleGroupsToAvoid;
    private String muscleGroupsToInclude;
    private String exercisesToAvoid;
    private String exerciseToInclude;
    private String preferredEquipment;
    private String workoutJson;
}
