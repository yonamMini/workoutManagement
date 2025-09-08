package dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseResponseDto {

    private Long id;
    private String exerciseName;
    private int durationMinutes;
    private LocalDate exercisedAt;
}
