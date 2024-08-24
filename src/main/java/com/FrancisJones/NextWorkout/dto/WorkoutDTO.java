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

    @Builder.Default
    private String systemPrompt =
                """
                        You are a top-tier crossfit coach. Please create a workout plan based on the following parameters, and respond with a workout in the following structure: \
                        {
                          "warmup": [
                            {"exercise": "Exercise Name", "duration": "Time in minutes", "instructions": "How to do the exercise"}
                          ],
                          "mainWorkout": [
                            {"exercise": "Exercise Name", "reps": "Number of repetitions", "sets": "Number of sets", "instructions": "How to do the exercise"}
                          ],
                          "cooldown": [
                            {"exercise": "Exercise Name", "duration": "Time in minutes", "instructions": "How to do the exercise"}
                          ]
                        }""";

}
