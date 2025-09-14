import { useEffect, useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip } from "recharts";

export default function ExerciseTracker() {
  // 운동 기록 데이터 상태
  const [exercises, setExercises] = useState([]);

  // 현재 수정 중인 운동 ID
  const [editingId, setEditingId] = useState(null);

  // 수정 입력값 상태
  const [editForm, setEditForm] = useState({ name: "", duration: 0, date: "" });

  // 주간/월간 차트 데이터 상태
  const [weeklyData, setWeeklyData] = useState([]);
  const [monthlyData, setMonthlyData] = useState([]);

  // 컴포넌트가 마운트될 때 운동 데이터 불러오기
  useEffect(() => {
    fetch("/api/exercises")
      .then(res => res.json())
      .then(data => {
        setExercises(data);
        calcStats(data); // 불러온 데이터 기반으로 통계 계산
      });
  }, []);

  // 주간/월간 운동 시간 총합 계산 함수
  const calcStats = (data) => {
    const now = new Date();

    // 주간 합계 (이번 주 일요일부터)
    const weekStart = new Date(now.setDate(now.getDate() - now.getDay()));
    const weekData = data.filter(e => new Date(e.date) >= weekStart);
    const weeklyTotal = weekData.reduce((acc, cur) => acc + cur.duration, 0);
    setWeeklyData([{ name: "This Week", total: weeklyTotal }]);

    // 월간 합계 (이번 달)
    const month = new Date().getMonth();
    const monthData = data.filter(e => new Date(e.date).getMonth() === month);
    const monthlyTotal = monthData.reduce((acc, cur) => acc + cur.duration, 0);
    setMonthlyData([{ name: "This Month", total: monthlyTotal }]);
  };

  // 수정 모드 활성화
  const handleEdit = (exercise) => {
    setEditingId(exercise.id);
    setEditForm({ name: exercise.name, duration: exercise.duration, date: exercise.date });
  };

  // 수정 저장 (PUT API 호출)
  const handleSave = (id) => {
    fetch(`/api/exercises/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(editForm)
    })
      .then(res => res.json())
      .then(updated => {
        // 수정된 항목 반영
        const newList = exercises.map(e => (e.id === id ? updated : e));
        setExercises(newList);
        calcStats(newList); // 통계 다시 계산
        setEditingId(null); // 수정 모드 종료
      });
  };

  // 삭제 (DELETE API 호출)
  const handleDelete = (id) => {
    fetch(`/api/exercises/${id}`, { method: "DELETE" })
      .then(() => {
        const newList = exercises.filter(e => e.id !== id);
        setExercises(newList);
        calcStats(newList); // 통계 다시 계산
      });
  };

  return (
    <div className="p-4 grid gap-6 md:grid-cols-2">
      {/* 운동 기록 목록 카드 */}
      <Card>
        <CardContent className="p-4">
          <h2 className="text-xl font-bold mb-4">운동 기록 목록</h2>
          <ul className="space-y-3">
            {exercises.map(ex => (
              <li key={ex.id} className="flex flex-col md:flex-row md:items-center justify-between bg-gray-50 p-3 rounded-xl">
                {/* 수정 모드일 때 */}
                {editingId === ex.id ? (
                  <div className="flex flex-col md:flex-row gap-2">
                    <Input value={editForm.name} onChange={e => setEditForm({ ...editForm, name: e.target.value })} />
                    <Input type="number" value={editForm.duration} onChange={e => setEditForm({ ...editForm, duration: Number(e.target.value) })} />
                    <Input type="date" value={editForm.date} onChange={e => setEditForm({ ...editForm, date: e.target.value })} />
                    <Button onClick={() => handleSave(ex.id)}>저장</Button>
                  </div>
                ) : (
                  // 일반 모드일 때
                  <>
                    <span>{ex.name} - {ex.duration}분 ({ex.date})</span>
                    <div className="flex gap-2 mt-2 md:mt-0">
                      <Button size="sm" onClick={() => handleEdit(ex)}>수정</Button>
                      <Button size="sm" variant="destructive" onClick={() => handleDelete(ex.id)}>삭제</Button>
                    </div>
                  </>
                )}
              </li>
            ))}
          </ul>
        </CardContent>
      </Card>

      {/* 통계 카드 (주간/월간) */}
      <Card>
        <CardContent className="p-4">
          <h2 className="text-xl font-bold mb-4">운동 시간 통계</h2>

          {/* 텍스트로 표시 */}
          <p className="mb-2">이번 주 총 운동시간: {weeklyData[0]?.total || 0}분</p>
          <p className="mb-4">이번 달 총 운동시간: {monthlyData[0]?.total || 0}분</p>

          {/* 차트로 표시 */}
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={[...weeklyData, ...monthlyData]}>
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Bar dataKey="total" fill="#3b82f6" radius={[8, 8, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
    </div>
  );
}
