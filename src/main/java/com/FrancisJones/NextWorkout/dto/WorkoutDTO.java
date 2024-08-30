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
                    "Create a workout plan based on the following details:\n\n" +
                    "Workout Category: [Strength/Hypertrophy/Cardio/Hybrid]\n\n" +
                    "Total Session Length: [e.g., 60 minutes]\n\n" +
                    "Preferred Equipment: [e.g., dumbbells, kettlebell, treadmill, etc.]\n\n" +
                    "Muscle Groups to Focus On: [e.g., back, lats, biceps]\n\n" +
                    "Muscle Groups to Avoid: [e.g., legs, lower back]\n\n" +
                    "Exercises to Include: [e.g., squats, push-ups]\n\n" +
                    "Exercises to Avoid: [e.g., pull-ups]\n\n" +
                    "Rest Time Between Sets: [e.g., 60 seconds for hypertrophy, or specify differently for strength/cardio/hybrid]\n\n" +
                    "Workout Structure:\n\n" +
                     "- For Strength: Focus on exercises that promote strength training. " +
                            "Include multiple sets of each exercise with a specific number of repetitions and rest periods between sets Vary the amount of sets, and reps to make it interesting and fun, but naturally strength will be on the lower end of reps with longer breaks.\n\n" +
                    "- For Hypertrophy: Focus on exercises that promote muscle growth with moderate to heavy weights. " +
                    "Include multiple sets of each exercise with a specific number of repetitions and rest periods between sets Vary the amount of sets, and reps to make it interesting and fun, but naturally hypertrophy will be on the higher end of reps with shorter breaks.\n\n" +
                    "- For Cardio: Focus on exercises that maintain an elevated heart rate. Include continuous movements like running, " +
                    "cycling, or bodyweight exercises with minimal rest.\n\n" +
                    "- For Hybrid: Include a mix of weightlifting exercises and cardio exercises. Specify the equipment for cardio and " +
                    "the exercises for strength training. Rest times should be managed between sets, considering the mix of cardio and strength exercises.\n\n" +
                    "Prioritise time allocation to correct rest times between sets. If workouts are short, and strength workout is specified keep the total amount of exercises lower to accommodate rest. Generate a workout plan that includes a warm-up (warm exercises should be kept short), main workout, and cooldown (give specific exercises), with estimated durations for each exercise, " +
                    "and specify rest times between sets for each exercise. Vary warmups, make them imaginative, change it up and keep it relevant to the main goal of the workout"+
                    "\n" +
                    "{\n" +
                    "  \"warmup\": [\n" +
                    "    {\n" +
                    "      \"exerciseName\": \"Exercise Name\",\n" +
                    "      \"reps\": \"Number of repetitions\",\n" +
                    "      \"sets\": \"Number of sets\",\n" +
                    "      \"instructions\": \"How to do the exercise\"\n" +
                    "       \"restTimeBetweenSets\": \"Rest between sets\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"mainWorkout\": [\n" +
                    "    {\n" +
                    "      \"exerciseName\": \"Exercise Name\",\n" +
                    "      \"reps\": \"Number of repetitions\",\n" +
                    "      \"sets\": \"Number of sets\",\n" +
                    "      \"instructions\": \"How to do the exercise\"\n" +
                    "       \"restTimeBetweenSets\": \"Rest between sets\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"cooldown\": [\n" +
                    "    {\n" +
                    "      \"exerciseName\": \"Exercise Name\",\n" +
                    "      \"duration\": \"Time in minutes\",\n" +
                    "      \"instructions\": \"How to do the exercise\"\n" +
                    "       \"restTimeBetweenSets\": \"Rest between sets\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
}
