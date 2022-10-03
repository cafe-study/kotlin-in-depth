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
        * 타입 파라미터는 상송하지 않는다

```kotlin
open class DataHolder<T>(val data: T)

class StringDataHolder(data: String) : DataHolder<String>(data)

class TreeNode<T>(data: T) : DataHolder<T>(data) { ... }

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
    * 자바의 <T extends Object> 와 동일함
      https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_2.kt#L6-L16

* 하위 타입이 아닐 경우 컴파일 오류가 발생
  https://github.com/cafe-study/kotlin-in-depth/blob/0a9264f2eeb9edaf9f1ab6bb7845a24aa909a030/src/main/kotlin/chap9/Chap9_1_2.kt#L20-L37

* 상위 바운드가 final 클래스이면 해당되는 하위타입이 하나 밖에 없으므로 제네릭의 의미가 없음

```kotlin
// 제네릭이 아닌 함수로 대신할 수 있다.
// fun TreeNode<Int>.sum(): Int { ... }
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