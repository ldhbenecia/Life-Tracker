# Life Tracker

Practice project using **Kotlin**, **Spring Boot**, **PostgreSQL**, **JDBC**, and **coroutines**.  
코틀린과 스프링 부트를 사용하여 가계부, 메모, 할일 관리를 연습하기 위한 프로젝트입니다.

## ✨ Overview

Life Tracker는 수입/지출 관리, 메모 작성, 할일 목록 기능을 제공하는 연습용 API 서버입니다.  
This project focuses on:
- Expense tracking
- Notes management
- Todo lists

**특징 / Features**
- Direct JDBC access (No JPA, No ORM)
- Kotlin Coroutine 기반 비동기 처리
- PostgreSQL 데이터베이스 사용

## 🛠️ Tech Stack

- Kotlin 1.9.25
- Spring Boot 3.4.5
- PostgreSQL
- Spring Data JDBC
- Kotlin Coroutines
- JUnit5 (for testing)

## 🚀 Getting Started

### 1. 프로젝트 클론 / Clone the repository

```bash
git clone https://github.com/your-username/life-tracker.git
cd life-tracker
```

## ⚙️ Configuration

1. Copy the example file:

```bash
cp src/main/resources/application-example.yml src/main/resources/application.yml
