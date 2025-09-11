package com.fweek.workoutManagement.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fweek.workoutManagement.entity.ExerciseRecord;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
}
