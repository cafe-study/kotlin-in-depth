# 13.coroutine

## 13.1 코루틴

명령형 스타일로 코드를 작성하면 컴파일러가 코드를 효율적인 비동기 계산으로 자동 변환해준다.

일시 중단 가능한 함수

## 13.1.1 코루틴과 일시 중단 함수

일시 중단 함수 : 원하는 지점에서 함수에 필요한 모든 런타임 문맥을 저장하고 함수 실행을 중단한 다음, 나중에 필요할 때 다시 실행을 계속 진행할 수 있게 한 것이다. suspend 변경자를 붙인다

```kotlin
suspend fun foo() {
	println("Task started")
	delay(100)
	println("Task finished")
}
```

`delay()` 함수는 일시중단함수. `Thread.sleep()`과 비슷한 일을 한다.

하지만 `delay()`는 현재 스레드를 블럭시키지 않고 자신을 호출한 함수를 일시 중단 시키며 스레드를 다른 작업을 수행할 수 있게 풀어준다.

일시중단 함수는 일시중단 함수와 일반 함수를 원하는대로 호출할 수 있다.

반면 코틀린은 일반함수가 일시중단 함수를 호출하는 것을 금지한다.

```kotlin
fun foo() {
	println("Task started")
	delay(100) //ERROR
	println("Task finished")
}
```

코루틴을 실행할 때 사용하는 여러가지 함수를 코루틴 빌더라고 부른다.

코루틴 빌더는 CoroutineScope 인스턴스의 확장 함수로 쓰인다.

CoroutineScope에 대한 구현 중 가장 기본적인 것으로 GlobalScope 객체가 있다.

GlobalScope 객체를 사용하면 독립적인 코루틴을 만들 수 있고, 이 코루틴은 자신만의 작업을 내포할 수 있다.

## 13.1.2 코루틴 빌더

* `launch()`

코루틴을 시작하고, 코루틴을 실행중인 작업의 상태를 추적하고 변경할 수 있는 `Job` 객체를 돌려준다.

이 함수는 `CoroutineScope.() -> Unit`타입의 일시 중단 람다를 받는다. 이 람다는 새 코루틴의 본문에 해당한다.

```kotlin
import kotlinx.coroutines.*
import java.lang.System.*

fun main() {
    val time = currentTimeMillis()

    GlobalScope.launch {
        delay(100)
        println("Task 1 finished in ${currentTimeMillis() - time}ms")
    }

    GlobalScope.launch {
        delay(100)
        println("Task 2 finished in ${currentTimeMillis() - time}ms")
    }

    Thread.sleep(200)
}
```

* 두 작업의 순서는 보장되지 않는다.
* `main()` 함수는 `Thread.sleep()` 을 통해 메인 스레드 실행을 잠시 중단한다.
이를 통해 코루틴 스레드가 완료될 수 있도록 충분한 시간을 제공한다.
* 코루틴을 처리하는 스레드는 데몬 모드로 실행되기 때문에 main() 스레드가 이 스레드보다 빨리 끝나버리면 자동으로 실행이 종료된다.
* 일시중단 함수 내부에서 `sleep()`과 같은 스레드를 블럭시키는 함수를 실행할 수도 있지만, 그런식의 코드는 코루틴을 사용하는 목적에 위배된다.
* 동시성 작업의 내부에서는 일시 중단 함수인 `delay()`를 사용해야 한다.
* 코루틴은 스레드보다 가벼워서 여러 코루틴을 충분히 동시에 실행할 수 있다.
* `launch()` 빌더는 동시성 작업이 결과를 만들어내지 않는 경우에 적합하다.
* 결과가 필요한 경우에는 async() 빌더 함수를 사용한다. 
이 함수는 `Deferred` 의 인스턴스를 돌려주고, 이 인스턴스는 Job의 하위 타입으로 `await()` 메서드를 통해 계산 결과에 접근할 수 있게 해준다.
* `await()` 메서드를 호출하면 `await()`는 계산이 완료되거나 계산 작업이 취소될 때 까지 현재 코루틴을 일시 중단시킨다.
* 작업이 취소되는 경우 `await()`는 예외를 발생시키면서 실패한다.
* `async()`를 자바의 future에 해당하는 코루틴 빌더라고 생각할 수 있다.

```kotlin
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

suspend fun main() {
    val message = GlobalScope.async {
        delay(100)
        "abc"
    }

    val count = GlobalScope.async {
        delay(100)
        1 + 2
    }

    delay(200)

    val result = message.await().repeat(count.await())
    println(result)
}
```

* `launch()`와 `async()` 빌더의 경우 일시중단 함수 내부에서 스레드 호출을 블럭시키지는 않지만, 
백그라운드 스레드를 공유하는 풀(pool)을 통해 작업을 실행한다.
* `runBlocking()` 빌더는 디폴트로 현재 스레드에서 실행되는 코루틴을 만들고 코루틴이 완료될 때까지 현재 스레드의 실행을 블럭시킨다.
* 코루틴이 성공적으로 끝나면 일시중단 람다의 결과가 `runBlocking()` 호출의 결과값이 된다.
* 코루틴이 취소되면 `runBlocking()`은 예외를 던진다.
* 반면에 블럭된 스레드가 인터럽트되면 `runBlocking()`에 의해 시작된 코루틴도 취소된다.

```kotlin
import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch {
        delay(100)
        println("Background task: ${Thread.currentThread().name}")
    }

    runBlocking {
        println("Primary task: ${Thread.currentThread().name}")
        delay(200)
    }
}
```

* 이러한 블러킹 동작 때문에 `runBlocking()`을 다른 코루틴 안에서 사용하면 안된다.
* `runBlocking()`은 블러킹 호출과 넌블러킹 호출 사이의 다리 역할을 하기 위해 고안된 코루틴 필더이므로,
테스트나 메인 함수에서 최상위 빌더로 사용하는 등의 경우에만 `runBlocking()`을 써야 한다.

### 13.1.3 코루틴 영역과 구조적 동시성

어떤 코루틴을 다른 코루틴의 문맥에서 실행하면 후자가 전자의 부모가 된다.

이 경우 자식의 실행이 모두 끝나야 부모가 끝날 수 있도록 부모와 자식의 생명주기가 연관된다.

이런 기능을 구조적 동시성 이라고 부르며, 지역변수 영역 안에서 블럭이나 서브루틴을 사용하는 경우와 구조적 동시성을 비교할 수 있다.

```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("Parent task started")

        launch {
            println("Task A started")
            delay(200)
            println("Task A finished")
        }

        launch {
            println("Task B started")
            delay(200)
            println("Task B finished")
        }

        delay(100)
        println("Parent task finished")
    }
    println("Shutting down...")
}
```

`coroutineScope()` 호출로 코드 블럭을 감싸면 커스텀 영역을 도입할 수 있다.

`runBlocking()`과 마찬가지로 `coroutineScope()` 호출은 람다의 결과를 반환하고, 자식들이 완료되기 전까지 실행이 완료되지 않는다.

`coroutineScope()`가 일시중단 함수라 현재 스레드를 블럭시키지 않는다.

```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("Custom scope start")

        coroutineScope {
            launch {
                delay(200)
                println("Task A finished")
            }

            launch {
                delay(200)
                println("Task B finished")
            }
        }

        println("Custom scope end")
    }
}
```

### 13.1.4 코루틴 문맥

코루틴마다 `CoroutineContext` 인터페이스로 표현되는 문맥이 연관돼 있으며, 코루틴을 감싸는 변수 영역의 `coroutineContext` 프로퍼티를 통해 이 문맥에 접근할 수 있다.

문맥은 키-값 쌍으로 이뤄진 불변 컬렉션이다.

- 코루틴이 실행 중인 취소 가능한 작업을 표현하는 잡(job)
- 코루틴과 스레드의 연관을 제어하는 디스패처(dispatcher)

일반적으로 문맥 `CoroutineContext.Element`를 구현하는 아무 데이터나 저장할 수 있다.

특정 원소에 접근하려면 get() 메서드나 인덱스 연산자에 키를 넘겨야 한다.

```kotlin
GlobalScope.launch {
	println("Task is active: ${coroutineContext[Job.Key]!!.isActive}")
}
```

기본적으로 `launch()`, `async()` 등의 표준 코루틴 빌더에 의해 만들어지는 코루틴은 현재 문맥을 이어받는다.

필요하면 빌더 함수에 context 파라미터를 지정해서 새 문맥을 넘길 수도 있다.

새 문맥을 만들려면 두 문맥의 데이터를 합쳐주는 `plus()` 함수 `+` 연산자를 사용하거나,

주어진키에 해당하는 원소를 문맥에서 제거해주는 `minusKey()` 함수를 사용하면 된다.

```kotlin
import kotlinx.coroutines.*

private fun CoroutineScope.showName() {
    println("Current coroutine: ${coroutineContext[CoroutineName]?.name}")
}

fun main() {
    runBlocking {
        showName()
        launch(coroutineContext + CoroutineName("Worker")) {
            showName()
        }
    }
}
```

## 13.2 코루틴 흐름 제어와 잡 생명 주기

잡은 동시성 작업의 생명 주기를 표현하는 객체다.

잡을 사용하면 작업 상태를 추적하고 필요할 때 작업을 취소할 수 있다.

(그림 13-3)
```
New → Active → Completing → Completed
                          → Cancelling → Cancelled
```

활성화 상태는 작업이 사작됐고 아직 완료나 취소로 끝나지 않았다는 뜻이다.

이 상태가 보통 디폴트 상태다.

즉, 잡은 생성되자마자 활성화 상태가 된다.

launch()나 async()는 CoroutineStart 타입의 인자를 지정해서 잡의 초기 상태를 선택하는 기능을 제공하기도 한다.

- `CoroutineStart.DEFAULT` 는 디폴트 동작이며, 잡을 즉시 시작한다.
- `CoroutineStart.LAZY` 는 잡을 자동으로 시작하지 말라는 뜻이다. 이 경우에는 잡이 신규 상태가 되고 시작을 기다리게 된다.

신규 상태의 잡에 대해 start()나 join() 메서드를 호출하면 잡이 시작되면서 활성화 상태가 된다.

```kotlin
package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            println("Job started")
        }

        delay(100)

        println("preparing to start...")
        job.start()
    }
}
```

활성화 상태에서는 코루틴 장치가 잡을 반복적으로 일시 중단하고 재개시킨다.

잡이 다른 잡을 시작할 수도 있는데, 이 경우 새 잡은 기존 잡의 자식이 된다.

잡의 부모 자식 관계는 동시성 계산 사이에 트리 형태의 의존 구조를 만든다.

`children` 프로퍼티를 통해 완료되지 않은 자식 잡들을 얻을 수 있다.

```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = coroutineContext[Job.Key]!!

        launch { println("This is task A") }
        launch { println("This is task B") }
        
        println("${job.children.count()} children running")
    }
}
```

코루틴이 일시 중단 람다 블록의 실행을 끝내면 잡의 상태는 ‘완료 중’ 상태로 바뀐다.

이 상태는 자식들의 완료를 기다리는 상태다.

잡은 모든 자식이 완료될 때 까지 이 상태를 유지하고, 모든 자식이 완료되면 잡의 상태가 ‘완료됨’으로 바뀐다.

잡의 `join()` 메서드를 사용하며 조인 대상 잡이 완료될 때 까지 현재 코루틴을 일시 중단시킬 수 있다.

```kotlin
package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = coroutineContext[Job.Key]!!

        val jobA = launch { println("This is task A") }
        val jobB = launch { println("This is task B") }

        jobA.join()
        jobB.join()

        println("${job.children.count()} children running")
    }
}
```

| 잡 상태 | isActive | isCompleted | isCancelled |
| --- | --- | --- | --- |
| 신규 | false | false | false |
| 활성화 | true | false | false |
| 완료중 | true | false | false |
| 취소중 | false | false | true |
| 취소됨 | false | true | true |
| 완료됨 | false | true | false |

### 13.2.1 취소

잡의 cancel() 메서드를 호출하면 잡을 취소할 수 있다.

이 메서드는 더 이상 필요없는 계산을 중단시킬 수 있는 표준적인 방법을 제공한다.

취소 가능한 코루틴이 스스로 취소가 요청됐는지 검사해서 적절히 반응해줘야 한다.

```kotlin
import kotlinx.coroutines.*

suspend fun main () {
    val squarePrinter = GlobalScope.launch(Dispatchers.Default) {
        var i = 1
        while (isActive) {
            println(i++)
        }
    }

    delay(100)
    squarePrinter.cancel()
}
```

`isActive` 확장 프로퍼티는 현재 잡이 활성화된 상태인지 검사한다.

부모 코루틴이 cancel() 메서드를 호출하면 squarePrinter 상태가 ‘취소 중’으로 바뀌고, 그 다음 isActive 검사를 통해 루프를 종료시킬 수 있다.

CancellationException을 발생시키면서 취소에 반응할 수 있게 일시 중단 함수를 호출하는 방법

코루틴 라이브러리에 정의된 delay()나 join() 등의 모든 일시중단 함수가 이 예외를 발생시켜준다.

yield() 는 실행중인 잡을 일시 중단 시켜서 자신을 실행중인 스레드를 다른 코루틴에게 양보한다.

```kotlin
import kotlinx.coroutines.*

suspend fun main () {
    val squarePrinter = GlobalScope.launch(Dispatchers.Default) {
        var i = 1
        while (true) {
            yield()
            println(i++)
        }
    }

    delay(100)
    squarePrinter.cancel()
}
```

부모 코루틴이 취소되면 자동으로 모든 자식의 실행을 취소한다.

이 과정은 부모에게 속한 모든 잡 계층이 취소될 때 까지 계속된다.

```kotlin
import kotlinx.coroutines.*

suspend fun main () {
    runBlocking {
        val parentJob = launch {
            println("Parent started")

            launch {
                println("Child 1 started")
                delay(500)
                println("Child 1 completed")
            }

            launch {
                println("Child 2 started")
                delay(500)
                println("Child 2 completed")
            }

            delay(500)
            println("Parent completed")
        }

        delay(100)
        parentJob.cancel()
    }
}
```

### 13.2.2 타임아웃

`withTimeout()` : 시간 경과시 `TimeoutCancellationException` 발생

`withTimeoutOrNull()` : 시간 경과시 null값을 리턴

```kotlin
import kotlinx.coroutines.*
import java.lang.Exception
import java.io.File

suspend fun main () {
    runBlocking {
        val asyncData = async {
            File("data.txt").readText()
        }

        try {
            val text = withTimeout(50) {
                asyncData.await()
            }
            println("Data loaded: $text")
        } catch (e: Exception) {
            println("Timeout exceeded")
        }
    }
}
```

### 13.2.3 코루틴 디스패치하기

코루틴 디스패처 : 코루틴을 실행할 때 사용할 스레드를 제어하는 작업을 담당하는 컴포넌트

디스패처는 코루틴 문맥의 일부. `launch()` 나 `runBlocking()` 등의 코루틴 빌더함수에서 지정할 수 있다.

디스패처는 원소가 하나뿐인 문맥이라 코루틴 빌더에 디스패처를 넘길 수도 있다.

```kotlin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch(Dispatchers.Default) {
            println(Thread.currentThread().name)
        }
    }
}
```

코루틴 디스패처는 자바의 executor와 비슷하다.

`asCoroutineDispatcher()` 확장함수를 사용하면 기존 executor 구현을 코루틴 디스패처로 바꿀 수 있다.

```kotlin
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val id = AtomicInteger(0)

    val executor = ScheduledThreadPoolExecutor(5) { runnable ->
        Thread(
            runnable,
            "WorkerThread-${id.incrementAndGet()}"
        ).also { it.isDaemon = true }
    }

    executor.asCoroutineDispatcher().use { dispatcher ->
        runBlocking {
            for (i in 1..3) {
                launch(dispatcher) {
                    println(Thread.currentThread().name)
                    delay(1000)
                }
            }
        }
    }
}
```

`ExecutorService` 의 인스턴스에 대해 `asCoroutineDispatcher()` 를 호출하면 `ExecutorCoroutineDispatcher` 를 반환하는데, 이 디스패처는 Closeable 인스턴스도 구현한다.

시스템 자원을 해제하려면 `close()` 함수를 직접 호출하거나 `use()` 함수 블록 안에서 디스패처를 사용해야 한다.

코루틴 라이브러리에서 제공하는 기본 디스패처 구현

- `Dispatchers.Default` : 공유 스레드풀. 풀 크기는 Max(CPU코어수, 2). CPU 위주의 작업에 적합.
- `Dispatchers.IO` : 스레드 풀 기반. 잠재적으로 블러킹될 수 있는 I/O를 많이 사용하는 작업에 최적화 되어있음. 필요에 따라 스레드를 추가하거나 종료시켜준다.
- `Dispatchers.Main` : 사용자 입력이 처리되는 UI 스레드에서만 배타적으로 작동하는 디스패처

`newFixedThreadPoolContext()` 나 `newSingleThreadPoolContext()` 를 사용하면 직접 만든 스레드풀을 사용하거나 스레드를 하나만 사용하는 디스패처도 만들 수 있다.

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

fun main() {
    newFixedThreadPoolContext(5, "WorkerThread").use { dispatcher ->
        runBlocking {
            for (i in 1..3) {
                launch(dispatcher) {
                    println(Thread.currentThread().name)
                    delay(1000)
                }
            }
        }
    }
}
```

디스패처를 명시적으로 지정하지 않으면 코루틴을 시작한 영역으로부터 디스패처가 자동으로 상속된다.

```kotlin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Root: ${Thread.currentThread().name}")

        launch {
            println("Nested, inherited: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Nested, explicit: ${Thread.currentThread().name}")
        }
    }
}
```

부모 코루틴이 없으면 암시적으로 `Dispatchers.Default` 로 디스패처를 가정한다.

`runBlocking()` 빌더는 현재 스레드를 사용한다.

`withContext()` 함수를 사용해 디스패처를 오버라이드할 수 있다.

이 기법은 중단 가능 루틴의 일부를 한 스레드에서만 실행하고 싶을 때 유용하다.

```kotlin
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    newSingleThreadContext("Worker").use { worker ->
        runBlocking {
            println(Thread.currentThread().name)
            
            withContext(worker) {
                println(Thread.currentThread().name)
            }
            
            println(Thread.currentThread().name)
        }
    }
}
```

### 13.2.4. 예외처리

예외처리전략

1) 예외를 부모 코루틴에게 전달하는 것

- 부모 코루틴이 (자식에게서 발생한 오류와) 똑같은 오류로 취소된다. 이로인해 부모의 나머지 자식도 모두 취소된다.
- 자식들이 모두 취소되고 나면 부모는 예외를 코루틴 트리 윗부분으로 전달한다.
- 그 후 예외에 `CoroutineExceptionHandler.Consider`에 의해 처리된다.

```kotlin
package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        launch{
            throw Exception("Error in task A")
            println("Task A completed")
        }

        launch {
            delay(1000)
            println("Task B completed")
        }

        println("Root")
    }
}
```

```kotlin
import kotlinx.coroutines.*

suspend fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("caught $throwable")
    }

    GlobalScope.launch(handler) {
        launch{
            throw Exception("Error in task A")
            println("Task A completed")
        }

        launch {
            delay(1000)
            println("Task B completed")
        }

        println("Root")
    }.join()
}
```

문맥에 핸들러 인스턴스 정의가 없는 경우, 코루틴 라이브러리는 JVM ServiceLoader 장치를 통해 설정된 모든 전역 핸들러를 호출하고 현재 스레드에 대해서는 uncaughtExceptionHandler를 발생시킨다.

CoroutineExceptionHandler는 전역 영역에서 실행된 코루틴에 대해서만 정의할 수 있고, CoroutineExceptionHandler가 정의된 코루틴의 자식에 대해서만 적용된다.

그래서 runBlocking()을 GlobalScope.launch()로 변경하고 main() 함수를 suspend로 표시하고 join() 호출을 사용해야 한다.

첫번째 예제에 핸들러만 추가하면 코루틴이 전역 영역에서 실행되지 않으므로 디폴트 핸들러를 사용하게 된다.

2) 던져진 예외를 저장했다가 예외가 발생한 계산에 대한 await() 호출을 받았을 때 다시 던지는 방법

(`async()` 빌더에서 사용하는 방법)

```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val deferredA = async {
            throw Exception("Error in task A")
            println("Task A completed")
        }

        val deferredB = async {
            println("Task B completed")
        }

        deferredA.await()
        deferredB.await()
        println("Root")
    }
}
```

이런 방식에서는 CoroutineExceptionHandler를 사용하지 않는다. 전역 디폴트 핸들러가 호출된다.

```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val deferredA = async {
            throw Exception("Error in task A")
            println("Task A completed")
        }

        val deferredB = async {
            println("Task B completed")
        }

        try {
            deferredA.await()
            deferredB.await()
        } catch (e: Exception) {
            println("Caught $e")
        }
        
        println("Root")
    }
}
```

자식(deferredA)이 실패하는 경우에는 부모를 취소시키기 위해 자동으로 예외를 던지게 된다.

이 동작을 변경하려면 슈퍼바이저(supervisor) 잡을 사용해야 한다.

슈퍼바이저 잡이 있으면 취소가 아래 방향으로만 전달된다.

슈퍼바이저 잡을 취소하면 자식이 모두 취소되지만, 자식이 취소된 경우 슈퍼바이저나 슈퍼바이저의 다른 자식들은 아무 영향을 받지 않는다.

슈퍼바이저의 자식 중 하나에 cancel()을 호출해도 코루틴의 형제자매나 슈퍼바이저 자신에는 아무 영향이 없다.

```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        supervisorScope {
            val deferredA = async {
                throw Exception("Error in task A")
                println("Task A completed")
            }

            val deferredB = async {
                println("Task B completed")
            }

            try {
                deferredA.await()
                deferredB.await()
            } catch (e: Exception) {
                println("Caught $e")
            }

            println("Root")
        }
    }
}
```

## 13.3 동시성 통신

### 13.3.1 채널(Channel)

`send()`

`receive()`

채널 기본구현은 크기가 정해진 내부 버퍼를 사용한다. 버퍼가 꽉 차면 최소 하나 이상의 채널 원소가 상대방에 의해 수신될 때 까지 send() 호출이 일시 중단된다.

이와 비슷하게, 버퍼가 비어있으면 누군가 하나 이상의 원소를 채널로 송신할 때 까지 receive() 호출이 일시 중단된다.

```kotlin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {
    runBlocking {
        val streamSize = 5
        val channel = Channel<Int>(3)

        launch {
            for (n in 1..streamSize) {
                delay(Random.nextLong(100))
                val square = n * n
                println("Sending: $square")
                channel.send(square)
            }
        }

        launch {
            for (i in 1..streamSize) {
                delay(Random.nextLong(100))
                val n = channel.receive()
                println("Receiving: $n")
            }
        }
    }
}
```

채널은 모든 값이 송신된 순서 그대로 수신되도록 보장한다.

- `Channel.UNLIMITED` (= Int.MAX_VALUE)
    - 채널 용량 제한은 없음. 내부 버퍼는 필요에 따라 증가한다.
    - send() 시에 중단되지 않는다.
    - 버퍼가 비어있으면 receive()시 중단될 수 있다.
- `Channel.RENDEZVOUS` (= 0)
    - 내부 버퍼가 없는 랑데부 채널
    - send() 호출은 receive() 호출까지 일시 중단된다.
- `Channel.CONFLATED` (= -1)
    - 송신된 값이 합쳐지는 채널
    - send()로 보낸 원소를 최대 하나까지만 버퍼에 저장하고, 누군가에 의해 수신되기 전에 send()가 호출되면 값을 덮어쓴다. 수신되지 못한 값은 소실된다.
    - send()는 일시중단 되지 않는다.

channel.close()를 이용해 더 이상 데이터를 보내지 않는다고 알려줄 수 있다.

```kotlin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {
    runBlocking {
        val streamSize = 5
        val channel = Channel<Int>(Channel.CONFLATED)

        launch {
            for (n in 1..streamSize) {
                delay(Random.nextLong(100))
                val square = n * n
                println("Sending: $square")
                channel.send(square)
            }

            channel.close()
        }

        launch {
            for (n in channel) {
                println("Receiving: $n")
                delay(200)
            }
        }
    }
}
```

```kotlin
channel.consumeEach {
	println("Receving: $it")
	delay(200)
}
```

채널이 닫힌 후 send()를 호출하면 `ClosedSendChannelExcepion` 이 발생한다.

채널 원소가 다 소진되면 receive()를 호출시에도 같은 에러가 발생한다.

생산자와 소비자가 하나씩일 필요는 없다. 한 채널을 여러 코루틴이 동시에 읽을 수도 있다 (fan out)

여러 생산자 코루틴이 한 채널에 써넣은 데이터를 한 소비자 코루틴이 읽을수도 있다 (fan in)

### 13.3.2 생산자

`produce()` 코루틴 빌더 : send() 메서드를 제공하는 ProducerScope 영역을 도입해준다.

```kotlin
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = produce {
            for (n in 1..5) {
                val square = n * n
                println("Sending: $square")
                send(square)
            }
        }

        launch {
            channel.consumeEach {
                println("Receiving: $it")
            }
        }
    }
}
```

코루틴이 종료되면 produce() 빌더가 채널을 자동으로 닫아준다.

produce() 안에서 예외가 발생하면 예외를 저장했다가 해당 채널에 대해 receive()를 가장 처음 호출한 코루틴쪽에 예외가 던져진다.

### 13.3.3 티커 (ticker)

랑데부 채널. Unit값을 계속 발생시키되 한 원소와 다음 원소의 발생 시점이 주어진 지연 시간만큼 떨어져있는 스트림을 만든다.

- delayMillis : 티커 원소의 발생 시간 간격
- initialDelayMillis : 최초 원소 발생시까지 시간 간격. 기본값은 delayMillis와 동일.
- context : 티커를 실행할 코루틴 문맥. 기본값은 빈 값.
- mode : 티커의 행동을 결정하는 TickerMode 값
    - TickerMode.FIXED_PERIOD : 실제 지연 시간을 조정한다.
    - TickerMode.FIXED_DELAY : 실제 흘러간 시간과 관계없이 delayMillis 만큼 지연시켜서 송신한다.

```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() {
    runBlocking {
        val ticker = ticker(100)
        println(withTimeoutOrNull(50) { ticker.receive() })
        println(withTimeoutOrNull(60) { ticker.receive() })
        delay(250)
        println(withTimeoutOrNull(1) { ticker.receive() })
        println(withTimeoutOrNull(60) { ticker.receive() })
        println(withTimeoutOrNull(60) { ticker.receive() })
    }
}
```

### 13.3.4 액터 (actor)

내부 상태와 다른 액터에게 메시지를 보내서 동시성 통신을 진행할 수 있느 ㄴ수단을 제공하는 객체.

액터는 자신에게 들어오는 메시지를 listen하고,

자신의 상태를 바꾸면서 메시지에 응답할 수 있으며,

다른 메시지를 (자기 자신이나 다른 액터에게) 보낼 수 있고,

새로운 액터를 시작할 수 있다.

액터의 상태는 액터 내부에 감춰져 있으므로, 다른 액터가 직접 이 상태에 접근할 수 없다.

액터는 특별한 영역(ActorScope)을 만들며, 이 영역은 기본 코루틴 영역에 자신에게 들어오는 메시지에 접근할 수 있는 수신자 채널이 추가된 것이다.

```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

sealed class AccountMessage

class GetBalance(
    val amount: CompletableDeferred<Long>
) : AccountMessage()

class Deposit(
    val amount: Long
) : AccountMessage()

class Withdraw(
    val amount: Long,
    val isPermitted: CompletableDeferred<Boolean>
) : AccountMessage()

fun CoroutineScope.accountManager(
    initialBalance: Long
) = actor<AccountMessage> {
    var balance = initialBalance

    for (message in channel) {
        when (message) {
            is GetBalance -> message.amount.complete(balance)
            is Deposit -> {
                balance += message.amount
                println("Deposited ${message.amount}")
            }
            is Withdraw -> {
                val canWithdraw = balance >= message.amount

                if (canWithdraw) {
                    balance -= message.amount
                    println("Withdraw ${message.amount}")
                }

                message.isPermitted.complete(canWithdraw)
            }
        }
    }
}

private suspend fun SendChannel<AccountMessage>.deposit(
    name: String,
    amount: Long
) {
    send(Deposit(amount))
    println("$name: deposit $amount")
}

private suspend fun SendChannel<AccountMessage>.tryWithdraw(
    name: String,
    amount: Long
) {
    val status = CompletableDeferred<Boolean>().let {
        send(Withdraw(amount, it))
        if (it.await()) "OK" else "DENIED"
    }

    println("$name : withdraw $amount ($status)")
}

private suspend fun SendChannel<AccountMessage>.printBalance(
    name: String
) {
    val balance = CompletableDeferred<Long>().let {
        send(GetBalance(it))
        it.await()
    }

    println("$name : balance is $balance")
}

fun main() {
    runBlocking {
        val manager = accountManager(100)
        withContext(Dispatchers.Default) {
            launch {
                manager.deposit("Client #1", 50)
                manager.printBalance("Client #1")
            }

            launch {
                manager.tryWithdraw("Client #2", 100)
                manager.printBalance("Client #2")
            }
        }

        manager.tryWithdraw("Client #0", 1000)
        manager.printBalance("Client #0")
        manager.close()
    }
}
```

## 13.4 자바 동시성 사용하기

### 13.4.1 스레드 시작하기

`thread()`

- start: 스레드를 생성하자마자 시작할지 여부 (디폴트 true)
- isDaemon: 스레드를 데몬으로 시작할지 여부 (디폴트 false)
- contextClassLoader : 스레드 코드가 클래스와 자원을 적재할 때 사용할 클래스 로더 (디폴트 null)
- name : 스레드 이름.
- priority : Thread.MIN_PRIORITY(=1) ~ Thread.MAX_PRIORITY(=10). 디폴트는 -1.
- block : () → Unit 타입의 함숫값

`timer()`

- name : 타이머 스레드의 이름
- daemon : 타이머 스레드를 데몬 스레드로 할지 여부 (디폴트 false)
- startAt : 최초로 타이머 이벤트가 발생하는 시간을 나타내는 Date 객체
- period : 연속된 타이머 이벤트 사이의 시간 간격 (millisecond)
- action : 타이머 이벤트가 발생할 때 마다 실행될 TimeTask.() → Unit 타입의 람다

### 13.4.2 동기화와 락

`syncronized()`

`@syncronized` 메서드에 사용

`withLock()` 함수가 알아서 락을 풀어줌

`ReenterantReadWriteLock` 읽기/쓰기 락. 락을 이미 획득한 스레드가 다시 같은 락을 요청해도 문제없이 작동하는 재진입 가능한 락.

wait(), notify(), notifyAll()은 코틀린 Any에 없다. java.lang.Object로 캐스팅해서 쓸 수 있다.