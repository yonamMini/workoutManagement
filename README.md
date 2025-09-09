🏋️ 운동 기록 관리 앱 (Workout Management App)
📌 프로젝트 개요

사용자가 운동 기록을 등록 / 조회 / 수정 / 삭제할 수 있고,
최근 7일(주간) 및 이번 달(월간)의 운동 시간을 통계로 확인할 수 있는 웹 애플리케이션입니다.

✨ 주요 기능

운동 기록 등록 (Create)

운동명, 시간(분), 날짜 입력 후 저장

운동 기록 조회 (Read)

등록된 모든 운동 기록 리스트 확인

최신순 / 오래된순 정렬

운동 기록 수정 (Update)

잘못 입력한 운동 기록 수정 가능

운동 기록 삭제 (Delete)

필요 없는 기록 삭제

주간/월간 통계 (Stats)

최근 7일 운동 시간 합계 표시

이번 달 운동 시간 합계 표시

간단한 차트/텍스트 시각화 (프론트)

🛠 기술 스택

Frontend: React (CRA/Vite), Axios, Tailwind/Material UI

Backend: Spring Boot, Spring Data JPA, MySQL, Lombok

Tools: GitHub, Swagger/Postman, Git Flow

👥 역할 분담
🔵 프론트엔드

희동 (UI & 입력 기능 담당)

React 프로젝트 세팅

운동 기록 입력 폼 구현

POST /api/exercises 연동

기본 스타일링

성빈 (리스트 & 통계 담당)

운동 기록 목록 페이지 구현

수정/삭제 기능 연동

주간/월간 합계 표시 (차트)

반응형 UI

🟢 백엔드

국진 (DB & Service/Repository 담당)

MySQL DB 설계 (exercise_records 테이블)

Entity, Repository, Service 계층 개발

주간/월간 합계 통계 로직 구현

DB 연결 설정

정인 (Controller & API 담당)

Spring Boot 프로젝트 세팅

REST API Controller 작성

DTO 설계 (Request/Response)

API 테스트 (Swagger/Postman)

🌱 브랜치 전략 (Git Flow)

main: 안정화된 최종 배포 브랜치

develop: 개발 통합 브랜치

feature/...: 기능 개발 브랜치




