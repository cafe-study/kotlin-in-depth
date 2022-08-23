# 5 고급 함수와 함수형 프로그래밍 활용하기

함수형 프로그래밍을 돕는 고차 함수, 람다, 호출 가능 참조 등의 코틀린 언어 기능을 배우고, 기존 타입을 더 보완할 수 있는 확장 함수나 프로퍼티 사용법을 배운다.

## 5.1 코틀린을 활용한 함수형 프로그래밍

### 5.1.1 고차 함수

예제 - 함수의 파라미터로 적당한 연산을 제공

```kotlin
fun aggregate(numbers: IntArray, op: (Int, Int) -> Int): Int {
	var result = numbers.firstOrNull()
		?: throw IllegalArgumentException("Empty array")

	for (i in 1..numbers.lastIndex) result = op(result, numbers[i])

	return result
}

fun sum(numbers: IntArray) = aggregate(numbers, { result, op -> result + op })

fun max(numbers: IntArray) = aggregate(numbers, { result, op -> if (op > result) op else result })
```

### 5.1.2 함수 타입

- 괄호로 둘러싸인 파라미터 타입 목록은 함숫값에 전달될 데이터의 종류와 수를 정의한다.
    - 함수가 인자를 받지 않는 경우에는 빈 괄호 사용 (`() -> Unit`)
    - 파라미터 타입을 둘러싼 괄호는 필수
- 반환 타입은 함수 타입의 함숫값을 호출하면 돌려받게 되는 값의 타입을 정의한다.
    - 반환값이 없어도 반드시 명시 (Unit)
- 예) (Int, Int) → Boolean
- 예) 호출방식: op(a, b) 또는 op.invoke(a, b)
- 함수타입은 변수에 저장가능
    - `val lessThan: (Int, Int) -> Boolean = { a, b -> a < b }`
    - 변수 타입을 생략하면 파라미터 타입추론이 불가능. 이런 경우에는 파라미터 타입을 명시
    - `val lessThan = { a: Int, b: Int -> a < b }`
- 함수 타입도 널이 될 수 있는 타입으로 지정 가능
    - `fun measureTime(action: (() -> Unit)?): Long { ... }`
- 함수 타입을 다른 함수 타입 안에 내포시켜서 고차 함수 타입을 정의할 수 있다
    
    ```kotlin
    fun main() {
    	val shifter: (Int) -> (Int) -> Int = { n -> { i -> i + n } }
    	val inc = shifter(1)
    	val dec = shifter(-1)
    
    	println(inc(10)) //11
    	println(dec(10)) //9
    }
    ```
    
- `->`는 오른쪽 결합이다
    
    ```kotlin
    fun main() {
    	val evalAtZero: ((Int) -> (Int)) -> Int = { f -> f(0) }
    	println(evalAtZero { n -> n + 1 }) //1
    	println(evalAtZero { n -> n - 1 }) //-1
    }
    ```
    
- 함수 타입의 파라미터 목록에 들어가는 파라미터 이름은 문서화를 위한 것
- [자바 vs 코틀린]
    - Java에서 SAM(Single Abstract Method) 인터페이스를 람다식이나 메서드 참조로 인스턴스화 하도록 지원하고 있음
        - 예) `Consumer<String> consume = s -> System.out.println(s);`
    - Kotlin 에서는 인터페이스 앞에 ``fun`` 을 붙여서 SAM 인터페이스로 취급 ([https://kotlinlang.org/docs/fun-interfaces.html](https://kotlinlang.org/docs/fun-interfaces.html))

### 5.1.3 람다와 익명 함수

- 함수형 타입의 값을 만드는 방법 : 람다식을 사용하는 것
- 람다식
    - 예) `{ result, op -> result + op }`
        - 파라미터 목록: result, op
        - 람다식의 몸통(본문)이 되는 식이나 문의 목록: result + op
    - 반환타입을 지정할 필요가 없다 (람다식 본문으로부터 자동으로 추론된다)
    - 파라미터 목록에 괄호가 없다 (파라미터를 괄호로 감싸면 구조문해 선언이 된다
    - 람다가 마지막 파라미터인 경우, 함수를 호출할 때 인자를 둘러싸는 괄호 밖에 이 람다를 위치시킬 수 있다
        
        ```kotlin
        fun sum(numbers: IntArray) = aggregate(numbers) { result, op -> result + op }
        fun max(numbers: IntArray) = aggregate(numbers) { result, op -> if (op > result) op else result }
        ```
        
    - 람다에 인자가 없으면 화살표 기호를 생략할 수 있다
        
        ```kotlin
        fun measureTime(action: () -> Unit): Long {
        	val start = System.nanoTime()
        	action()
        	return System.nanoTime() - start
        }
        
        val time = measureTime { 1 + 2 }
        ```
        
    - 인자가 하나밖에 없는 람다를 단순화해 사용할 수 있는 문법을 제공한다 (유일한 파라미터를 it 으로 지칭한다)
        
        ```kotlin
        fun check(s: String, condition: (Char) -> Boolean): Boolean {
        	for (c in s) {
        		if (!condition(c)) return false
        	}
        	return true
        }
        
        fun main() {
        	println(check("Hello") { it.isLowerCase() })
        }
        ```
        
    - 사용하지 않는 람다 파라미터를 밑줄 기호로 지정할 수 있다
        
        ```kotlin
        fun check(s: String, condition: (Int, Char) -> Boolean): Boolean {
        	for (i in s.indices) {
        		if (!condition(i, s[i])) return false
        	}
        	return true
        }
        
        println(check("Hello") { _, c -> c.isLetter() })
        ```
        
- 함수값을 만드는 다른 방법: 익명 함수를 사용하는 것
    - 예) `fun sum(numbers: IntArray) = aggregate(numbers, fun(result, op) = result + op)`
    - fun 키워드 다음에 바로 파라미터 목록이 온다. (이름이 없음)
    - 파라미터 타입을 추론할 수 있으면 파라미터 타입을 지정하지 않아도 된다.
    - 익명 함수는 식이기 때문에 인자로 함수에 넘기거나 변수에 대입하는 등 일반 값처럼 쓸 수 있다.
    - 반환 타입을 적을 수 있다.
    함수 본문이 식인 경우 반환 타입을 생략할 수 있고, 블록인 경우 명시적으로 지정해야 한다.
    - 익명 함수를 인자 목록의 밖으로 내보낼 수는 없다.
- 기타
    - 람다와 익명함수도 클로저, 또는 자신을 포함하는 외부 선언에 정의된 변수에 접근할 수 있다
    - 람다나 익명함수도 외부 영역의 가변 변수 값을 변경할 수 있다
    - [자바 vs 코틀린] 자바의 람다는 외부 변수의 값을 변경할 수 없다.

### 5.1.4 호출 가능 참조

호출가능참조 (callable reference) : 함수 정의를 함숫값 처럼 고차 함수에 넘기는 방법

```kotlin
fun check(s: String, condition: (Char) -> Boolean): Boolean {
	for (c in s) {
		if (!condition(c)) return false
	}
	return true
}

fun isCapitalLetter(c: Char) = c.isUpperCase() && c.isLetter()

fun main() {
	println(check("Hello", ::isCapitalLetter))
}
```

- 다른 패키지에 들어있는 함수의 호출 가능 참조를 만들려면 먼저 함수를 임포트 해야 한다.
- `::`을 클래스 이름 앞에 사용하면 클래스 생성자에 대한 호출 가능 참조를 얻는다.
- 주어진 클래스의 인스턴스 문맥 안에서 멤버 함수를 호출하고 싶을 때는 바인딩된 호출 가능 참조를 사용한다.
    
    ```kotlin
    class Person(val firstName: String, val familyName: String) {
    	fun hasNameOf(name: String) = name.equals(firstName, ignoreCase = true)
    }
    
    fun main() {
    	val isJohn = Person("John", "Doe")::hasNameOf
    	println(isJohn("John"))
    }
    ```
    
- 호출 가능 참조는 오버로딩된 함수를 구분할 수 없다. 명확히 타입을 지정해줘야 한다.
    
    ```kotlin
    fun max(a: Int, b: Int) = if (a > b) a else b
    fun max(a: Double, b: Double) = if (a > b) a else b
    
    val f: (Int, Int) -> Int = ::max //OK
    val g = ::max //ERROR
    ```
    
- 호출가능 참조를 직접 호출하고 싶다면 참조 전체를 괄호로 둘러싼 다음에 인자를 지정해야 한다
    
    ```kotlin
    println((::max)(1, 2)) //OK
    println(::max(1, 2)) //ERROR
    ```
    
- 코틀린 프로퍼티에 대한 호출 가능 참조도 만들 수 있는데, 이는 실제 함수값이 아니고 프로퍼티 정보를 담고있는 리플렉션 객체이다
    
    ```kotlin
    val person = Person("John", "Doe")
    val readName = person::firstName.getter
    val writeFamily = person::familyName.setter
    
    println(readName())
    writeFamily("Smith")
    ```
    

### 5.1.5 인라인 함수와 프로퍼티

- 인라인 기법
    - 함숫값을 사용할 때 발생하는 런타임 비용을 줄일 수 있는 해법.
    - 고차 함수를 호출하는 부분을 해당 함수의 본문으로 대체하는 방법.
    - `inline` 키워드 사용
    - 컴파일된 코드의 크기가 커지지만 성능을 높일 수 있다.
    - 코틀린 표준 라이브러리가 제공하는 여러 고차 함수 중 상당수가 인라인 함수이다
    - 인라인 함수는 실행 시점에 별도의 존재가 아니므로 변수에 저장되거나, 인라인 함수가 아닌 함수에 전달될 수 없다.
        
        ```kotlin
        var lastAction: () -> Unit = {}
        
        inline fun runAndMemorize(action: () -> Unit) {
        	action()
        	lastAction = action //ERROR
        }
        
        // 인라인 함수는 null이 될 수 있는 함수 타입의 인자를 받을 수 없다.
        
        inline fun forEach(a: IntArray, action: ((Int) -> Unit)?) { // ERROR
        }
        
        inline fun forEach(a: IntArray, noinline action: ((Int) -> Unit)?) { // OK. 특정 람다를 인라인 하지 않음
        }
        ```
        
    - 인라인 할 가치가 없는 함수인 경우 컴파일러가 경고를 표시한다
    - 인라인 함수에 비공개 멤버를 전달하는 것을 금지한다 (캡슐화를 깰 수 있기 때문에)
        
        ```kotlin
        class Person(private val firstName: String, private val familyName: String) {
        	inline fun sendMessage(message: () -> String) {
        		println("$firstName, $familyName: ${message()}") //ERROR
        	}
        }
        ```
        
    - 프로퍼티 접근자도 인라인 가능하다. (함수 호출이 없어지면서 성능이 향상된다)
    - 프로퍼티 자체를 inline으로 지정할 수도 있다. 이런경우 getter와 setter를 모두 인라인해준다.
        
        ```kotlin
        class Person(var firstName: String, var familyName: String) {
        	var fullName
        		inline get() = "$firstName $familyName"
        		set(value) { ... }
        }
        
        //
        
        class Person(var firstName: String, var familyName: String) {
        	inline var fullName
        		get() = "$firstName $familyName"
        		set(value) { ... }
        }
        ```
        
    

### 5.1.6 비지역적 제어 흐름

- 고차함수에서 return, break, continue 같은 제어 흐름을 깨는 명령을 사용할 때 문제가 생긴다.
    
    ```kotlin
    fun forEach(a: IntArray, action: (Int) -> Unit) {
    	for (n in a) action(n)
    }
    
    fun main() {
    	forEach(intArrayOf(1, 2, 3, 4)) {
    		if (it < 2 || it > 3) return //return main
    		println(it)
    	}
    }
    ```
    
- 람다 대신 익명함수를 사용하는 방법이 있다
- 람다에서 제어흐름을 반환하고 싶다면 함수 리터럴 식에 이름을 붙여서 문맥 이름을 만들어서 사용한다
    
    ```kotlin
    val action: (Int) -> Unit = myFun@ {
    	if (it < 2 || it > 3) return@myFun
    	println(it)
    }
    
    forEach(intArrayOf(1, 2, 3, 4)) {
    	if (it < 2 || it > 3) return@forEach // 람다를 고차 함수의 인자로 넘기는 경우에는 레이블을 명시적으로 선언하지 않아도 함수 이름을 문맥으로 사용할 수 있다.
    	println(it)
    }
    ```
    
- qualified return은 일반 함수에도 사용할 수 있다 (불필요한 중복이다)
- 람다가 인라인 될 경우에는 인라인된 코드를 둘러싸고 있는 함수에서 반환할 때 return 문을 사용할 수 있다
- crossinline : 고차함수가 inline 될 수 있는 람다를 받는데, 이 고차 함수의 본문에서 람다를 직접 호출하지 않고, 지역함수나 지역 클래스의 메서드 등 다른 문맥에서 호출하는 경우에 사용
    
    ```kotlin
    inline fun forEach(a: IntArray, action: (Int) -> Unit) = object {
    	fun run() {
    		for (n in a) {
    			action(n) //ERROR
    		}
    	}
    }
    
    //---
    
    inline fun forEach(a: IntArray, crossinline action: (Int) -> Unit) = object {
    	fun run() {
    		for (n in a) {
    			action(n) //OK
    		}
    	}
    }
    ```
    

## 5.2 확장

- 멤버인 것 처럼 쓸 수 있는 함수나 프로퍼티를 클래스 밖에서 선언할 수 있도록 해준다.
- 기존 클래스를 변경하지 않아도 새로운 기능을 기존 클래스에 추가할 수 있다.

### 5.2.1 확장 함수

- 어떤 클래스의 멤버인 것처럼 호출 할 수 있는 함수
    
    ```kotlin
    fun String.truncate(maxLength: Int): String {
    	return if (length <= maxLength) this else substring(0, maxLength)
    }
    
    fun main() {
    	println("Hello".truncate(3))
    }
    ```
    
- 확장함수 본문 안에서 수신객체에 this로 접근 가능하다
    - 비공개(private) 멤버에는 접근할 수 없다 (클래스 본문 안에 확장함수를 정의하는 경우에는 접근 가능)
- 바인딩 된 호출 가능 참조 위치에 사용할 수 있다
    
    ```kotlin
    fun Person.hasName(name: String) = name.equals(this.name, ignoreCase = true)
    
    fun main() {
    	val f = Person("John", 25)::hasName
    	println(f("John"))
    }
    ```
    
- 클래스 멤버와 확장과 시그니처가 같다면 클래스 멤버가 호출된다. (shadowing 경고가 발생한다)
- 지역 확장 함수를 정의할 수도 있다.
    - 확장함수 안에 확장함수를 내포시킬 수 있다
    - 이런경우 바깥쪽 함수의 수신 객체를 참조하고 싶으면 한정시킨 this를 사용해야 한다 (예: `this@truncator`)
- 다른 패키지에 최상위 확장 함수가 정의된 경우, 확장 함수를 호출하기 전에 반드시 임포트 해야 한다.
    - 확장 함수를 전체 이름으로 호출 할 수 없는 이유는, 전체 이름에서 패키지 이름과 클래스 이름이 차지할 부분에 수신 객체 식이 오기 때문이다
    
    ```kotlin
    package util
    
    fun String.truncate(maxLength: Int): String {
    	return if (length <= maxLength) this else substring(0, maxLength)
    }
    
    //---
    
    package main
    import util.truncate
    
    fun main() {
    	println("Hello".truncate(3)) // util.String.truncate()???
    }
    ```
    
- 널이 될 수 있는 타입에 대해서도 확장을 정의할 수 있다.
    
    ```kotlin
    fun String?.truncate(maxLength: Int): String? {
    	if (this == null) return null
    	return if (length <= maxLength) this else substring(0, maxLength)
    }
    
    fun main() {
    	val s = readLine()
    	println(s.truncate(3)) // s?. 라고 쓰지 않아도 됨
    }
    ```
    
- [자바 vs 코틀린]
    - 코틀린의 확장 함수는 수신 객체가 파라미터로 추가된 정적 메서드로 컴파일 된다.
    - 일반 함수를 클래스 멤버인 것처럼 쓸 수 있게 해주는 편의 문법이다.
- 

## 5.3 확장 프로퍼티

- 확장 프로퍼티 정의시 항상 명시적인 getter 를 정의해야 하고, 가변 프로퍼티인 경우 setter도 명시해야 한다
    
    ```kotlin
    val IntRange.leftHalf: IntRange
    	get() = start..(start + endInclusive)/2
    
    fun main() {
    	println((1..3).leftHalf)
    }
    
    // ---
    
    val IntArray.midIndex
    	get() = lastIndex/2
    
    var IntArray.midValue
    	get() = this[midIndex]
    	set(value) {
    		this[midIndex] = value
    	}
    ```
    
- 확장 프로퍼티에서 위임을 사용할 수 있는데, 위임식이 프로퍼티의 수신 객체에 접근할 수 없다.
    
    ```kotlin
    val String.message by lazy { "Hello" }
    
    fun main() {
    	println("Hello".message) //Hello
    	println("Bye".message) //Hello
    }
    
    //---
    object Messages
    
    val Messages.HELLO by lazy { "Hello" }
    
    fun main() {
    	println(Messages.HELLO)
    }
    ```
    

## 5.4 동반 확장

```kotlin
fun IntRange.Companion.singletonRange(n: Int) = n..n

val String.Companion.HELLO
	get() = "Hello"

fun main() {
	println(IntRange.singletonRange(5))
	println(IntRange.Companion.singletonRange(5))
	println(String.HELLO)
	println(String.Companion.HELLO)
}
```

- 동반 객체가 존재하는 경우에만 확장을 정의할 수 있다
- Any에는 동반 객체가 존재하지 않으므로 Any의 동반 객체에 대한 확장은 정의할 수 없다.

### 5.4.1 람다와 수신 객체 지정 함수 타입

- 람다나 익명함수에 대해서도 확장 수신 객체를 활용할 수 있다.
- 수신 객체 지정 함수 타입 ; funtional type with receiver
    
    ```kotlin
    fun aggregate(numbers: IntArray, op: Int.(Int) -> Int): Int { //Int.(Int)
    	var result = numbers.firstOrNull()
    		?: throw IllegalArgumentException("Empty array")
    
    	for (i in 1..numbers.lastIndex) result = result.op(numbers[i])
    
    	return result
    }
    
    fun sum(numbers: IntArray) = aggregate(numbers) { op -> this + op } //this를 이용해 수신 객체에 접근할 수 있다
    ```
    
- 익명 함수에 대해서도 확장 함수 문법을 사용할 수 있다.
    
    ```kotlin
    fun sum(numbers: IntArray) = aggregate(numbers, fun Int.(op: Int) = this + op)
    ```
    
- 수신 객체가 있는 함숫값을 호출할 때는, 수신객체를 첫 번째 파라미터로 넣어서 일반 함수 형태로 호출 할 수 있다
    
    ```kotlin
    fun aggregate(numbers: IntArray, op: Int.(Int) -> Int):Int {
    	var result = numbers.firstOrNull()
    		?: throw IllegalArgumentException("Empty array")
    
    	for (i in 1..numbers.lastIndex) {
    		result = op(result, numbers[i]) // 비확장 함수 호출
    	}
    
    	return result
    }
    ```
    
- 수신 객체가 없는 함숫값은 비확장 형태로만 호출할 수 있다
    
    ```kotlin
    val min1: Int.(Int) -> Int = { if (this < it) this else it }
    val min2: (Int, Int) -> Int = min1 // 수신 객체가 첫 번째 파라미터인 일반 함수 타입
    val min3: Int.(Int) -> Int = min2 // 수신 객체가 있는 함수 타입
    
    // ---
    
    fun main() {
    	val min1: Int.(Int) -> Int = { if (this < it) this else it }
    	val min2: (Int, Int) -> Int = min1
    
    	println(3.min1(2)) //OK. min1을 확장 함수로 호출함
    	println(min1(1, 2)) //OK. min1을 비확장 함수로 호출함
    	println(3.min2(2)) //ERROR
    	println(min2(1, 2)) //OK. min2를 비확장 함수로 호출 함
    ```
    

## 5.5. 수신 객체가 있는 호출 가능 참조

- 수신 객체가 있는 함숫값을 정의하는 호출 가능 참조를 만들 수 있다.
    
    ```kotlin
    fun aggregate(numbers: IntArray, op: Int.(Int) -> Int): Int {
    	var result = numbers.firstOrNull()
    		?: throw IllegalArgumentException("Empty array")
    
    	for (i in 1..numbers.lastIndex) result = result.op(numbers[i])
    
    	return result
    }
    
    fun Int.max(other: Int) = if (this > other) this else other
    
    fun main() {
    	val numbers = intArrayOf(1, 2, 3, 4)
    	println(aggregate(numbers, Int::plus)) //Int내장 클래스의 plus() 멤버함수
    	println(aggregate(numbers, Int::max)) //확장함수를 전달
    }
    
    // ---
    // op: Int.(Int) -> Int 타입의 파라미터를 받는 고차함수에 인자가 두 개인 호출가능 참조 ::max를 전달
    fun max(a: Int, b: Int) = if (a > b) a else b
    
    fun main() {
    	println(aggregate(intArrayOf(1, 2, 3, 4), ::max))
    }
    ```
    
    ```kotlin
    //op: (Int, Int) -> Int  일반 함수 타입의 파라미터를 받는 함수
    fun aggregate(numbers: IntArray, op: (Int, Int) -> Int): Int {
    	var result = numbers.firstOrNull()
    		?: throw IllegalArgumentException("Empty array")
    
    	for (i in 1..numbers.lastIndex) result = op(result, numbers[i]) 
    
    	return result
    }
    
    fun Int.max(other: Int) = if (this > other) this else other
    
    fun main() {
    	println(aggregate(intArrayOf(1, 2, 3, 4), Int::plus))
    	println(aggregate(intArrayOf(1, 2, 3, 4), Int::max))
    }
    ```
    

### 5.5.1 영역 함수

- 영역 함수 ; 인자로 제공한 람다를 간단하게 실행해준다.
- 예) `run`, `let`, `with`, `apply`, `also`
- 모든 영역 함수는 인라인 함수이기 때문에 런타임 부가 비용이 없다.
- 남용하면 코드 가독성이 오히려 나빠지고 실수하기도 쉬워진다
- 영역 함수를 여러겹으로 내포시켜 사용하지 않는 편을 권장한다 (`this`나 `it`이 가르키는 대상을 구분하기가 어려워진다)
- `run`
    - 확장 람다를 받는 확장 함수이며, 람다의 결과를 돌려준다.
    - 패턴 : 객체 상태를 설정한 다음, 이 객체를 대상으로 어떤 결과를 만들어내는 람다를 호출
    
    ```kotlin
    class Address {
    	var zipCode: Int = 0
    	var city: String = ""
    	var street: String = ""
    	var house: String = ""
    
    	fun post(message: String): Boolean {
    		"Message for {$zipCode, $city, $street, $house}: $message"
    		return readLine() == "OK"
    	}
    }
    
    fun main() {
    	val isReceived = Address().run {
    		zipCode = 123456,
    		city = "London",
    		street = "Baker Street",
    		house = "221b"
    		post("Hello!") // 반환값
    	}
    }
    ```
    
    - 문맥이 없는 `run`
        - 문맥식이 없고 람다의 값을 반환하기만 한다.
        
        ```kotlin
        fun main() {
        	val address = run {
        		val city = readLine() ?: return // run은 인라인 함수이므로 return 사용가능
        		val street = readLine() ?: return
        		val house = readLine() ?: return
        		Address(city, street, house)
        	}
        
        	println(address.asText())
        }
        ```
        
- `with`
    - run과 비슷. 확장 함수 타입이 아니므로, 문맥 식을 with의 첫 번째 인자로 전달해야 한다.
    - 패턴 : 문맥 식의 멤버 함수와 프로퍼티에 대한 호출을 묶어 동일한 영역 내에서 실행
    
    ```kotlin
    fun main() {
    	val message = with (Address("London", "Baker Street", "221b")) {
    		"Address: $city, $street, $house"
    	}
    
    	println(message)
    }
    ```
    
- `let`
    - run과 비슷. 확장 함수 타입의 람다를 받지 않고, 인자가 하나뿐인 함수 타입의 람다를 받는다
    - 패턴
        - 외부 영역에 새로운 변수를 도입하는 일을 피하고 싶을 때 사용
        - 널이 될 수 있는 값을 안정성 검사를 거쳐서 널이 될 수 없는 함수에 전달
    
    ```kotlin
    class Address(val city: String, val street: String, val house: String) {
    	fun post(message: String) {}
    }
    
    fun main() {
    	Address("London", "Baker Street", "221b").let {
    		println("To city: ${it.city}") //it으로 접근
    		it.post("Hello")
    	}
    
    	//파라미터에 이름을 부여
    	Address("London", "Baker Street", "221b").let { addr ->
    			println("To city: ${addr.city}")
    		addr.post("Hello")
    	}
    }
    ```
    
    ```kotlin
    fun readInt() = try {
    	readLine()?.toInt()
    } catch (e: NumberFormatException) {
    	null
    }
    
    fun main(args: Array<String>) {
    	val index = readInt()
    	val arg = index?.let { args.getOrNull(it) }
    }
    ```
    
- `apply`
    - 확장 람다를 받는 확장 함수이며 자신의 수신 객체를 반환한다
    - 패턴 : 반환값을 만들어내지 않고 객체의 상태를 설정
    
    ```kotlin
    class Address {
    	var city: String = ""
    	var street: String = ""
    	var house: String = ""
    
    	fun post(message: String) { }
    }
    
    fun main() {
    	val message = readLine() ?: return
    
    	Address().apply {
    		city = "Lonton"
    		street = "Baker Street"
    		house = "221b"
    	}.post(message)
    }
    ```
    
- `also`
    - apply와 비슷. 인자가 하나 있는 람다를 파라미터로 받는다
    
    ```kotlin
    class Address {
    	var city: String = ""
    	var street: String = ""
    	var house: String = ""
    
    	fun post(message: String) { }
    }
    
    fun main() {
    	val message = readLine() ?: return
    
    	Address().also {
    		it.city = "Lonton"
    		it.street = "Baker Street"
    		it.house = "221b"
    	}.post(message)
    }
    ```
    

|  | 수신객체 전달방법 | 반환(return) 값 | 정의 |
| --- | --- | --- | --- |
| run | 수신객체를 람다의 수신객체로 전달 (this 로 접근) | block의 마지막 줄 | fun <T, R> T.run(block: T.() -> R): R = block() |
| with | 수신객체를 람다의 수신객체로 전달 (this 로 접근) | block의 마지막 줄 | fun <T, R> with(receiver: T, block: T.() -> R): R = receiver.block() |
| let | 수신객체를 람다의 파라미터로 전달 (it 으로 접근) | block의 마지막 줄 | fun <T, R> T.let(block: (T) -> R): R = block(this) |
| apply | 수신객체를 람다의 수신객체로 전달 (this 로 접근) | 수신객체 자체 | fun <T> T.apply(block: T.() -> Unit): T { block(); return this } |
| also | 수신객체를 람다의 파라미터로 전달 (it 으로 접근) | 수신객체 자체 | fun <T> T.also(block: (T) -> Unit): T { block(this); return this } |

### 5.5.2 클래스 멤버인 확장

```kotlin
class Address(val city: String, val street: String, val house: String)

class Person(val firstName: String, val familyName: String) {
	fun Address.post(message: String) {
		val city = city // 암시적 this: 확장 수신 객체(Address)
		val street = this.city // 한정시키지 않은 this: 확장 수신 객체(Address)
		val house = this@post.house // 한정시킨 this: 확장 수신 객체(Address)
		val firstName = firstName // 암시적 this: 디스패치 수신 객체(Person)
		val familyName = this@Person.familyName // 한정시킨 this: 디스패치 수신 객체(Person)

	fun test(address: Address) {
		// 디스패치 수신 객체(Person): 암시적
		// 확장 수신 객체(addres): 명시적
		address.post("Hello")
	}
}
```

```kotlin
class Address(val city: String, val street: String, val house: String)

class Person(val firstName: String, val familyName: String) {
	fun Address.post(message: String) { }

	inner class Mailbox {
		fun Person.testExt(address: Address) {
			address.post("Hello")
		}
	}

	fun Person.testExt(address: Address) {
		address.post("Hello")
	}
}
```

```kotlin
//Address 클래스의 본문 안에서 post()를 호출하고 싶다면 (에러)

class Address(val city: String, val street: String, val house: String) {
	fun test(person: Person) {
		person.post("Hello") // ERROR
	}
}

class Person(val firstName: String, val familyName: String) {
	fun Address.post(message: String) { }
}
```

```kotlin
//Address 클래스의 본문 안에서 post()를 호출하고 싶다면 (FIX)

class Address(val city: String, val street: String, val house: String) {
	fun test(person: Person) {
		with (person) {
			// 암시적 디스패치와 확장 수신 객체
			post("Hello")
		}
	}
}

class Person(val firstName: String, val familyName: String) {
	fun Address.post(message: String) { }
}
```

- 큰 혼란을 야기할 수 있다.
수신 객체의 영역을 자신이 포함된 선언 내부로 제한하는 쪽을 권장한다
    
    ```kotlin
    class Address(val city: String, val street: String, val house: String)
    
    class Person(val firstName: String, val familyName: String) {
    	fun Address.post(message: String) { }
    }
    
    fun main() {
    	with(Person("John", "Watson")) {
    		Address("London", "Baker Street", "221b").post("Hello")
    	}
    }
    ```
    
    ```kotlin
    class Address(val city: String, val street: String, val house: String)
    
    class Person(val firstName: String, val familyName: String) {
    	// Person 클래스 밖에서는 쓸 수 없음
    	private fun Address.post(message: String) { }
    	fun test(address: Address) = address.post("Hello")
    }
    ```
