import axios from "axios";

export const api = axios.create({
  baseURL: "/", // 프록시로 /api/* 요청을 백엔드로 넘김
  headers: { "Content-Type": "application/json" },
});
