## 8.1 상속

### 8.1.1 하위 클래스 선언

* 상속은 : 으로 표시
* 코틀린 클래스는 기본적으로 final 이므로, 상속을 위해서는 수퍼클래스에 open을 추가해야 함
    * 깨지기 쉬운 기반 클래스 문제를 줄이기 위해
* data class는 open으로 선언할 수 없다.
* 인라인 클래스는 다른 클래스를 상속할 수 없고, 다른 클래스의 상위 클래스 역할을 할 수도 없음
* 객체는 다른 클래스를 상속 가능하나, 객체를 상속할 수는 없음

| 종류           | 상속 가능 | 수퍼 클래스 가능 |
|--------------|-------|-----------|
| data class   | X     | O         |
| inline class | X     | X         |
| object       | O     | X         |

https://github.com/cafe-study/kotlin-in-depth/blob/3fbc61887509fb0efabb8f8f127f0d7722e674e4/src/main/kotlin/chap8/Chap8_1_1.kt#L3-L26