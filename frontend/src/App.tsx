import { useEffect, useState } from "react";
import ExerciseForm from "./components/ExerciseForm";
import ExerciseTracker from "./components/ExerciseTracker";

interface Exercise {
  id: number;
  exerciseName: string;
  durationMinutes: number;
  exercisedAt: string;
}

interface ExerciseStats {
  weeklyRangeStart: string;
  weeklyRangeEnd: string;
  weeklyTotalMinutes: number;

  monthlyRangeStart: string;
  monthlyRangeEnd: string;
  monthlyTotalMinutes: number;
}

export default function App() {
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const [stats, setStats] = useState<ExerciseStats | null>(null);

  // 공통 데이터 로드
  const loadExercises = () => {
    fetch("/api/exercises")
      .then((res) => res.json())
      .then((data: Exercise[]) => setExercises(data));
  };

  const loadStats = () => {
    fetch("/api/exercises/stats")
      .then((res) => res.json())
      .then((data: ExerciseStats) => setStats(data));
  };

  useEffect(() => {
    loadExercises();
    loadStats();
  }, []);

  // 자식 컴포넌트에서 호출할 수 있는 갱신 함수
  const reloadAll = () => {
    loadExercises();
    loadStats();
  };

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100">
      <div className="mx-auto max-w-5xl p-6 grid gap-8">
        <header className="py-6">
          <h1 className="text-3xl font-extrabold md:text-4xl">
            운동 기록 관리 앱
          </h1>
        </header>

        {/* 운동 입력 */}
        <ExerciseForm onSuccess={reloadAll} />

        {/* 운동 기록 + 통계 */}
        <ExerciseTracker
          exercises={exercises}
          stats={stats}
          reloadAll={reloadAll}
        />
      </div>
    </div>
  );
}
