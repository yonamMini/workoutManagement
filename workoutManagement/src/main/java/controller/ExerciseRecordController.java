package controller;

import dto.ExerciseRequestDto;
import dto.ExerciseResponseDto;
import dto.ExerciseStatsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExerciseRecordService;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Workout Management App", description = "운동 기록 관리 앱 API")
@RestController
@RequestMapping("/api/exercises")
public class ExerciseRecordController {

    private final ExerciseRecordService exerciseService;

    public ExerciseRecordController(ExerciseRecordService exerciseService){
        this.exerciseService = exerciseService;
    }

    @Operation(
            summary = "운동 기록 생성",
            description = "운동 기록을 새로 등록합니다."

    )
    @ApiResponse(
            responseCode = "201",
            description = "생성 성공",
            content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))
    )
    @PostMapping
    public ResponseEntity<ExerciseResponseDto> create(
            @Valid @RequestBody ExerciseRequestDto request
    ) {
        ExerciseResponseDto created = exerciseService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "운동 기록 목록 조회",
            description = "등록된 모든 운동 기록을 정렬 기준에 따라 조회합니다."

    )
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))
    )
    @GetMapping
    public ResponseEntity<List<ExerciseResponseDto>> getList(
        @RequestParam(defaultValue = "newest") String sort
    ) {

        String normalized = switch (sort.toLowerCase()) {
            case "newest", "latest", "desc" -> "newest";
            case "oldest", "asc" -> "oldest";
            default -> "newest";
        };

        List<ExerciseResponseDto> exerciseLog = exerciseService.getListBySort(normalized);

        return ResponseEntity.ok(exerciseLog);
    }

    @Operation(
            summary = "운동 기록 수정",
            description = "지정한 ID의 기록을 전체 수정합니다."

    )
    @ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDto> update(
            @Parameter(description = "운동 기록 ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ExerciseRequestDto request
    ) {
        ExerciseResponseDto updated = exerciseService.update(id, request);

        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "운동 기록 삭제",
            description = "지정한 ID의 운동 기록을 삭제합니다."

    )
    @ApiResponse(
            responseCode = "204",
            description = "삭제 성공"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exerciseService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "운동 통계 조회",
            description = "주간/월간 집계 기간 정보를 반환합니다. 기준일을 지정할 수 있습니다."

    )
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = ExerciseStatsDto.class))
    )
    @GetMapping("/stats")
    public ResponseEntity<ExerciseStatsDto> getStats(
            @RequestParam(required = false) LocalDate baseDate
    ) {
        ExerciseStatsDto stats = exerciseService.getStats(baseDate);

        return ResponseEntity.ok(stats);
    }
}
