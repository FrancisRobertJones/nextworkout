package com.FrancisJones.NextWorkout.services;

import com.FrancisJones.NextWorkout.dto.StructuredWorkoutDTO;
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
    private final ObjectMapper objectMapper;

    public WorkoutService(WorkoutRepository workoutRepository,
                          WebClient.Builder webClientBuilder,
                          @Value("${openai.api.key}") String apiKey,
                          @Value("${openai.api.url}") String apiUrl,
                         ObjectMapper objectMapper)

    {
        this.workoutRepository = workoutRepository;
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.objectMapper = objectMapper;
    }

    public Mono<String> generateWorkoutJsonFromLLM(WorkoutDTO workoutDTO){
        String prompt = buildGptPromptFromWorkoutDTO(workoutDTO);
        Map<String, Object> requestBody = buildRequestBody(workoutDTO.getSystemPrompt(), prompt);

        String jsonPayload;

        try {
            jsonPayload = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient
                .post()
                .bodyValue(jsonPayload)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is5xxServerError() || httpStatusCode.is4xxClientError(),
                        clientResponse -> Mono.error(new RuntimeException("Failed to call gpt API"))
                )
                .bodyToMono(String.class);





/*

        String jsonPayload;
        try {
             jsonPayload = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException("Error serializing request body", e));
        }
            return webClient.post()
                    .bodyValue(jsonPayload)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> Mono.error(new RuntimeException("Failed to call GPT API"))
                    )
                    .bodyToMono(String.class);
*/

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

    private Map<String, Object> buildRequestBody(String systemPrompt, String userPrompt) {
        List<Map<String, String>> messages = List.of(
                Map.of("role", "system",
                        "content", systemPrompt
                ),
                Map.of("role", "user",
                        "content", userPrompt
                )
        );
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-3.5-turbo");
        payload.put("messages", messages);
        return payload;
    }

    public Mono<WorkoutDTO> processWorkoutJsonResponse(WorkoutDTO workoutDTO) {
        return Mono.fromCallable(() -> {
            StructuredWorkoutDTO structuredWorkout = parseWorkoutContent(workoutDTO.getWorkoutJson());
            workoutDTO.setStructuredWorkout(structuredWorkout);
            return workoutDTO;
        });
    }

    public StructuredWorkoutDTO parseWorkoutContent(String workoutJson) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(workoutJson, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            String workoutContent = (String) message.get("content");
            return objectMapper.readValue(workoutContent, StructuredWorkoutDTO.class);
        } catch (Exception e) {
            System.err.println("Failed to parse workout JSON: " + workoutJson);
            e.printStackTrace();
            throw new RuntimeException("Failed to parse workout JSON", e );
        }
    }
}
