package repository;


import com.example.workout.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {

    // 특정 기간의 운동 기록 조회 (주간/월간 통계용)
    List<ExerciseRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // 총합 시간 계산 (JPQL)
    @Query("SELECT SUM(e.duration) FROM ExerciseRecord e WHERE e.date BETWEEN :startDate AND :endDate")
    Integer sumDurationBetween(LocalDate startDate, LocalDate endDate);
}
