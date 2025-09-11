package com.fweek.workoutManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class ExerciseRequestDto {

    @Schema(description = "운동 이름", example = "아침 러닝")
    @NotBlank
    private String exerciseName;

    @Schema(description = "운동 시간(분)", example = "30")
    @Min(0)
    private int durationMinutes;

    @Schema(description = "운동 일자(YYYY-MM-DD)", example = "2025-09-08")
    @NotNull
    @PastOrPresent
    private LocalDate exercisedAt;

    // 기본 생성자
    public ExerciseRequestDto() {}

    // 전체 필드 생성자
    public ExerciseRequestDto(String exerciseName, int durationMinutes, LocalDate exercisedAt) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.exercisedAt = exercisedAt;
    }

    // Getter/Setter
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDate getExercisedAt() { return exercisedAt; }
    public void setExercisedAt(LocalDate exercisedAt) { this.exercisedAt = exercisedAt; }
}
