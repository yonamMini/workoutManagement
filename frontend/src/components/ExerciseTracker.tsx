import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

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

export default function ExerciseTracker({
  exercises,
  stats,
  reloadAll,
}: {
  exercises: Exercise[];
  stats: ExerciseStats | null;
  reloadAll: () => void;
}) {
  const handleDelete = (id: number) => {
    fetch(`/api/exercises/${id}`, { method: "DELETE" }).then(() => reloadAll());
  };

  return (
    <div className="p-4 grid gap-6 md:grid-cols-2">
      <div className="rounded-xl border border-zinc-700 bg-zinc-800 p-4">
        <h2 className="text-xl font-bold mb-4">운동 기록 목록</h2>
        <ul className="space-y-3">
          {exercises.map((ex) => (
            <li
              key={ex.id}
              className="flex flex-col md:flex-row md:items-center justify-between bg-zinc-900 p-3 rounded-xl">
              <span>
                {ex.exerciseName} - {ex.durationMinutes}분 ({ex.exercisedAt})
              </span>
              <button
                className="rounded bg-red-500 px-3 py-1 text-white mt-2 md:mt-0"
                onClick={() => handleDelete(ex.id)}>
                삭제
              </button>
            </li>
          ))}
        </ul>
      </div>

      <div className="rounded-xl border border-zinc-700 bg-zinc-800 p-4">
        <h2 className="text-xl font-bold mb-4">운동 시간 통계</h2>
        {stats ? (
          <>
            <p className="mb-2">
              이번 주 총 운동시간: {stats.weeklyTotalMinutes}분 <br />(
              {stats.weeklyRangeStart} ~ {stats.weeklyRangeEnd})
            </p>
            <p className="mb-4">
              이번 달 총 운동시간: {stats.monthlyTotalMinutes}분 <br />(
              {stats.monthlyRangeStart} ~ {stats.monthlyRangeEnd})
            </p>

            <ResponsiveContainer width="100%" height={300}>
              <BarChart
                data={[
                  { name: "This Week", total: stats.weeklyTotalMinutes },
                  { name: "This Month", total: stats.monthlyTotalMinutes },
                ]}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="total" fill="#3b82f6" radius={[8, 8, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </>
        ) : (
          <p>통계 불러오는 중...</p>
        )}
      </div>
    </div>
  );
}
