# 9.1 타입 파라미터

## 9.1.1 제네릭 선언

* 어떤 선언을 제네릭 선언으로 만들려면 하나 이상의 타입 파라미터를 추가해야 함
* 컴파일러가 문맥에서 타입 인자의 타입을 추론할 수 있다면 타입 인자를 생략할 수 있음

```kotlin
val map = HashMap<Int, String>()
val list = arrayListOf<String>()

// 변수 선언에 제네릭 타입이 기재되어 있어, 생성자에서는 제네릭을 기입할 필요 없음
val map: Map<Int, String> = hashMap()

// 정의시 element의 타입으로 추론 가능 
val list = arrayListOf("abd", "def")

// 자바와 달리 함수 호출 뒤에 제네릭이 명시된다. Collection.<String>emptyList()
val emptyStringList = emptyList<String>()
```

#### 제네릭 클래스 만들기

https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_1.kt#L3-L26

* 제네릭 클래스나 인터페이스 정의시 코틀린은 반드시 타입 인자를 명시해야 함
    * 예) TreeNode<Any>
* 대부분의 경우 컴파일러가 문맥에서 타입 인자를 추론해줌
    * 예외: 상위 클래스 생성자에 대한 위임 호출
* 타입 파라미터는 상속하지 않는다

```kotlin
open class DataHolder<T>(val data: T)

class StringDataHolder(data: String) : DataHolder<String>(data)

// 타입 파라미터는 상속하지 않음
class TreeNode<T>(data: T) : DataHolder<T>(data) { ... }

// 생성자 위임 호출의 타입인자는 추론하지 못함
// error: one type argument expected for class DataHolder<T>
class StringDataHolder2(data: String) : DataHolder(data)

```

### 제네릭 함수, 프로퍼티 선언

https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_1_2.kt#L5-L23

* 프로퍼티나 함수를 제네릭으로 선언할 때는 타입 파라미터를 fun이나 val/var 바로 뒤에 위치시킴
* 확장 프로퍼티만 타입 파라미터를 가질 수 있음 (클래스 멤버 프로퍼티나 객체에서의 타입 파라미터는 사용이 불가능. 아래 참고)

```kotlin
// error: type parameter of a property must be used in its receiver type
val <T> root: TreeNode<T>? = null

// error: type parameters are not allowed for objects
object EmptyTree<T> 
```

### 9.1.2 바운드와 제약

* 특정 타입 파라미터가 어떤 타입의 상위 바운드 또는 하위 바운드의 값이길 바랄 때 아래와 같이 선언할 수 있음
  https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_2.kt#L6-L16

* 하위 타입이 아닐 경우 컴파일 오류가 발생
  https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_2.kt#L20-L37

* 상위 바운드가 final 클래스이면 해당되는 하위타입이 하나 밖에 없으므로 제네릭의 의미가 없음 (그래서 warning이 발생)

```kotlin
// 제네릭이 아닌 함수로 대신할 수 있다.
// fun TreeNode<Int>.sum(): Int { ... }
// [FINAL_UPPER_BOUND] 'Int' is a final type, and thus a value of the type parameter is predetermined
fun <T : Int> TreeNode<T>.sum(): Int {
    var sum = 0
    walkDepthFirst { sum += it }
    return sum
}
```

* 재귀적 타입 파라미터: 타입 파라미터 바운드로 타입 파라미터를 사용
  https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_2_2.kt#L6-L23

* 바운드가 자신보다 앞에 있는 타입 파라미터를 가리킬 수 있음
  https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_2_3.kt#L6-L22

* 코틀린의 타입 파라미터의 상위 바운드는 자바의 상위 바운드와 비슷함
    * T extends Number -> T: Number
* null이 아닌 타입을 지정하려면 상위 바운드도 null이 아닌 타입을 지정

```kotlin
fun <T : Any> notNullTreeOf(data: T) = TreeNode(data)
```

* 한 타입 파라미터에 아래와 같은 방식으로 여러 개의 제약 조건을 가할 수 있음

```kotlin
interface Named {
    val name: String
}

interface Identified {
    val id: Int
}

class Registry<T> where T : Named, T : Identified {
    val items = ArrayList<T>()
}
```

### 9.1.3 타입 소거와 구체화

* 런타임에서는 제네릭 타입 인자에 대한 정보가 코드에서 지워진다.
    * 타입 소거: 자바 5에서 제네릭을 추가할 때 하위 호환성을 유지하는 과정에서 제네릭으로 설정한 타임 인자에 대한 정보는 런타임에서 지워짐

```kotlin
// [CANNOT_CHECK_FOR_ERASED] Cannot check for instance of erased type: T
fun <T> TreeNode<Any>.isInstanceOf(): Boolean = data is T && children.all { it.isInstanceOf<T>() }
```

* 마찬가지 이유로 제네릭 타입에 대해 is 연산자를 적용하는 것도 의미가 없음. 다만 이런 경우 컴파일러가 타입 인자와 타입 파라미터가 서로 일치하는지 확인하여 경고나 오류를 보고

```kotlin
val list = listOf(1, 2, 3)
list is List<Number>
// [CANNOT_CHECK_FOR_ERASED] Cannot check for instance of erased type: List<String>
list is List<String>
```

* 코틀린은 항상 제네릭 타입이 들어가야 하므로, 어떤 컬랙션인지 확인하고 싶다면 다음과 같이 *를 사용

```kotlin
list is List<*>
map is Map<*, *>
```

#### 제네릭 타입소거를 코틀린에서 해결하는 방법

* 자바에서 타입 소거를 해결하기 위해 아래와 같은 방법이 가능
    1. 캐스트: 문제가 컴파일 시점에 해결되지 않음
    2. 리플랙션: 성능에 영향을 미칠 수 있음
* 코틀린의 구체화 (reified)
    * reified 키워드는 인라인일때만 사용 가능
    * 자바의 접근방식과 달리 안전하고 빠름
    * 인라인 함수를 사용하므로, 컴파일된 코드가 커지는 경향이 있음
    * 한계
        * 구체화된 타입 파라미터를 통해 생성자를 호출하거나 동반 객체에 접근할 수 없음
        * 구체화된 타입 파라미터를 구체화하지 않은 타입 파라미터로 대신할 수 없음

https://github.com/cafe-study/kotlin-in-depth/blob/bfd85a84e802d2ccaf7ffb9a3c4be425968a9a69/src/main/kotlin/chap9/Chap9_1_3.kt#L5-L31

# 9.2 변성

## 9.2.1 변성: 생산자와 소비자 구분

* 무공변(invariant): 타입 파라미터 사이에 하위타입 관계가 성립해도 제네릭 타입 사이에는 하위타입 관계가 생기지 않음

```kotlin
val node: TreeNode<Any> = TreeNode<String>("Hello") // error: type mismatch
```

* 제네릭 타입이 자신의 타입 파라미터를 취급하는 방법
    1. 생산자: T 타입의 값을 반환하는 연산만 제공하고 T타입의 값을 입력으로 받는 연산은 제공하지 않음
    2. 소비자: T 타입의 값을 입력으로 받기만 하고, T 타입의 값을 반환하지는 않음
    3. 위 두가지 경우에 해당하지 않는 나머지 타입들

* 세번째 타입(생산자도 소비자도 아닌 타입)의 경우 타입 안정성을 깨지않고는 하위 타입 관계를 유지할 수 없음
    * 즉 TreeNode<Any>와 TreeNode<String> 간에 상위/하위타입 관계를 설정할 수 없음

```kotlin
val stringNode = TreeNode<String>("Hello")
val anyNode: TreeNode<Any> = stringNode // 실제로 허용되지 않으나, 허용이 된다면
anyNode.addChild(123)
val s = stringNode.children.first() // 123이 string으로 캐스트되면서 예외가 발생 (계약이 깨짐)
```

### 불변 컬렉션 타입의 변성

* 불변 컬랙션 타입은 T 타입의 값을 만들어내기만 하고 결코 소비하지 않음
    * List<String>은 List<Any>의 능력도 가짐 -> 공변적 (covariant)
    * 코틀린에서 생산자 역할을 하는 타입은 모두 공변적임

```kotlin
val stringProducer: () -> String = { "Hello" }
val anyProducer: () -> Any = stringProducer
println(anyProducer())
```

### 공변적 != 불변성

```kotlin
// 가변적인 리스트이지만, 생산자의 역할만 수행하므로 공변적 (NonGrowingList<String> 은 NonGrowingList<Any>가 할 수 있는 모든 일을 할 수 있음)
interface NonGrowingList<T> {
    val size: Int
    fun get(index: Int): Int
    fun remove(index: Int)
}

// 불변적이지만 생산자가 아니기 때문에 T의 하위타입 관계를 유지하지 않음
interface Set<T> {
    fun contains(element: T): Boolean
}
```

### 소비자 타입의 변성

* 타입 파라미터의 하위 타입관계를 역방향으로 유지해줌 (생산자와 반대)
    * Set<Number>는 Set<Int>의 하위 타입처럼 동작함 -> 반공변적 (contravariant)

```kotlin
val anyConsumer: (Any) -> Unit = { println(it) }
val stringConsumer: (String) -> Unit = anyConsumer
stringConsumer("Hello")
```

### 변성 정리

* 주어진 제네릭 타입 X<T,..> 에 대해
    * X가 생산자 역할: T를 공변적으로 선언할 수 있고, A가 B의 하위 타입이면 X<A>도 X<B>의 하위 타입이 됨
    * X가 소비자 역할: T를 반공변적으로 선언할 수 있고, B가 A의 하위 타입이면 X<A>가 X<B>의 하위 타입이 됨
    * 나머지 경우는 X는 T에 대해 무공변

## 9.2.2 선언 지점 변성

* 타입파라미터의 변성을 선언 자체에 지정하는 방식
* 예: concat이라는 함수를 정의하고, Number의 하위 타입을 넣을수 있게 하고 싶음 -> out 키워드 사용
  https://github.com/cafe-study/kotlin-in-depth/blob/94d47e269af4b85175f3421e60dee4b1cd20c267/src/main/kotlin/chap9/Chap9_2_2.kt#L3-L34
* out은 제네릭이 생산자 역할/in은 제네릭이 소비자역할을 할 때 사용할 수 있음
  https://github.com/cafe-study/kotlin-in-depth/blob/94d47e269af4b85175f3421e60dee4b1cd20c267/src/main/kotlin/chap9/Chap9_2_2_2.kt#L32-L32
* TreeNode는 in/out 연산을 모두 가지고 있어 공변/반공변으로 정의가 불가능
    * 모든 자식을 만드는 함수를 만들고, 이를 공변으로 사용하고 싶다면? -> 다음 장에서 설명

## 9.2.3 프로젝션을 사용한 사용 지점 변성

* 타입 파라미터의 변성을 함수내에서 정의
    * 선언 지점 변성과 마찬가지로, 지정한 변성에서 사용할 수 없는 연산을 사용하려 하면 컴파일 에러가 발생
      https://github.com/cafe-study/kotlin-in-depth/blob/94d47e269af4b85175f3421e60dee4b1cd20c267/src/main/kotlin/chap9/Chap9_2_3.kt#L17-L17

* 코틀린 프로젝션은 근본적으로 자바의 extends/super 와일드카드와 같은 역할을 함
    * 자바의 PECS(Producer Extends, Consumer Super)
* 프로젝션이 적용된 타입 인자에 해당하는 선언 지점 변성은 의미가 없음
    * 선언/프로젝션이 동일하면 경고를 띄우고
    * 다르면 컴파일 오류 발생
      https://github.com/cafe-study/kotlin-in-depth/blob/master/src/main/kotlin/chap9/Chap9_2_3_2.kt#L20-L20

## 9.2.4 스타 프로젝션

* 스타 프로젝션을 사용하면 타입 인자가 중요하지 않거나 알려져 있지 않은 제네릭 타입을 간결하게 표현할 수 있음
    * 자바의 ?와 동일
      https://github.com/cafe-study/kotlin-in-depth/blob/master/src/main/kotlin/chap9/Chap9_2_4.kt#L15-L15
* 타입 파라미터에 바운드가 둘 이상 있다면 *로 명시적인 out 프로젝션을 대신할 수 없음
  https://github.com/cafe-study/kotlin-in-depth/blob/master/src/main/kotlin/chap9/Chap9_2_4_2.kt#L3-L3
* *는 선언 지점 변성이 붙은 타입 파라미터를 대신할 때 쓸 수 있음

```kotlin
interface Consumer<in T> {
    fun consume(value: T)
}

interface Producer<out T> {
    fun produce(): T
}

fun main() {
    val starProducer: Producer<*>   // Producer<Any?>와 같음
    val starConsumer: Consumer<*>   // Consumer<Nothing>와 같음
}
```

# 9.3 타입 별명

* typealias로 정의하며, 긴 타입이름을 짧게 부를 수 있는 별명을 지정함
* 각종 타입, 내포된 클래스등을 지정할 수 있으며, 제네릭 타입을 포함할 수 있음
* 가시성을 사용할 수 있음
* 타입 별명은 기존 타입을 가리키는 새로운 방법을 추가해주는 것 뿐이므로 원래의 타입과 자유롭게 바꿔 쓸 수잇음
* 제약
    * 최상위에만 선언 가능
    * 제네릭 타입 별명에 제약이나 바운드를 선언할 수 없음
      https://github.com/cafe-study/kotlin-in-depth/blob/master/src/main/kotlin/chap9/Chap9_3.kt#L274-L274
* 참고: 타입에 대한 새 이름을 제공하는 방법
    * 임포트 별명: 제네릭 사용 불가, import 된 파일 내에서만 사용 가능
    * 상속: 제네릭, 가시성 제어 가능, 새로운 타입이 정의되는 것이므로 상속받은 타입과 자유롭게 바꿔쓰는 것은 불가능
    * 인라인 클래스: 원래 타입과 호환되지 않는 새로운 타입을 만들어냄