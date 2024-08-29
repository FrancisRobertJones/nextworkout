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
            "Create a detailed workout plan with the following parameters:\n" +
                    "\n" +
                    "- **Workout Type**: {workoutType} (e.g., AMRAP, EMOM, For Time). The workout should strictly adhere to the style specified, focusing on the principles and goals associated with this type. For instance, if the workout type is AMRAP, ensure it is structured to maximize rounds within a set time.\n" +
                    "\n" +
                    "- **Total Session Length**: {totalSessionLength} minutes. Please include time for warm-up, the main workout, and cool down within this duration.\n" +
                    "\n" +
                    "- **Main Workout Length**: {workoutLength} minutes. Dedicate this time specifically to the main workout section, ensuring it aligns with the chosen workout style.\n" +
                    "\n" +
                    "- **Focus Muscle Groups**: Include exercises targeting {muscleGroupsToInclude} and avoid exercises for {muscleGroupsToAvoid}.\n" +
                    "\n" +
                    "- **Preferred Equipment**: Focus on using {preferredEquipment}. If any exercises require equipment that is not listed, suggest alternatives or modifications.\n" +
                    "\n" +
                    "- **Exercises to Include**: Prioritize including {exerciseToInclude} in the workout.\n" +
                    "\n" +
                    "- **Exercises to Avoid**: Do not include {exercisesToAvoid} in the workout.\n" +
                    "\n" +
                    "- **Warm-up and Cooldown**: Include a brief warm-up and cooldown session.\n" +
                    "\n" +
                    "For each exercise, provide:\n" +
                    "1. **Name of the exercise**\n" +
                    "2. **Number of sets and reps** (or time duration if applicable)\n" +
                    "3. **Instructions on how to perform the exercise**\n" +
                    "\n" +
                    "Ensure that the workout plan aligns strictly with the specified workout type and that the estimated duration for each exercise adds up appropriately within the total session length. The response should be a valid JSON object with the following structure:\n" +
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
