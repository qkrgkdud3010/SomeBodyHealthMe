# 🏋🏻‍♂️ SomeBodyHealthMe

헬스장 무인 운영을 위해 **출입·신체(InBody)·식단** 데이터를 통합 관리하는 Java 기반 웹 애플리케이션입니다.

| 구분 | 내용 |
|------|------|
| **기간** | 2024-10-24 ~ 2024-11-12 (4주) |
| **주요 스택** | Java, JSP/Servlet, Custom MVC, Oracle DB, JSTL, Jackson, Bootstrap |
| **배포 환경** | Apache Tomcat 10.x |

---

## 📌 Features
| 모듈 | 기능 요약 |
|------|-----------|
| **Entry** | 전화번호 인증 → 출입 로그 자동 기록, 관리자·회원별 이력 조회 |
| **Body / InBody** | 키·몸무게·BMI 및 월별 근육·체지방 데이터 저장, Chart.js 그래프 시각화 |
| **Diet** | 식단 계획·섭취 기록, 3대 영양소·칼로리 자동 합산·리포트 |
| **Admin** | 회원·출입·식단·InBody 데이터 일괄 관리 대시보드 |

---

## 🛠️ Tech Highlights
- **Action–DAO–VO 구조**로 모듈화 → 신규 인증 장비 연동 시 코드 수정 최소화
- `PreparedStatement` + 배치 삽입으로 **대량 식단 업로드 I/O 최적화**
- **Bootstrap Dashboard** + Chart.js로 직관적 UI/UX 구현

---

## 🚀 Quick Start

```bash
# 1. clone
git clone https://github.com/qkrgkdud3010/SomeBodyHealthMe.git
cd SomeBodyHealthMe

# 2. DB 세팅 (Oracle)
#   /sql/schema.sql 참고 후 테이블·시퀀스 생성

# 3. 설정
cp src/main/resources/jdbc-template.properties.sample jdbc-template.properties
# → DB URL / ID / PW 수정

# 4. 빌드 & 실행 (Tomcat)
mvn package
# WAR 배포 또는 IDE Tomcat 실행