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
|--------------|------------------|-----------------------------|
| data class   | O                | X                           |
| inline class | X                | X                           |
| object       | O                | X                           |

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