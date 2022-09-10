# 8.1 상속

## 8.1.1 하위 클래스 선언

### 클래스 상속

* 클래스의 상속은 : 으로 표시
* 코틀린 클래스는 기본적으로 final 이므로, 상속을 위해서는 수퍼클래스에 open을 추가해야 함
    * 깨지기 쉬운 기반 클래스 문제를 줄이기 위해 - base class가 상속을 고려하지 않고 설계되었을 경우 base class의 동작 변경이 하위 클래스의 올바르지 못한 동작을 야기시킬 수 있음.

### 특수한 클래스들의 상속

* data class는 open으로 선언할 수 없다.
* 인라인 클래스는 다른 클래스를 상속할 수 없고, 다른 클래스의 상위 클래스 역할을 할 수도 없음
* 객체는 다른 클래스를 상속 가능하나, 객체를 상속할 수는 없음

| 종류           | 다른 클래스를 상속할 수 있음 | open 선언 가능 (수퍼 클래스가 될 수 있음) |
|--------------|:----------------:|:---------------------------:|
| data class   |        O         |              X              |
| inline class |        X         |              X              |
| object       |        O         |              X              |

https://github.com/cafe-study/kotlin-in-depth/blob/f8df534b553c3c20a8eaa382d75cd0dbba3a587e/src/main/kotlin/chap8/Chap8_1_1.kt#L40-L62

### 임의 다형성

* 상위 클래스 멤버의 여러 다른 구현을 하위 클래스에게 제공하고, 런타임에 실제 인스턴스가 속한 클래스에 따라 구현을 선택해주는 기능

https://github.com/cafe-study/kotlin-in-depth/blob/f8df534b553c3c20a8eaa382d75cd0dbba3a587e/src/main/kotlin/chap8/Chap8_1_1-2.kt#L3-L23

### 멤버의 오버라이드

* 코틀린 함수와 프로퍼티도 기본적으로 final이므로, 상속하려면 open을 명시해야 함
    * 오버라이드한 멤버에는 override 키워드를 붙여야 함
    * 오버라이드는 동일한 시그니쳐를 사용해야 함
    * 반환 타입을 하위 타입(즉, 좀 더 구체적인 타입)으로 변경할 수 있음
    * 오버라이드하는 멤버를 final로 선언하면 더 이상 하위 클래스가 이 멤버를 오버라이드할 수 없음

```kotlin
open class Super {
    open fun start(): String? {
        println("I'm ridding")
    }
}

class Sub : Super() {
    final override fun start() = print("I'm ridding")
}
````

* 프로퍼티도 오버라이드 할 수 있다.
* 불변 프로퍼티를 가변 프로퍼티로 오버라이드 할수 있다. (하지만 이런 방식은 상위클래스에서 불변으로 강제한 값을 변경할 수 있게 하므로, 가급적 사용하지 않아야 한다.)

https://github.com/cafe-study/kotlin-in-depth/blob/f8df534b553c3c20a8eaa382d75cd0dbba3a587e/src/main/kotlin/chap8/Chap8_1_1-2.kt#L26-L32

### protected 변경자

* 자바와 동일하게 상속받는 클래스에서 해당 멤버를 접근가능하게 하는 접근자
* 자바는 같은 패키지 내 코드에서 protected에 접근 가능하지만 코틀린은 이런 접근이 금지된다. (코틀린에는 package private 개념의 접근자가 없음)

### super

* 상위 클래스 버전의 함수나 프로퍼티를 호출하고 싶을 때 사용

## 8.1.2 하위 클래스 초기화

### 상위 클래스의 초기화 코드 호출

* 코틀린은 별다른 코드 작성 없이도 상위 클래스의 초기화를 연쇄적으로 실행한다.

https://github.com/cafe-study/kotlin-in-depth/blob/aaafafce5eeeaf4ab14fbf062237e5021bb55c24/src/main/kotlin/chap8/Chap8_1_2.kt#L3-L29

### 상위 클래스의 생성자 호출하기

* 위임 호출 구문 사용 (예제 참고)

https://github.com/cafe-study/kotlin-in-depth/blob/aaafafce5eeeaf4ab14fbf062237e5021bb55c24/src/main/kotlin/chap8/Chap8_1_2_2.kt#L3-L14

### this 유출

* 코틀린의 null이 될 수 없는 타입의 변수 값이 null이 될 수도 있는 아주 드문 경우
* university 변수가 null이 나오는 이유
    * Person의 init이 호출되는 시점에 Student가 초기화되지 않아 university가 null인 상황

https://github.com/cafe-study/kotlin-in-depth/blob/aaafafce5eeeaf4ab14fbf062237e5021bb55c24/src/main/kotlin/chap8/Chap8_1_2_3.kt#L3-L28

## 8.1.3 타입 검사와 캐스팅

### is 연산자

* 자바의 instanceof 연산자와 동일한 역할을 수행
* null 처리 관점에서 차이가 있음
    * instanceof는 null에 대해 항상 false 반환
    * is는 오른쪽 타입이 null이 될수 있는지 여부에 따라 결과가 달라짐

https://github.com/cafe-study/kotlin-in-depth/blob/1051b68463eaed5367c8c022f9e2abe9ea4f2977/src/main/kotlin/chap8/Chap8_1_3.kt#L4-L11

### is 연산자의 스마트 캐스트

* is 연산자를 통해 타입이 확인된 내부 코드에서는 스마트 케이스가 지원

https://github.com/cafe-study/kotlin-in-depth/blob/1051b68463eaed5367c8c022f9e2abe9ea4f2977/src/main/kotlin/chap8/Chap8_1_3.kt#L12-L23

* 아래와 같은 상황에서는 스마트캐스트 제한 사항
    * 기본적으로 불변(val)에 대해서만 작동. 가변(var) 프로퍼티는 외부에서 변경할 수 있으므로 불가능
    * 프로퍼티나 커스텀 접근자가 정의된 변수
    * 위임을 사용하는 프로퍼티나 지역 변수
    * open 멤버 프로퍼티
    * 검사하는 시점과 변수를 읽는 시점 사이에 값을 변경한 경우

### as 연산자

* 어떤 값의 타입을 강제로 변경하기 위해 사용
* as?
    * as는 변환하려는 객체가 변환하려는 타입과 일치하지 않으면 에러를 발생시킴
    * as?는 변환하려는 객체가 변환하려는 타입과 일치하지 않으면 null을 반환

https://github.com/cafe-study/kotlin-in-depth/blob/1051b68463eaed5367c8c022f9e2abe9ea4f2977/src/main/kotlin/chap8/Chap8_1_3.kt#L25-L30

## 8.1.4 공통 메서드

* 코틀린의 모든 클래스는 Any를 직간접적으로 상속
    * JVN에서 Any의 런타임 값은 Object

```kotlin
open class Any {
    public open operator fun equals(other: Any?): Boolean
    public open fun hashCode(): Int
    public open fun toString(): String
}
```

* operator: 코틀린에서는 일부 지정된 연산자에 대한 오버라이드를 지원한다. (좀 더 자세한 내용은 11장 참고)

### 임의의 코틀린 클래스에 대한 커스텀 동등성 연산 구현

https://github.com/cafe-study/kotlin-in-depth/blob/ac8dd070a044982a6a555bf41aebe87983083774/src/main/kotlin/chap8/Chap8_1_4.kt#L3-L66

* 자바와 마찬가지로 equals() 메서드의 커스텀 구현은 대응하는 hashCode()와 서로 잘 조회되어야 한다.
    * 일부 컬랙션 (hashSet 등)은 hashCode()를 사용해 해시 테이블에서 원소가 들어갈 슬롯을 먼저 찾고, 그 후에 equals()를 통해 해시 코드가 같은 모든 후보를 검색한다.
* equals() 구현은 기본적으로 자바와 동일
    * 널이 아닌 객체는 널과 동일할 수 없음
    * 반사적(reflexive): 모든 객체는 자기 자신과 동일
    * 대칭적(symmetric): a == b 이면 b == a
    * 추이적(transitive): a == b 이고 b == c 이면 a == c
* Intellij의 관련 기능
    * equals(), hashCode() 기본 구현 제공 (Cmd + N > Generate > equals() and hashCode())
    * equals(), hashCode() 구현시 양쪽 모두를 구현하지 않은 경우 경고를 표시

### 배열의 동등성 구현

* contentEquals(), contentHashCode()
* 다차원 배열은 contentDeepEquals(), contentDeepHashCode()를 사용

### toString() 메소드

* 자바와 마찬가지로 모든 코틀린 클래스가 가지고 있음
* 자바와 마찬가지로 디폴트 구현은 클래스 이름 뒤에 객체 해시코드를 붙인 값을 반환
* Intellij toString() 기본 구현 제공 (Cmd + N > Generate > toString())

https://github.com/cafe-study/kotlin-in-depth/blob/ac8dd070a044982a6a555bf41aebe87983083774/src/main/kotlin/chap8/Chap8_1_4_2.kt#L14-L17

* Any?에 대한 toString() 확장 정의가 들어가 있어, null인 값에 대해서도 toString()을 안전하게 호출할 수 있음

https://github.com/cafe-study/kotlin-in-depth/blob/ac8dd070a044982a6a555bf41aebe87983083774/src/main/kotlin/chap8/Chap8_1_4_2.kt#L18-L20

# 8.2 추상 클래스와 인터페이스

## 8.2.1 추상 클래스와 추상 멤버

* 자바와 마찬가지로 코틀린도 추상 클래스를 지원 (자바와 거의 동일하므로 가볍게 특징만 짚어보고 넘어가자.)
    * 추상 클래스는 abstract 키워드를 붙여 정의 (open을 추가로 붙일 필요가 없음)
    * 추상 클래스는 인스턴스 생성이 불가
    * 추상 클래스 생성자는 오직 하위클래스의 생성자에서 위임 호출로만 호출할 수 있음
    * 추상 클래스는 추상 멤버를 정의할 수 있으며, 하위 클래스에서는 반드시 멤버를 오버라이드 해야 함
        * 추상 프로퍼티는 초기화 할 수 없고, 명시적인 접근자나 by 절을 추가할 수 없음
        * 추상 함수는 본문이 없어야 함
        * 추상 프로퍼티와 함수 모두 명시적으로 반환 타입을 지정해야 함

https://github.com/cafe-study/kotlin-in-depth/blob/0c7d4a3f06390c21e44aafa7bf1192c69b3fbede/src/main/kotlin/chap8/Chap8_2_1.kt#L3-L12

## 8.2.2 인터페이스

* 자바와 거의 동일하므로 가볍게 특징만 짚어보고 넘어가자
    * 메서드나 프로퍼티를 포함하지만 자체적인 인스턴스 상태나 생성자를 만들 수는 없는 타입
    * interface 키워드로 정의 (open을 붙일 필요는 없다)
    * 인터페이스 멤버는 디폴트가 추상 멤버 (abstract를 붙일 필요는 없다)
    * 인터페이스는 클래스나 다른 인터페이스의 상위 타입이 될 수 있다.
    * **인터페이스의 멤버를 상속해 구현할 경우에도 override 키워드를 추가해야 함**
    * **코틀린에서는 상속과 구현 모두 : 를 사용해 표시**
    * 인터페이스의 함수나 프로퍼티에 구현을 추가할 수 있음
    * 인터페이스의 멤버는 기본적으로 상속이 가능(open)하며, final로 정의하면 컴파일 오류가 발생한다.
        * **만약 인터페이스에 상속할 수 없는 멤버를 넣고 싶다면 확장함수를 이용하자**
    * 인터페이스 안에는 뒷받침하는 필드가 들어있는 프로퍼티를 정의할 수 없다. (상태를 사용할 수 없음 -> 다이아몬드 상속 문제를 방지하기 위해)
    * 인터페이스는 생성자를 사용할 수 없다.

https://github.com/cafe-study/kotlin-in-depth/blob/master/src/main/kotlin/chap8/Chap8_2_2.kt#L3-L77

### 인터페이스 다중상속

* 자바와 마찬가지로 코틀린 인터페이스도 다중 상속을 지원한다. (자바와 동일하므로, 조금 어려운 케이스만 짚어보고 넘어가자.)
* 동일한 시그니쳐를 가진 구현된 멤버를 가지는 인터페이스를 둘 이상 상속하는 경우, super를 통한 멤버 호출이 모호해진다. 이럴 때는 상위 타입을 한정시킨 super 키워드를 사용해야 한다. (사실 자바8도 동일하다)

https://github.com/cafe-study/kotlin-in-depth/blob/master/src/main/kotlin/chap8/Chap8_2_2_2.kt#L3-L26

## 8.2.3 봉인된 클래스와 인터페이스

https://github.com/cafe-study/kotlin-in-depth/blob/948dacb013224051dc47b308532a35df0913c324/src/main/kotlin/chap8/Chap8_2_3.kt#L3-L39

* 특정 클래스를 상속받는 서브 클래스를 제한하고 싶을 때 sealed 클래스를 사용할 수 있다.
* 봉인된 클래스를 사용하면
    * when 구문에서 불필요한 else를 쓰지 않아도 된다.
* 어떤 클래스를 sealed로 지정하면 이 클래스의 상속을 아래와 같이 제한할 수 있다.
    * 내포된 클래스 또는 객체
    * 같은 파일 안에서 최상위 클래스
* 상속 제한은 봉인된 클래스를 직접 상속한 클래스에 대해서만 성립한다. (즉, sealed 클래스를 상속한 클래스를 상속할 수 있음)

```kotlin
sealed class Result {
    class Success(val value: Any) : Result()
    open class Error(val message: String) : Result()
}

class FatalError(message: String) : Result.Error(message)
```

* (코틀린 1.1부터) 봉인된 클래스가 다른 클래스를 상속할 수도 있다. 즉 봉인된 클래스의 하위 클래스가 다시 봉인된 클래스가 될 수 있다.

```kotlin

import java.lang.NumberFormatException

sealed class Result

class Success(val value: Any) : Result()

sealed class Error : Result() {
    abstract val message: String
}

class ErrorWithException(val exception: Exception) : Error() {
    override val message: String
        get() = exception.message ?: ""
}

class ErrorWithMessage(override val message: String) : Error()
```

* 데이터 클래스와 봉인된 클래스를 함께 사용할 수 있다.

https://github.com/cafe-study/kotlin-in-depth/blob/948dacb013224051dc47b308532a35df0913c324/src/main/kotlin/chap8/Chap8_2_3_2.kt#L3-L27

* sealed 변경자는 인터페이스에 적용할 수 없다.
    * 즉, sealed 클래스의 하위 클래스는 sealed 클래스 하나만 상속할 수 있다. (다른 인터페이스를 상속할 수 없다는 뜻은 아니다. sealed가 인터페이스에도 적용되면 다중 구현이 가능하므로 문제가 될 수 있다.)

* 봉인된 클래스를 객체로 구현할 수도 있다.
    * 모든 직접 상속자가 객체라면 enum 처럼 동작한다.

```kotlin
sealed class Result {
    object Completed : Result()
    class ValueProduced(val value: Any) : Result()
    class Error(val message: String) : Result()
}
```

## 8.2.4 위임

* 기존 클래스를 확장하거나 변경해야 하는데, 상속할 수 없다면 위임 패턴을 사용할 수 있다.
  https://github.com/cafe-study/kotlin-in-depth/blob/948dacb013224051dc47b308532a35df0913c324/src/main/kotlin/chap8/Chap8_2_4.kt#L3-L34
* 위 접근 방식의 문제는 필요한 메서드나 프로퍼티를 다른 객체에 위임하기 위해 작성해야 하는 준비코드가 너무 많음
* 코틀린은 위임을 처리하는 기능을 내장하고 있음
    * 클래스 위임을 사용하면 번거로운 준비코드를 사용하지 않고도 객체 합성과 상속의 이점을 살릴 수 있다.
    * 상위 인터페이스 이름 바로 뒤에 by 키워드를 붙이고 그 다음에 위임할 인스턴스를 쓰면 됨

``` kotlin
  class Alias(
    private val realIdentity: PersonData,
    private val newIdentity: PersonData
  ): PersonData by newIdentity
```

* 구현을 바꾸고 싶다면 직접 오버라이드 할 수 있음
    * 위임과 객체 식을 조합하면 원래 객체와 약간 다른 구현을 만들때 유용하다.

```kotlin
class Alias(
    private val realIdentity: PersonData,
    private val newIdentity: PersonData
) : PersonData by newIdentity {
    override val age: Int
        get() = realIdentity.age
}

fun PersonData.aliased(newIdentity: PersonData) =
    object : PersonData by newIdentity {
        override val age: Int
            get() = this@aliased.age
    }
```
