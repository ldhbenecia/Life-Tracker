# Life Tracker API Server

코틀린과 스프링 부트를 기반으로 개인의 일상 활동(할 일, 메모 등)을 관리하고 추적하기 위한 백엔드 API 서버입니다. 본 프로젝트는 확장성 높은 다중 모듈 아키텍처와 이벤트 기반 통신 방식을 적용하여 개발 및 학습을 목표로 합니다.

## 1. 주요 기능 (Core Features)

-   **사용자 관리 (User Management)**: 회원 가입, 인증, 프로필 관리 등 사용자 계정 관련 기능을 제공합니다.
-   **할 일 관리 (Todo Management)**: 할 일 목록 생성, 수정, 상태 변경 기능을 제공하며, 친구(Friend) 기능 및 실시간 채팅(Chat) 기능과 연동됩니다.
-   **메모 관리 (Memo Management)**: 텍스트 기반의 간단한 메모를 생성하고 관리하는 기능을 제공합니다.

## 2. 아키텍처 (Architecture)

본 프로젝트는 서비스의 명확한 역할 분리와 유연한 확장을 위해 **다중 모듈 구조(Multi-module Architecture)**를 채택하고 있습니다. 또한, 실시간 및 비동기 통신을 위해 이벤트 기반 아키텍처 요소를 도입합니다.

### 2.1. 모듈 구성 (Module Configuration)

-   **`client`**: API 엔드포인트 정의, 외부 요청 처리 및 응답을 담당하는 프레젠테이션 계층입니다.
-   **`core`**: 도메인별 핵심 비즈니스 로직을 구현하는 서비스 계층입니다. 외부 환경에 대한 의존성을 최소화합니다.
-   **`storage`**: 데이터 영속성(Persistence) 처리를 전담하는 인프라스트럭처 계층입니다. (JPA, Redis, NoSQL 등)
-   **`common`**: 여러 모듈에서 공통으로 사용되는 코드, 예외 처리, 유틸리티 등을 제공하는 공통 계층입니다.

### 2.2. 통신 및 데이터 처리 전략

-   **Apache Kafka**: `Todo` 관련 기능(친구 추가, 채팅 메시지 등) 처리 시 발생하는 이벤트를 비동기적으로 발행(Produce) 및 구독(Consume)하여 시스템 간 결합도를 낮추고 응답성을 향상시키기 위해 사용됩니다.
-   **WebSocket & STOMP**: 실시간 양방향 통신을 구현하여 사용자 간 채팅 기능을 제공하기 위해 사용됩니다.
-   **Redis**: 주요 데이터 조회 성능 최적화를 위한 캐싱(Caching) 계층으로 활용될 예정입니다.

## 3. 기술 스택 (Technical Stack)

| 구분 | 기술                                                      |
| :--- |:--------------------------------------------------------|
| **Language** | Kotlin `1.9.25`                                         |
| **Framework** | Spring Boot `3.5.0`                                     |
| **Data Access** | Spring Data JPA, Spring Data Redis, QueryDSL            |
| **Database** | MySQL (RDBMS), Redis (In-Memory Cache), NoSQL (MongoDB) |
| **Messaging** | Apache Kafka, WebSocket & STOMP                         |
| **Testing** | JUnit 5, Kotest                                         |
| **Build** | Gradle (Kotlin DSL)                                     |
