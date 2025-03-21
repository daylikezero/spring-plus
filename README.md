# SPRING PLUS

## 🧑‍🏫 프로젝트 소개
[📙[Spring 5기]플러스 주차 개인 과제](https://teamsparta.notion.site/Spring-5-1b22dc3ef51480d0918adeedd610fa97)<br>
<details>
  <summary><b>🌱 Level1 필수 기능</b></summary>
  <br/>
  <div>
    <ul>
      <li> 1. 코드 개선 퀴즈 - @Transactional의 이해 </li>
      <li> 2. 코드 추가 퀴즈 - JWT의 이해 </li>
      <li> 3. 코드 개선 퀴즈 -  JPA의 이해 </li>
      <li> 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해 </li>
      <li> 5. 코드 개선 퀴즈 - AOP의 이해 </li>
    </ul>
  </div>
</details>
<details>
  <summary><b>🌿 Level2 필수 기능</b></summary>
  <br/>
  <div>
    <ul>
      <li>6. JPA Cascade </li>
      <li>7. N+1 </li>
      <li>8. QueryDSL </li>
      <li>9. Spring Security </li>
    </ul>
</details>
<details>
  <summary><b>🪴 Level3 도전 기능</b></summary>
  <br/>
  <div>
    <ul>
      <li>10. QueryDSL 을 사용하여 검색 기능 만들기 </li>
      <li>11. Transaction 심화 </li>
      <li>12. AWS 활용 </li>
      <li>13. 대용량 데이터 처리 </li>
    </ul>
</details>
<details>
  <summary><b>🌳 Level4 Kotlin 적용하기</b></summary>
  <br/>
  <ul>
      <li>14. Entity 및 Repository CRUD 리팩토링(Kotlin) </li>
      <li>15. Kotlin으로의 전환 ❎</li>
   </ul>
</details>

## 🗓️ 개발 기간
2025.03.10(월) ~ 2025.03.21(금)

## 🧑‍🔬 과제
### 12. AWS 활용
* EC2, RDS, S3를 사용하여 프로젝트를 관리하고 배포
* 각 AWS 서비스 간 보안 그룹을 구성
  
![AWS_구조](https://github.com/user-attachments/assets/bf43a150-3ea7-40dc-8ad4-9903a3592e58)

<hr>

### 12-1. EC2
* 인스턴스 설정
  * 탄력적 IP 설정

![12-1](https://github.com/user-attachments/assets/27fadfcf-66a7-4368-9d87-0ced9d91a37d)

* EC2 인스턴스 접속 - 서버 가동

![12-1-2](https://github.com/user-attachments/assets/0b056c9f-a01a-4bbf-b8e1-1a7b271c13a8)

* 서버 접속 및 Live 상태 확인 - health check API

![12-1-3](https://github.com/user-attachments/assets/2aba59eb-96b8-4863-b7b9-569a2cb6596a)

<hr>

### 12-2. RDS
![12-2](https://github.com/user-attachments/assets/4d1736f5-13ed-40ac-a6e3-419c5dd6a4b3)

![12-2-2](https://github.com/user-attachments/assets/c15d223b-9416-4d6a-a5e1-de6855dbd692)

<hr>

### 12-3. S3
![12-3](https://github.com/user-attachments/assets/010e2f3d-26c0-4374-918f-7ac8099bfe5a)

![12-3-2](https://github.com/user-attachments/assets/931bef68-b466-4052-a6d5-8fc23eb6946f)

<hr>

### 🎬 검증 시나리오
1. Elastic IP 주소로 접속하여 회원가입<br>
![image](https://github.com/user-attachments/assets/e4aa3f0b-aacf-48ea-873d-0883da84a05f)

2. RDS 유저 조회 - 프로필 이미지 URI 확인<br>
![image](https://github.com/user-attachments/assets/9e9ba552-6abf-463f-a65a-bcd0befe9f86)

3. S3 버킷 Object 일치 확인<br>
![image](https://github.com/user-attachments/assets/e56be4ba-427e-4844-8372-13dc1eaa9850)
![image](https://github.com/user-attachments/assets/8fcb6ae2-cfbc-4c4c-9808-5570d3ae776f)



## ⚙ 개발 환경
- <img src="https://img.shields.io/badge/Java-007396?&style=for-the-badge&logo=java&logoColor=white" /><img src="https://img.shields.io/badge/gradle-%2302303A.svg?&style=for-the-badge&logo=gradle&logoColor=white" /><img src="https://img.shields.io/badge/spring-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white" /><img src="https://img.shields.io/badge/mysql-%234479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white" />
- <img src="https://img.shields.io/badge/Amazon_AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white" /><img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" />
- JDK: `corretto-17 Amazon Corretto 17.0.13 - aarch64`

## 🔫 트러블 슈팅
[[Spring]_플러스_주차_개인과제_-_Level1](https://velog.io/@daylikezero/Spring-%ED%94%8C%EB%9F%AC%EC%8A%A4-%EC%A3%BC%EC%B0%A8-%EA%B0%9C%EC%9D%B8%EA%B3%BC%EC%A0%9C-Level1)<br>
[[Spring]_플러스_주차_개인과제_-_Level2](https://velog.io/@daylikezero/Spring-%ED%94%8C%EB%9F%AC%EC%8A%A4-%EC%A3%BC%EC%B0%A8-%EA%B0%9C%EC%9D%B8%EA%B3%BC%EC%A0%9C-Level2)<br>
[[Spring]_플러스_주차_개인과제_-_Level3](https://velog.io/@daylikezero/Spring-%ED%94%8C%EB%9F%AC%EC%8A%A4-%EC%A3%BC%EC%B0%A8-%EA%B0%9C%EC%9D%B8%EA%B3%BC%EC%A0%9C-Level3)<br>
[[Spring]_플러스_주차_개인과제_-_Level4](https://velog.io/@daylikezero/Spring-%ED%94%8C%EB%9F%AC%EC%8A%A4-%EC%A3%BC%EC%B0%A8-%EA%B0%9C%EC%9D%B8%EA%B3%BC%EC%A0%9C-Level4)<br>
