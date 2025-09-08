package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
