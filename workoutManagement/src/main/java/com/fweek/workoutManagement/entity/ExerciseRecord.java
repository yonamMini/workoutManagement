package com.fweek.workoutManagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exercise_records")
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String exerciseName;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private LocalDate exercisedAt;

    // 기본 생성자
    public ExerciseRecord() {}

    // 전체 필드 생성자
    public ExerciseRecord(Long id, String exerciseName, int durationMinutes, LocalDate exercisedAt) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.exercisedAt = exercisedAt;
    }

    // getter & setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDate getExercisedAt() { return exercisedAt; }
    public void setExercisedAt(LocalDate exercisedAt) { this.exercisedAt = exercisedAt; }

    // 빌더 대체 메서드
    public static ExerciseRecord create(String exerciseName, int durationMinutes, LocalDate exercisedAt) {
        return new ExerciseRecord(null, exerciseName, durationMinutes, exercisedAt);
    }
}