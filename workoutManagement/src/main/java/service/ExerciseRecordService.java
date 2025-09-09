package service;

import com.example.workout.entity.ExerciseRecord;
import com.example.workout.repository.ExerciseRecordRepository;
import com.example.workout.dto.ExerciseStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseRecordService {

    private final ExerciseRecordRepository repository;

    // 운동 기록 등록
    public ExerciseRecord saveRecord(ExerciseRecord record) {
        return repository.save(record);
    }

    // 운동 기록 전체 조회
    public List<ExerciseRecord> getAllRecords() {
        return repository.findAll();
    }

    // 운동 기록 수정 (id 기준)
    public ExerciseRecord updateRecord(Long id, ExerciseRecord updated) {
        return repository.findById(id).map(record -> {
            record.setExerciseName(updated.getExerciseName());
            record.setDuration(updated.getDuration());
            record.setDate(updated.getDate());
            return repository.save(record);
        }).orElseThrow(() -> new RuntimeException("기록을 찾을 수 없습니다."));
    }

    // 운동 기록 삭제
    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }

    // 주간/월간 통계 조회
    public ExerciseStatsDto getStats(LocalDate startDate, LocalDate endDate) {
        Integer totalMinutes = repository.sumDurationBetween(startDate, endDate);
        if (totalMinutes == null) totalMinutes = 0;
        return new ExerciseStatsDto(totalMinutes, startDate, endDate);
    }
}