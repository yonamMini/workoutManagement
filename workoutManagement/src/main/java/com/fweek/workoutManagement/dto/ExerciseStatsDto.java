package com.fweek.workoutManagement.dto;

import java.time.LocalDate;

public class ExerciseStatsDto {

    private LocalDate weeklyRangeStart;
    private LocalDate weeklyRangeEnd;
    private int weeklyTotalMinutes;

    private LocalDate monthlyRangeStart;
    private LocalDate monthlyRangeEnd;
    private int monthlyTotalMinutes;

    public ExerciseStatsDto() {}

    public ExerciseStatsDto(LocalDate weeklyRangeStart, LocalDate weeklyRangeEnd, int weeklyTotalMinutes,
                            LocalDate monthlyRangeStart, LocalDate monthlyRangeEnd, int monthlyTotalMinutes) {
        this.weeklyRangeStart = weeklyRangeStart;
        this.weeklyRangeEnd = weeklyRangeEnd;
        this.weeklyTotalMinutes = weeklyTotalMinutes;
        this.monthlyRangeStart = monthlyRangeStart;
        this.monthlyRangeEnd = monthlyRangeEnd;
        this.monthlyTotalMinutes = monthlyTotalMinutes;
    }

    public LocalDate getWeeklyRangeStart() { return weeklyRangeStart; }
    public void setWeeklyRangeStart(LocalDate weeklyRangeStart) { this.weeklyRangeStart = weeklyRangeStart; }

    public LocalDate getWeeklyRangeEnd() { return weeklyRangeEnd; }
    public void setWeeklyRangeEnd(LocalDate weeklyRangeEnd) { this.weeklyRangeEnd = weeklyRangeEnd; }

    public int getWeeklyTotalMinutes() { return weeklyTotalMinutes; }
    public void setWeeklyTotalMinutes(int weeklyTotalMinutes) { this.weeklyTotalMinutes = weeklyTotalMinutes; }

    public LocalDate getMonthlyRangeStart() { return monthlyRangeStart; }
    public void setMonthlyRangeStart(LocalDate monthlyRangeStart) { this.monthlyRangeStart = monthlyRangeStart; }

    public LocalDate getMonthlyRangeEnd() { return monthlyRangeEnd; }
    public void setMonthlyRangeEnd(LocalDate monthlyRangeEnd) { this.monthlyRangeEnd = monthlyRangeEnd; }

    public int getMonthlyTotalMinutes() { return monthlyTotalMinutes; }
    public void setMonthlyTotalMinutes(int monthlyTotalMinutes) { this.monthlyTotalMinutes = monthlyTotalMinutes; }
}
