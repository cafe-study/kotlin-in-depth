# 16.1 Ktor 소개
* 코틀린에 특화된, 네트워크를 통해 서로 통신하는 클라이언트와 서버 애플리케이션을 개발하기 위한 프레임워크
* 핵심 기능
  * 코틀린 DSL을 통해 애플레이션의 주요 설정을 간결하고 선언적인 스타일로 기술하고 코드의 다른 부분과 쉽게 조합할 수 있음
  * 코루틴을 통한 효율적인 비동기 연산을 지원

## 실습 - Ktor 프로젝트 생성
1. 플러그인 설치: ktor 플러그인을 설치한다. 설치가 되어있다면 넘어간다.
2. New Project > ktor 옵션을 선택
3. 대부분 기본 옵션을 선택하고, 사용할 기능은 HTML dsl을 선택

### 예제 파일 설명

https://github.com/cafe-study/kotlin-in-depth/blob/40050b171fbca658f6283d4c3821822248a24486/ktor-example/src/main/kotlin/com/example/Application.kt#L9-L11

* main() - 서버를 실행하는 코드가 위치
* 모든 설정은 프로그램 코드 또는 HOCON(Human-Optimized Config Object Notation)을 통해 이루어짐
 
https://github.com/cafe-study/kotlin-in-depth/blob/40050b171fbca658f6283d4c3821822248a24486/ktor-example/src/main/kotlin/com/example/plugins/Routing.kt#L13-L13
* 라우팅하는 부분의 코드

https://github.com/cafe-study/kotlin-in-depth/blob/40050b171fbca658f6283d4c3821822248a24486/ktor-example/src/main/kotlin/com/example/plugins/Templating.kt#L13-L13
* html dsl 코드

https://github.com/cafe-study/kotlin-in-depth/blob/40050b171fbca658f6283d4c3821822248a24486/ktor-example/src/test/kotlin/com/example/ApplicationTest.kt#L16-L27
* test 코드