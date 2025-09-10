import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwind from "@tailwindcss/vite"; // ⬅️ 추가

export default defineConfig({
  plugins: [react(), tailwind()], // ⬅️ 추가
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080", // 백엔드(Spring Boot) 주소
        changeOrigin: true,
      },
    },
  },
});
