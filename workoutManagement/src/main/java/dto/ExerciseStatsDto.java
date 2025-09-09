package dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseStatsDto {

    private LocalDate weeklyRangeStart;
    private LocalDate weeklyRangeEnd;

    private LocalDate monthlyRangeStart;
    private LocalDate monthlyRangeEnd;
}
