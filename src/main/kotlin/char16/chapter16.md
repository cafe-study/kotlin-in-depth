# 16.1 Ktor 소개

* 코틀린에 특화된, 네트워크를 통해 서로 통신하는 클라이언트와 서버 애플리케이션을 개발하기 위한 프레임워크
* 핵심 기능
    * 코틀린 DSL을 통해 애플레이션의 주요 설정을 간결하고 선언적인 스타일로 기술하고 코드의 다른 부분과 쉽게 조합할 수 있음
    * 코루틴을 통한 효율적인 비동기 연산을 지원

## 실습 - Ktor 프로젝트 생성하기

1. 플러그인 설치: ktor 플러그인을 설치한다. 설치가 되어있다면 넘어간다.
2. New Project > ktor 옵션을 선택
3. 대부분 기본 옵션을 선택하고, 사용할 기능은 HTML DSL을 선택

## 생성된 프로젝트 예제 파일 설명

### Application.kt

* main() - embeddedServer()와 같이 서버를 실행하는 코드가 들어감
* 모든 설정은 프로그램 코드 또는 HOCON(Human-Optimized Config Object Notation)을 통해 이루어짐

### Routing.kt

* 라우팅하는 부분의 코드

### Templating.kt

* HTML DSL을 이용한 코드

### ApplicationTest.kt

* 간단한 HTTP 요청을 보낸 후 응답으로 받은 결과를 검사할 수 있음

# 16.2 서버 관련 기능

### 서버 압축 기능

* Application.kt에서 아래와 같이 간단한 설정을 통해 압축이 가능

```kotlin
fun Application.configureHTTP() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
}
```

## 16.2.1 라우팅

* Routing.kt의 예제들 참고
* 라우팅에서 사용할 수 있는 매처 
  * header(name, value): 헤더 이름과 값을 매치
  * param(name, value): 요청 쿼리 파라미터의 이름과 값을 매치
  * param(name): 요청 쿼리 파라미터의 이름만 매치. 매치되는 이름이 있으면 그 값을 name으로 파라미터 맵에 저장해 줌 
  * optionalParam(name): 요청 쿼리 파라미터의 이름만 매치. 지정한 이름에 해당하는 요청 파라미터가 없어도 매치가 됨

## 16.2.2 HTTP 요청 처리

* Ktor의 요청/응답 처리 방법
    * call.responseText() - 텍스트 기반의 응답 반환, 원하는 경우 MIME 타입을 지정할 수 있음
    * call.respondTextWriter() - PrintWriter를 이용해 응답 작성
    * call.responseBytes() - 이진 데이터로 응답
    * call.responseFile() - 파일로 응답
    * call.status() - http 응답 상태 지정
    * call.header() - 응답시 http header 지정

## 16.2.3 HTML DSL

* HTML DSL을 사용하면 간결한 문법, 타입 안정성, 강력한 IDE 코드 인사이트 등과 같은 코틀린 코드의 모든 장점을 함께 얻을 수 있음

### 난수를 생성하는 간단한 웹 애플리케이션 예제

* (예제: ktor-random의 Routing.kt 참고) 
* HTML DSL에서 지원하는 입력 컴포넌트
  * input(), passwordInput(), numberInput(), dateInput
  * (), ... 

## 16.2.4 세션 지원

* (예제: ktor-session이 Security.kt 참고)
* 세션 기능을 사용하는 방법
```kotlin
    install(Sessions) {
    cookie<MyData>("my_data")
}
```
  * my_data: 세션의 키 
  * MyData: 세션 데이터를 표현하는 클래스. 불변 객체로 만드는 것이 타장함
  * get(), set() 메소드를 지원하며, getOrSet() 함수를 사용하면 존재하지 않는 세션 데이터를 초기화 할 수 있음

### 세션 트랜스포머
* 기본적으로 모든 세션 데이터는 클라이언트에 전송되고, 클라이언트는 세션 정보를 기억했다가 다음 요청을 보낼 때 함께 전송
  * 세션 데이터가 일반 텍스트로 저장될 경우 세션 정보가 오가는 것이 보안상 위험할 수 있음
  * 해시나 암호화를 이용해 세션 데이터에 대한 보안을 강화할 수 잇음

# 16.3 클라이언트 기능
## 16.3.1 요청과 응답
* (예제: ktor-client의 Application.kt 참고)
* httpClient
  * 제공하는 요청 메소드들이 일시 중단 함수이므로, 코루틴 문맥안에서 호출되어야 함 (runBlocking)
  * get, post, put, delete, patch, head, options 등 HTTP 1.x/2.x 표준이 제공하는 모든 요청방식을 처리하는 비슷한 함수를 지원
  * client.get<String>(url) 대신 ByteArray나 ByteReadChannel과 같은 비동기 데이터로도 받을 수 있음
  * close()를 통해 명시적으로 클라이언트를 종료해야 함

## 16.3.2 쿠키
* (예제: ktor-client의 PredefinedCookie.kt 참고)
* ConstantCookiesStorage - 항상 고정된 쿠키값을 반환