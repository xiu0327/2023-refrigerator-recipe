<div align=center>
  
<img width="397" alt="스크린샷 2023-02-27 오전 1 14 48" src="https://user-images.githubusercontent.com/78461009/221422412-f1827191-acb7-4738-afb6-664daa7a1f6a.png">
  
<h2>버려지는 식재료, 우리가 관리해줄게요</h1>
<br>
  <strong>냉장고 속 식재료 관리가 어려운 1인 가구를 위한 레시피 추천 서비스 !</strong>
<br>
<br>
  
[![Application](http://img.shields.io/badge/Application-F46A54?style=flat&logo=github&logoColor=white&link=)]()
[![Storybook](http://img.shields.io/badge/Storybook-FF4785?style=flat&logo=Storybook&logoColor=white&link=h)]()
[![API Docs](http://img.shields.io/badge/-API%20Docs-important?style=flat&logo=dev.to&logoColor=white&link=)]()
[![WIKI](http://img.shields.io/badge/-GitHub%20WiKi-395FC1?style=flat&logo=GitHub&logoColor=white&link=)]()
<br>
[![서비스_이용_가이드](http://img.shields.io/badge/-서비스_이용_가이드-81B441?style=flat&logo=Pinboard&logoColor=white&link=)]()
[![체험_가이드](http://img.shields.io/badge/-체험_가이드-6F53F3?style=flat&logo=Lemmy&logoColor=white&link=)]()

</div>

# 1. 기술 스택

## 1.1 ⚡️ 프론트엔드

<img width="940" alt="스크린샷 2023-02-27 오전 12 13 03" src="https://user-images.githubusercontent.com/78461009/221419907-607bc152-9e3b-4b4d-a4a2-2416c8796c1a.png">

React, TypeScript, Next.js가 냉장고를 부탁해의 프론트엔드에서 다루는 핵심 기술입니다.

- **React**: 냉장고를 부탁해의 대부분 페이지는 리액트로 개발되었습니다. 리액트는 컴포넌트 단위로 개발할 수 있어 확장성과 재사용성이 좋기 때문입니다.
- **TypeScript**: 냉장고를 부탁해 모든 코드는 TypeScript로 작성됩니다. JavaScript와 달리 코드 목적에 맞지 않는 함수 혹은 변수의 컴파일 에러를 발생시켜주기 때문입니다.
- **Next.js**: 사용자들이 저희 서비스를 검색했을 때 잘 찾을 수 있도록 Next.js를 사용합니다.

그 외에 아래 기술들을 보조적으로 사용하고 있습니다.

- **Jest**: 리액트 코드를 테스트하기 위해 사용됩니다. 보다 원활한 테스트 코드 작성을 위해 react testing library를 혼합하여 사용하기도 합니다.
- **Redux**: 상태관리를 효율적으로 관리하여 유지보수성을 높이기 위해 사용합니다.
- **React Query**: 비동기를 다루는 상황에서 사용하고 있는 라이브러리 입니다. 선언적으로 비동기 자원을 관리하고 캐싱할 수 있습니다.
- **SASS**: CSS의 재사용성을 높여 유지보수의 편의성을 높이고자 사용합니다.

냉장고를 부탁해의 자세한 프론트엔드 기술 스택 정보를 살펴보려면 여기를 클릭해주세요!

## 1.2 ⚡️ 백엔드

<img width="510" alt="스크린샷 2023-02-27 오전 12 02 44" src="https://user-images.githubusercontent.com/78461009/221419923-ec7f5cb4-d5e0-4b6e-abe9-d2ed448a0822.png">

SpringBoot, MySQL, Spring Data JPA가 냉장고를 부탁해의 백엔드에서 다루는 핵심 기술입니다.

- **SpringBoot**: 냉장고를 부탁해의 대부분 백엔드 서버는 SpringBoot로 개발되었습니다. DI, AOP, Spring MVC, 트랜잭션 관리 등 객체지향을 적극 활용할 수 있기 때문입니다.
- **MySQL**: 부하 분산을 위해 Master와 Slave로 이중화하여 사용합니다. 데이터베이스에 장애가 발생하면 빠르게 대응할 수 있고, 쓰기 작업과 읽기 작업을 구분하여 시스템의 성능을 높일 수 있기 때문입니다.
- **Spring Data JPA**: 순수 JPA로 개발할 때 발생하는 중복 코드를 줄이고자 사용합니다. 이외에도 Querydsl을 적극 활용하여 쿼리문을 쉽고 간편하게 작성합니다.

그 외에 아래 기술들을 보조적으로 사용하고 있습니다.

- **JUnit**: 테스트 코드를 작성하기 위해 사용하는 라이브러리입니다. SpringBoot에 기본적으로 내장되어 있어 별도의 설치없이 편리하게 사용할 수 있습니다.
- **Redis**: 캐시 서버나 JWT 토큰 저장소로 사용되는 서브 DB입니다. Key-Value 구조로 되어있어 데이터를 빠르게 조회할 수 있습니다.

냉장고를 부탁해의 자세한 백엔드 기술 스택 정보를 살펴보시려면 여기를 클릭해주세요!

## 1.3 ⚡️ 인프라

<img width="653" alt="스크린샷 2023-02-27 오전 12 16 37" src="https://user-images.githubusercontent.com/78461009/221419954-bd2b4174-c1c3-44cc-9a66-d537cd6099e6.png">

## 2. 팀 문화
