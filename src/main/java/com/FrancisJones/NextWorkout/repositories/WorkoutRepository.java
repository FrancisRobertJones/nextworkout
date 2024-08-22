package com.FrancisJones.NextWorkout.repositories;

import com.FrancisJones.NextWorkout.entities.WorkoutEntity;
import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<WorkoutEntity, String> {
}
