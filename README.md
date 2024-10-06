# 서버 통합 로그인 (Single Sign-On)
<p align="center">
<br> <img src=./src/main/java/kr/co/test/image/SSOArchitecture.png width="90%" /> <br>
</p>
Spring을 사용해 만든 통합 로그인 RESTful API입니다.
<br/>

## 🖥️ 프로젝트 소개
항상 프로젝트에 사용되는 로그인 기능을 분리해 만든 통합 로그인 서버입니다. 팀에서 개발한 여러 서버의 로그인 기능을 통합해 처리합니다. 또한, JWT 토큰을 사용해 사용자 정보를 관리하며, 다른 서비스로 이동하더라도 로그인 상태가 유지됩니다.
<br>

## ⏰ 개발 기간
* 23.05.19 ~ 23.07.18

### ⚙️ 기술 스택

- **Language** : Java 
- **Framework** : Spring
- **Library** : Spring Security
- **Database** : MySQL
- **ORM** : Mybatis

## ERD
<p align="center">
<br> <img src=./src/main/java/kr/co/test/image/SSOERD.png width="90%" /> <br>
</p>

## 동작 원리

<p align="center">
<br> <img src=./src/main/java/kr/co/test/image/SSOLogic.png width="90%" /> <br>
</p>
