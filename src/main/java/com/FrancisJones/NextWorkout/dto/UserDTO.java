package com.FrancisJones.NextWorkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {
    private Long id;
    private String nickname;
    private List<WorkoutDTO> workouts;
}
