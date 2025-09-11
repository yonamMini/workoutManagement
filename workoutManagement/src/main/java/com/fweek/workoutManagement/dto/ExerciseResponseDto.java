package com.fweek.workoutManagement.dto;


import java.time.LocalDate;

public class ExerciseResponseDto {

    private Long id;
    private String exerciseName;
    private int durationMinutes;
    private LocalDate exercisedAt;

    public ExerciseResponseDto() {}

    public ExerciseResponseDto(Long id, String exerciseName, int durationMinutes, LocalDate exercisedAt) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.exercisedAt = exercisedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDate getExercisedAt() { return exercisedAt; }
    public void setExercisedAt(LocalDate exercisedAt) { this.exercisedAt = exercisedAt; }

    // 빌더 대체
    public static ExerciseResponseDto of(Long id, String exerciseName, int durationMinutes, LocalDate exercisedAt) {
        return new ExerciseResponseDto(id, exerciseName, durationMinutes, exercisedAt);
    }
}
