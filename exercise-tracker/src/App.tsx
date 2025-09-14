import ExerciseForm from "./components/ExerciseForm";

export default function App() {
  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100">
      <div className="mx-auto max-w-3xl p-6">
        <header className="py-6">
          <h1 className="text-3xl font-extrabold md:text-4xl">
            운동 기록 관리 앱
          </h1>
        </header>

        <ExerciseForm />
      </div>
    </div>
  );
}
