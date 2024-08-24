package com.FrancisJones.NextWorkout.services;

import com.FrancisJones.NextWorkout.dto.WorkoutDTO;
import com.FrancisJones.NextWorkout.entities.WorkoutEntity;
import com.FrancisJones.NextWorkout.repositories.WorkoutRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class WorkoutService {
    private WorkoutRepository workoutRepository;
    private WebClient webClient;

    public WorkoutService(WorkoutRepository workoutRepository,
                          WebClient.Builder webClientBuilder,
                          @Value("${openai.api.key}") String apiKey,
                          @Value("${openai.api.url}") String apiUrl)

    {
        this.workoutRepository = workoutRepository;
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Mono<String> generateWorkoutJsonFromLLM(WorkoutDTO workoutDTO){
        String prompt = buildGptPromptFromWorkoutDTO(workoutDTO);
        List<Map<String, String>> messages = List.of(
               Map.of("role", "system",
                       "content", workoutDTO.getSystemPrompt()
               ),
                Map.of("role", "user",
                        "content", prompt
                )
        );
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-3.5-turbo");
        payload.put("messages", messages);

        return webClient.post()
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Failed to call GPT API"))
                )
                .bodyToMono(String.class);
    }

    public Mono<WorkoutEntity> saveWorkoutToDb(WorkoutEntity workoutEntity) {
       return Mono.fromCallable(() -> workoutRepository.save(workoutEntity));
    }

    private String buildGptPromptFromWorkoutDTO(WorkoutDTO workoutDTO) {
        return  "The workout should be a " + workoutDTO.getWorkoutType() + " workout, lasting "
                + workoutDTO.getTotalSessionLength() + " minutes in total, with a workout session of "
                + workoutDTO.getWorkoutLength() + " minutes. Focus on the following muscle groups: "
                + workoutDTO.getMuscleGroupsToInclude() + ". Avoid exercises targeting: "
                + workoutDTO.getMuscleGroupsToAvoid() + ", and avoid exercises such as "
                + workoutDTO.getExercisesToAvoid() + ". Please try to include exercises that use the following equipment: "
                + workoutDTO.getPreferredEquipment() + ". Provide a detailed and structured workout plan.";
    }

    private String buildRequestBody(String prompt) {
        Map<String, Object> requestBody =  new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 150);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error building JSON request body", e);
        }
    }
}
