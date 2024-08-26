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
    private StructuredWorkoutDTO structuredWorkout;

    @Builder.Default
    private String systemPrompt =
            "You are a top-tier crossfit coach. Please create a workout plan based on the following parameters. The response should be a valid JSON object with the following structure:\n" +
                    "\n" +
                    "{\n" +
                    "  \"warmup\": [\n" +
                    "    {\n" +
                    "      \"exerciseName\": \"Exercise Name\",\n" +
                    "      \"duration\": \"Time in minutes\",\n" +
                    "      \"instructions\": \"How to do the exercise\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"mainWorkout\": [\n" +
                    "    {\n" +
                    "      \"exerciseName\": \"Exercise Name\",\n" +
                    "      \"reps\": \"Number of repetitions\",\n" +
                    "      \"sets\": \"Number of sets\",\n" +
                    "      \"instructions\": \"How to do the exercise\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"cooldown\": [\n" +
                    "    {\n" +
                    "      \"exerciseName\": \"Exercise Name\",\n" +
                    "      \"duration\": \"Time in minutes\",\n" +
                    "      \"instructions\": \"How to do the exercise\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
}
