import { useState } from "react";
import { api } from "../lib/api";
import type { ExercisePayload } from "../types";

const today = () => new Date().toISOString().slice(0, 10); // YYYY-MM-DD

export default function ExerciseForm() {
  // ✅ 상태
  const [name, setName] = useState("");
  const [minutes, setMinutes] = useState<number>(30);
  const [date, setDate] = useState<string>(today());
  const [loading, setLoading] = useState(false);

  // ✅ 유효성 검사
  const isValidName = name.trim().length > 0;
  const isValidMinutes =
    Number.isFinite(minutes) && minutes > 0 && minutes <= 600; // 10시간 제한 예시
  const isValidDate = /^\d{4}-\d{2}-\d{2}$/.test(date);
  const valid = isValidName && isValidMinutes && isValidDate;

  // ✅ 제출
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!valid) return alert("입력값을 확인해 주세요.");

    const payload: ExercisePayload = {
      name: name.trim(),
      minutes,
      date,
    };

    try {
      setLoading(true);
      await api.post("/api/exercises", payload); // Vite 프록시로 8080에 전달
      alert("운동 기록이 등록되었습니다!");

      // 폼 초기화
      setName("");
      setMinutes(30);
      setDate(today());
    } catch (err: any) {
      console.error(err);
      const msg = err?.response?.data?.message ?? err?.message ?? "요청 실패";
      alert(`등록 실패: ${msg}`);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="max-w-xl mx-auto">
      <h2 className="mb-4 text-2xl font-bold">운동 기록 입력</h2>

      <form
        onSubmit={handleSubmit}
        className="space-y-4 rounded-2xl border border-white/10 bg-white/5 p-6"
      >
        {/* 운동명 */}
        <div>
          <label className="mb-1 block font-medium">운동명</label>
          <input
            type="text"
            placeholder="예) 스쿼트"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full rounded-xl border bg-white/80 px-3 py-2 text-black focus:outline-none focus:ring"
            required
          />
          {!isValidName && (
            <p className="mt-1 text-sm text-red-300">운동명을 입력해 주세요.</p>
          )}
        </div>

        {/* 시간(분) */}
        <div>
          <label className="mb-1 block font-medium">운동 시간(분)</label>
          <input
            type="number"
            min={1}
            max={600}
            step={1}
            value={minutes}
            onChange={(e) => setMinutes(Number(e.target.value))}
            className="w-full rounded-xl border bg-white/80 px-3 py-2 text-black focus:outline-none focus:ring"
            required
          />
          {!isValidMinutes && (
            <p className="mt-1 text-sm text-red-300">
              1~600 사이의 숫자를 입력하세요.
            </p>
          )}
        </div>

        {/* 날짜 */}
        <div>
          <label className="mb-1 block font-medium">날짜</label>
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            className="w-full rounded-xl border bg-white/80 px-3 py-2 text-black focus:outline-none focus:ring"
            required
          />
          {!isValidDate && (
            <p className="mt-1 text-sm text-red-300">
              YYYY-MM-DD 형식의 날짜를 선택하세요.
            </p>
          )}
        </div>

        {/* 제출 */}
        <button
          type="submit"
          disabled={loading || !valid}
          className="w-full rounded-xl bg-indigo-600 py-2.5 font-semibold text-white disabled:opacity-50"
        >
          {loading ? "등록 중…" : "운동 기록 등록"}
        </button>
      </form>
    </div>
  );
}
