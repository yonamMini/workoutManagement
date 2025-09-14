import { useState } from "react";
import { api } from "../lib/api";
import type { ExercisePayload } from "../types";

const today = () => new Date().toISOString().slice(0, 10); // YYYY-MM-DD

export default function ExerciseForm({ onSuccess }: { onSuccess: () => void }) {
  const [name, setName] = useState("");
  const [minutes, setMinutes] = useState<string>("30"); // ✅ 문자열로 관리
  const [date, setDate] = useState<string>(today());
  const [loading, setLoading] = useState(false);

  const isValidName = name.trim().length > 0;
  const isValidMinutes =
    minutes !== "" && // 빈 문자열("")일 때는 false
    Number.isFinite(Number(minutes)) &&
    Number(minutes) > 0 &&
    Number(minutes) <= 600;

  const isValidDate = /^\d{4}-\d{2}-\d{2}$/.test(date);
  const valid = isValidName && isValidMinutes && isValidDate;

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!valid) return alert("입력값을 확인해 주세요.");

    const payload: ExercisePayload = {
      exerciseName: name.trim(),
      durationMinutes: Number(minutes), // ✅ 문자열 → 숫자 변환
      exercisedAt: date,
    };

    try {
      setLoading(true);
      await api.post("/api/exercises", payload);
      alert("운동 기록이 등록되었습니다!");

      // 부모(App)의 데이터 갱신
      onSuccess();

      // 폼 초기화
      setName("");
      setMinutes("30");
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
        className="space-y-4 rounded-2xl border border-white/10 bg-white/5 p-6">
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
        </div>

        <div>
          <label className="mb-1 block font-medium">운동 시간(분)</label>
          <input
            type="number"
            min={1}
            max={600}
            step={1}
            value={minutes}
            onChange={(e) => setMinutes(e.target.value)} // 빈 값("") 그대로 유지 가능
            placeholder="시간을 입력하세요" // ✅ 사용자가 다 지우면 이게 뜸
            className="w-full rounded-xl border bg-white/80 px-3 py-2 text-black focus:outline-none focus:ring"
            required
          />
        </div>

        <div>
          <label className="mb-1 block font-medium">날짜</label>
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            className="w-full rounded-xl border bg-white/80 px-3 py-2 text-black focus:outline-none focus:ring"
            required
          />
        </div>

        <button
          type="submit"
          disabled={loading || !valid}
          className="w-full rounded-xl bg-indigo-600 py-2.5 font-semibold text-white disabled:opacity-50">
          {loading ? "등록 중…" : "운동 기록 등록"}
        </button>
      </form>
    </div>
  );
}
