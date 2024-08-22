package com.FrancisJones.NextWorkout.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="workouts")
public class WorkoutEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workoutType;
    private int totalSessionLength;
    private int workoutLength;

    private String muscleGroupsToAvoid;
    private String exercisesToAvoid;
    private String muscleGroupsToInclude;
    private String preferredEquipment;

    @Lob
    private String workoutJson;
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
