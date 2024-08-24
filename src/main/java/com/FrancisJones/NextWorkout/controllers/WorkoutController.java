package com.FrancisJones.NextWorkout.controllers;

import com.FrancisJones.NextWorkout.dto.WorkoutDTO;
import com.FrancisJones.NextWorkout.entities.WorkoutEntity;
import com.FrancisJones.NextWorkout.mappers.Mapper;
import com.FrancisJones.NextWorkout.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WorkoutController {

    private Mapper<WorkoutEntity, WorkoutDTO> workoutMapper;
    private WorkoutService workoutService;
    public WorkoutController(WorkoutService workoutService, Mapper<WorkoutEntity, WorkoutDTO> workoutMapper) {
        this.workoutService = workoutService;
        this.workoutMapper = workoutMapper;
    }

    @PostMapping(path = "/workout/create")
    public Mono<ResponseEntity<WorkoutDTO>> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        return workoutService.generateWorkoutJsonFromLLM(workoutDTO)
                .flatMap(workoutJson -> {
                    workoutDTO.setWorkoutJson(workoutJson);
                    WorkoutEntity workoutEntityToBeSaved = workoutMapper.mapFrom(workoutDTO);
                    return workoutService.saveWorkoutToDb(workoutEntityToBeSaved)
                            .map(savedWorkoutEntity -> {
                                WorkoutDTO responseDTO = workoutMapper.mapTo(savedWorkoutEntity);
                                return ResponseEntity.ok(responseDTO);
                            });
                });
    }

    @GetMapping(path="/workout/healthcheck")
    public ResponseEntity<String> checkHealth(){
        System.out.print("health check endpoint hit");
        return ResponseEntity.ok("server up and running");
    }


}
