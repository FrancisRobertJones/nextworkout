package com.FrancisJones.NextWorkout.services;

import com.FrancisJones.NextWorkout.dto.WorkoutDTO;
import com.FrancisJones.NextWorkout.entities.WorkoutEntity;
import com.FrancisJones.NextWorkout.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WorkoutService {
    private WorkoutRepository workoutRepository;
    private WebClient webClient;

    public WorkoutService(WorkoutRepository workoutRepository,
                          WebClient.Builder webClientBuilder,
                          @Value("${GPTKEY}") String apiKey) {
        this.workoutRepository = workoutRepository;
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        System.out.println("GPT API Key: " + apiKey);
    }

    public Mono<String> generateWorkoutJsonFromLLM(WorkoutDTO workoutDTO){
        String prompt = buildGptPromptFromWorkoutDTO(workoutDTO);

        return webClient.post()
                .uri("/ADDOPENAISURLHERE")
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class);
    }

    public WorkoutEntity saveWorkoutToDb(WorkoutEntity workoutEntity) {
       return workoutRepository.save(workoutEntity);
    }

    public String buildGptPromptFromWorkoutDTO(WorkoutDTO workoutDTO) {
        return "Create a " + workoutDTO.getWorkoutType() + " workout for "
                + workoutDTO.getTotalSessionLength() + " minutes.";
        //TODO build on this, make a proper prompt.
    }


    private String buildRequestBody(String prompt) {
        // Build your request body, e.g., {"prompt": prompt, "max_tokens": 100}
        return "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 100}";
    }

}
