package com.fweek.workoutManagement.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import com.fweek.workoutManagement.dto.ExerciseRequestDto;
import com.fweek.workoutManagement.dto.ExerciseResponseDto;
import com.fweek.workoutManagement.dto.ExerciseStatsDto;
import com.fweek.workoutManagement.entity.ExerciseRecord;
import com.fweek.workoutManagement.repository.ExerciseRecordRepository;
@Service
@Transactional
public class ExerciseRecordService {

    private final ExerciseRecordRepository repository;

    public ExerciseRecordService(ExerciseRecordRepository repository) {
        this.repository = repository;
    }

    // 기록 생성
    public ExerciseResponseDto create(ExerciseRequestDto request) {
        ExerciseRecord record = new ExerciseRecord(
                null,
                request.getExerciseName(),
                request.getDurationMinutes(),
                request.getExercisedAt()
        );

        ExerciseRecord saved = repository.save(record);

        return new ExerciseResponseDto(
                saved.getId(),
                saved.getExerciseName(),
                saved.getDurationMinutes(),
                saved.getExercisedAt()
        );
    }

    // 기록 목록 조회
    public List<ExerciseResponseDto> getListBySort(String sort) {
        List<ExerciseRecord> records = repository.findAll();

        return records.stream()
                .sorted((a, b) -> {
                    if ("oldest".equals(sort)) {
                        return a.getExercisedAt().compareTo(b.getExercisedAt());
                    } else {
                        return b.getExercisedAt().compareTo(a.getExercisedAt());
                    }
                })
                .map(record -> new ExerciseResponseDto(
                        record.getId(),
                        record.getExerciseName(),
                        record.getDurationMinutes(),
                        record.getExercisedAt()
                ))
                .collect(Collectors.toList());
    }

    // 기록 수정
    public ExerciseResponseDto update(Long id, ExerciseRequestDto request) {
        ExerciseRecord record = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 운동 기록이 존재하지 않습니다."));

        record.setExerciseName(request.getExerciseName());
        record.setDurationMinutes(request.getDurationMinutes());
        record.setExercisedAt(request.getExercisedAt());

        ExerciseRecord updated = repository.save(record);

        return new ExerciseResponseDto(
                updated.getId(),
                updated.getExerciseName(),
                updated.getDurationMinutes(),
                updated.getExercisedAt()
        );
    }

    // 기록 삭제
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID의 운동 기록이 존재하지 않습니다.");
        }
        repository.deleteById(id);
    }

    // 주간/월간 통계 (합계 포함)
    public ExerciseStatsDto getStats(LocalDate baseDate) {
        LocalDate today = (baseDate != null) ? baseDate : LocalDate.now();

        LocalDate weekStart = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate weekEnd = weekStart.plusDays(6);

        LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());

        // DB에서 전체 기록 가져오기
        List<ExerciseRecord> allRecords = repository.findAll();

        // 주간 합계
        int weeklyTotal = allRecords.stream()
                .filter(r -> !r.getExercisedAt().isBefore(weekStart) && !r.getExercisedAt().isAfter(weekEnd))
                .mapToInt(ExerciseRecord::getDurationMinutes)
                .sum();

        // 월간 합계
        int monthlyTotal = allRecords.stream()
                .filter(r -> !r.getExercisedAt().isBefore(monthStart) && !r.getExercisedAt().isAfter(monthEnd))
                .mapToInt(ExerciseRecord::getDurationMinutes)
                .sum();

        return new ExerciseStatsDto(
                weekStart, weekEnd, weeklyTotal,
                monthStart, monthEnd, monthlyTotal
        );
    }
}
