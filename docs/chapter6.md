# 6 특별한 클래스 사용하기

## 6.1 이넘 클래스

- 코틀린은 `enum class` 키워드 사용
- enum을 내부 클래스나 함수 본문에서 정의할 수 없다

### 6.1.1 빠뜨린 부분이 없는 when 식

- `when` 식에서 모든 enum 상수를 다른 경우에는 `else` 구문을 생략할 수 있다
    - 새로운 enum 값이 추가된다면 컴파일 시점에 오류가 발생한다
        - 암시적으로 else 에 `NoWhenBranchMatchedException` 을 발생시키는 코드가 들어간다
    - 코틀린의 when 구문에는  긴 이름으로 사용할 수 있다

### 6.1.2 커스텀 멤버가 있는 이넘 정의하기

- enum 클래스도 멤버를 포함할 수 있다. 확장함수나 프로퍼티를 붙일 수 있다.
    
    ```kotlin
    enum class WeekDay {
    	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY; //세미콜론!
    
    	val lowerCaseName get() = name.lowercase()
    	fun isWorkDay() = this == SATURDAY || this == SUNDAY
    }
    
    enum class RainbowColor(val isCold: Boolean) {
    	RED(false), ORANGE(false), YELLOW(false), GREEN(true), BLUE(true), INDIGO(true), VIOLET(true);
    
    	val isWarm get= !isCold
    }
    ```
    
- enum 상수 본문에 정의된 멤버를 해당 본문이 아닌 부분에서 접근할 수는 없다
    - enum class 자체나 상위 타입에 들어있는 가상 메서드를 구현하는 경우에 상수별 멤버 정의가 유용하다 (8장에서 추가 설명)
    
    ```kotlin
    enum class WeekDay {
    	MONDAY { fun startWork() == println("Work week started") },
    	TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
    
    fun main() = WeekDay.MONDAY.startWork() //ERROR
    ```
    

### 6.1.3 이넘 클래스의 공통 멤버 사용하기

- 코틀린의 모든 enum class는 kotlin.Enum 클래스의 하위 타입이다
- `name`: 이름
- `ordinal`: 값의 순서 (인덱스)
    - 비교연산( `<` , `>` )시 ordinal 값으로 비교한다
- `valueOf()`: enum의 이름을 String 파라미터로 넘기면 enum 값을 돌려주거나 IllegalArgumentException을 발생시킨다
- `values()`: 호출할 때 마다 배열이 새로 생긴다
- generic 최상위 메서드 : `enumValues<>()`, `enumValueOf<>()`
    
    ```kotlin
    fun main() {
    	val weekDays = enumValues<WeekDay>()
    
    	println(enumValueOf<WeekDay>("THURSDAY"))
    }
    ```
    

## 6.2 데이터 클래스

### 6.2.1 데이터 클래스와 데이터 클래스에 대한 연산

```kotlin
data class Person(val firstName: String, val familyName: String, val age: Int)

data class Mailbox(val address: String, val person: Person)

fun main() {
	val person1 = Person("John", "Doe", 25)
	val person2 = Person("John", "Doe", 25) 
	val person3 = person1

	println(person1 == person2) //true
	println(person1 == person3) //true

	val box1 = Mailbox("Unknown", Person("John", "Doe", 25))
	val box2 = Mailbox("Unknown", Person("John", "Doe", 25))

	println(box1 == box2) //true
}
```

- `equals()`, `hashCode()`, `toString()`
- 주 생성자의 파라미터에서 선언한 프로퍼티만 `equals()`, `hashCode()`, `toString()` 메서드 구현이 쓰인다.
    
    ```kotlin
    data class Person(val firstName: String, val familyName: String) {
    	var age = 0 // equals(), hashCode(), toString() 에 영향을 미치지 않음
    }
    ```
    
- 모든 데이터 클래스는 암시적으로 copy() 함수를 제공한다
    - 시그니처는 주 생성자와 동일하다. (보통 인자 구문에 이름을 붙여서 사용한다)
        - `person.copy(familyName = “Smith”)`
    - 인스턴스를 쉽게 복사할 수 있어서 불변 데이터 구조를 사용하기 쉽게 해준다.
- 범용 데이터 클래스 `Pair`, `Triple` 이 있다
    
    ```kotlin
    fun main() {
    	val pair = Pair(1, "two") // .first, .second
    	val pair2 = 1 to "two" // 중위 연산자로 생성하는 경우
    	val triple = Triple("one", 2, false) // .first, .second, .third
    ```
    

### 6.2.2 구조 분해 선언

```kotlin
data class Person(val firstName: String, val familyName: String, val age: Int)

fun main() {
	val (firstName, familyName, age) = Person("John", "Doe", 25)
	val (firstName, familyName: String, age) = Person("John", "Doe", 25)
	val (firstName, familyName) = Person("John", "Doe", 25)
	val (_, familyName) = Person("John", "Doe", 25)
	var (firstName, familyName, age) = Person("John", "Doe", 25)
}
```

- 매핑은 위치에 의해 결정되며, 변수 이름과는 무관하다
- 타입은 없지만 표기할 수 있다
- 사용하지 않는 부분은 `_` 로 대체할 수 있다
- `var` 도 가능하다
- for 루프에서 사용할 수도 있다
    
    ```kotlin
    for ((number, name) in pairs) {
    	println("$number: $name")
    }
    ```
    
- 람다 파라미터에 사용할 수도 있다
    
    ```kotlin
    fun combine(person1: Person,
    						person2: Person, 
    						folder: ((String, Person) -> String)): String {
    	return folder(folder("", person1), person2)
    }
    
    fun main() {
    	val p1 = Person("John", "Doe", 25)
    	val p2 = Person("John", "Doe", 26)
    
    	println(combine(p1, p2) { text, person -> "$text ${person.age}" })
    	println(combine(p1, p2) { text, (firstName) -> "$text $firstName" })
    	println(combine(p1, p2) { text, (_, familyName) -> "$text $familyName" })
    ```
    
- 지역 변수에서만 구조 분해 선언을 사용할 수 있다.
- 클래스 본문이나 파일의 최상위에 구조분해를 사용할 수는 없다.
- 구조분해 안에 다른 구조분해를 내포시킬 수는 없다.
- 데이터 클래스는 자동으로 구조 분해를 지원하지만, 아무 코틀린 타입이나 구조 분해를 구현할 수 있다 (연산자 오버로딩)

## 6.3 인라인 클래스 (값  클래스)

- 부가 비용 없이 래퍼클래스를 사용할 수 있다

### 6.3.1 인라인 클래스 정의하기

- 1.3까지 `inline` 키워드, 1.5부터는 `value` 키워드를 사용

```kotlin
@JvmInline //JVM 백엔드를 사용하는 경우 사용
value class Dollar(val amount: Int)

@JvmInline
value class Euro(val amount: Int)
```

- 인라인 클래스의 주생성자에는 불변 프로퍼티를 하나만 선언해야 한다
- 런타임에 클래스 인스턴스는 별도의 래퍼 객체를 생성하지 않고 이 프로퍼티의 값으로 표현된다.
- 인라인 클래스도 자체 함수와 프로퍼티를 포함할 수 있다

```kotlin
@JvmInline
inline class Dollar(val amount: Int) {
	fun add(d: Dollar) = Dollar(amount + d.amount)
	val isDebt get() = amount < 0
}

fun main() {
	println(Dollar(15).add(Dollar(20)).amount) //35
	println(Dollar(-10).isDebt) //true
}
```

- 인라인 클래스의 프로퍼티는 상태를 포함할 수 없다. (뒷바침필드나 Lateinit, lazy 등을 사용할 수 없다)
- 초기화 블록을 사용할 수 없다
- 사용하는 문맥에 따라서 박싱해야 하는 경우에는 인라인 되지 않는다.

```kotlin
fun safeAmount(dollar: Dollar?) = dollar?.amount ?: 0

fun main() {
	println(Dollar(15).amount) // 인라인 됨
	println(Dollar(15)) // Any? 로 사용되기 때문에 인라인 되지 않음
	println(safeAmount(Dollar(15))) // Dollar? 로 사용되기 때문에 인라인 되지 않음
}
```

### 6.3.2 부호 없는 정수

- `UByte`, `UShort`, `UInt`, `ULong`

```kotlin
val uByte: UByte = 1u
val uShort: UShort = 100u
val uInt = 1000u
val uLong: ULong = 1000u
val uLong2 = 1000uL
```

- 부호있는 타입과 서로 호환되지 않는다. `to~()` 를 통해 변환해야 한다.
- 연산(`+`, `-`, `*`, `/`, `%`)을 지원하지만, 부호있는 타입과는 혼합해서 사용할 수 없다.
- 단항 부호 반전 연산자(`-`)를 지원하지 않는다
- 증가(`++`), 감소(`--`), 복합대입(`+=`) 연산자를 사용할 수 있다
- 비트 반전, AND, OR, XOR 같은 비트 연산을 지원한다
- UInt와 ULong은 왼쪽(`shl`), 오른쪽(`shr`) 비트 시프트를 지원한다
- 비교연산자를 지원한다.
- 코틀린 표준 라이브러리에 부호없는 정수 배열을 표현하는 보조 타입이 있다
    - `UByteArray`, `UShortArray`, `UIntArray`, `ULongArray`
- 부호 있는 타입과 비슷한 방법으로 부호 없는 배열을 만들 수 있다
    
    ```kotlin
    val uByte = ubyteArrayOf(1u, 2u, 3u)
    val squares = UIntArray(10) { it.toUInt() * it.toUInt() }
    ```
    
- 이런 방식을 사용하기 위해서 `@ExperimentalUnsignedTypes`를 붙이거나 `-Xopt-in=kotlin.ExperimentalUnsignedTypes`를 옵션으로 추가해야 한다
- `..` 연산자나 `until` , `downTo`를 사용할 수도 있다
