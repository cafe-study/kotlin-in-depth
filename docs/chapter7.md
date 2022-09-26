# 7 컬렉션과 I/O
컬렉션과 I/O 자세히 알아보기
7.1 컬렉션

- 엘리먼트들로 이뤄진 그룹을 저장하기 위해 설계된 객체
- 컬렉션을 조작하는 모든 연산은 인라인 함수이다
  - 이터러블
    -  Iterable<T> 로 표현, 즉시 계산되는 상태가 있는 컬렉션을 표현한다.(컬렉션 생성할 때 초기화 된다)

```
       data class Person(
       val name: String,
       val age: Int,
       val driverLicense: Boolean = false
       )

  val friendGroup = listOf(
  Person("jo", 19),
  Person("Mic", 15),
  Person("Hay", 33, true),
  Person("Cal", 25)
  )

  fun main() {
  val list = listOf("red", "green", "blue")

        for (item in list) {
            print(item + " ")
        }
       println(" ")   
       val friendGroupAny = friendGroup.any { it.driverLicense}
       println( "라이센스를 가진 사람이 적어도 1명: $friendGroupAny" )
     
       val friendGroupNone = friendGroup.none {it.age < 18} 
       println("18세보다 어린 사람이 없다: $friendGroupNone" )
  }
```



자바와 Iterator의 차이는 remove메소드가 없다. remove메소드는 MutableIterator에 있다.

    //iterator
    fun main() {
        val stringList = listOf<String>("a", "b", "c")
        val iterator = stringList.iterator()
        
       while (iterator.hasNext() ) {
           println(iterator.next())
       }
    }

MutableIterator remove

    fun main() {
        val stringList = mutableListOf("a", "b", "c", "d")
        val mutableIterator = stringList.iterator()
        
        mutableIterator.next()
        mutableIterator.remove()
        println("삭제 후: $stringList")
    }

시퀀스

https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/-sequence/


Iterable을 수행할 때는 결과를 즉시 리턴한다. (전체 요소 실행)
sequences는 각각 하나의 element에 대해 모든 단계를 수행한다.

시퀀스 생성방법

요소 선언

        val stringSequence = sequenceOf("a", "b", "c", "d")
    
        val numbers = listOf("one", "two", "three", "four")
        val numberSequence = numbers.asSequence() //iterable object

//261p
함수 사용

    fun main() {
        val oddNumbers = generateSequence(1) {it + 2}
        println(oddNumbers.take(5).toList())
        //println(oddNumbers.count()) null이 리턴되지 않아서 무한 호출 오류
    }
    //null 반환 추가
    fun main() {
        val oddNumbersLessThan10 = generateSequence(1) { if (it < 10) it + 2 else null}
         println(oddNumbersLessThan10.toList())
        println(oddNumbersLessThan10.count())
    }

청크 https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/chunked-sequence.html

    fun main() {
        val words = "one two three four five six seven eight nine ten".split(' ')
            val chunks = words.chunked(3)
        println(chunks)
    }

Map
list를 map으로 변환
associateBy 사용


    data class Person (val name: String, val age:Int)
    
    
    fun main() {
        val personList = listOf(Person("a", 10), Person("b", 12), Person("c", 15))
        val personToMap = personList.associateBy({it.name}, {it.age})
        println(personToMap)
    }


List.map{}.toMap() 사용

    fun main() {
        val personList = listOf(Person("a", 10), Person("b", 12), Person("c", 15))
        val personToMap = personList.map {it.name to it.age}.toMap()
        println(personToMap)
    }

map remove 사용
map 선언 시 Pair 또는 to 중위연산자로 선언 가능

    fun main() {
            val map1 = mutableMapOf(1 to "z", 2 to "y", Pair(3,"x"))
        val map2 = mapOf(1 to "z", 2 to "y", Pair(3,"x"))
        println(map1)
        println(map2)
        println(map1.remove(1)) // mutable은 삭제 가능
        //println(map2.remove(1)) 
    }


262p yield => 13장 동시성
기본 컬렉션 연산
map 이터레이션

      fun main() {
            val map = mapOf(1 to "one", 2 to "two", 3 to "three")
        
        for ((key, value) in map){
            println("$key -> $value")
        }
    }

map minus 연산



    fun main() {
        val numberMap = mapOf(1 to "I", 5 to "V")
            val numberSortedMap = sortedMapOf(1 to "I", 5 to "V")
        numberSortedMap.put(100, "C")
        println(numberSortedMap)
        numberSortedMap -= 100 // minus연산은 key만 적용
        println(numberSortedMap)
        numberSortedMap -= listOf(1)
        println(numberSortedMap)
        
        //numberMap -= 1
        println(numberMap)
    }

list 이터레이션

    fun main() {
            val list = listOf("a", "b", "c").forEach {println("'$it'")}
        //원소 인덱스 참조
        listOf(10, 20, 30).forEachIndexed {i , n -> println("$i: ${n*n}")}
    }

원소 접근

    fun main() {
      val numbers = linkedSetOf("one", "two", "three", "four", "five")
      println(numbers.elementAt(0))
      println(numbers.first())
      println(numbers.last())
      
      val numbersSortedSet = sortedSetOf("one", "two", "three", "four")
      println(numbersSortedSet.elementAt(0)) // ascending order
      //startWith
      println(numbers.last {it.startsWith("f")})
      println(numbers.firstOrNull {it.length > 6})
      println("랜덤: " + numbers.random())
    }

집계
count

    fun main() {
      val numbers = listOf(1,2,3,4).count()
      println(numbers)
      val numbersMoreThan0 = listOf(1,2,3,4).count({it > 2})
      println(numbersMoreThan0)
      val numbers = listOf(1,2,3,4).count()
      println(numbers)
      val numbersMoreThan0 = listOf(1,2,3,4).count({it > 2})
      println(numbersMoreThan0)
      
      println(mapOf(1 to "I", 5 to "V", 10 to "X").count{it.key == 1})
    }

sum ( 타입을 수로 변환할 수 있으면 합계를 사용 가능)


    fun main() {
     println(listOf(1,2,3,4).sumOf {it / 4 }) //1
     println(listOf(1,2,3,4).sumOf {it / 4.0 }) //2.5
     //문자형 숫자 합
     println(arrayOf("1","2","3").sumOf{ it.toInt()})
    }

avaerage

    fun main() {
     val myEmptyList: List<Int> = listOf()
     println(myEmptyList.average()) //NaN
     println(listOf(1,2,3,4).average()) 
    }

최소 최대

    class Person(val firstName:String,
                val familyName:String,
                val age: Int) {
        override fun toString() = "$firstName $familyName : $age"
    }
    
    fun main() {
        val list = listOf(1,2,3,4,5)
        println(list.min())
        println(list.max())
        
             val persons = sequenceOf(
                Person("brook", "Watts", 25),
            Person("Silver","Hudson", 30),
            Person("Dane", "Ortiz", 19),
            Person("val", "Hall", 28)
        )
        println(persons.minByOrNull {it.firstName}) //19
        println(persons.maxByOrNull {it.firstName}) //28
        println()
        println(persons.minByOrNull {it.familyName}) //19
        println(persons.maxByOrNull {it.familyName}) //28
        println()
        println(persons.maxByOrNull {it.age}) //28
    }

prefix, postfix , seperator, limit, truncated


    fun main() {
       val list = listOf(1,2,3,4,5)
       println(list.joinToString(prefix = "[", postfix = "]")) //[1, 2, 3, 4, 5]
       println(list.joinToString(separator = "|")) // 1|2|3|4|5
       println(list.joinToString(limit=2))
       println(list.joinToString(limit=1,
                                separator = " ",
                                truncated = "???")) //default는 ....
    }

Accumulate함수 fold, reduce
fold는 초기값이 존재, reduce는 초기값이 없음

    fun main() {
       val list = listOf(2, 5)
       val multipleSumReduce = list.reduce {total, num -> total + num * 2} //total 초기값이 포함안됨
       println("reduces: $multipleSumReduce") // 12
       val multipleSumFold = list.fold(0) {total, num -> total + num * 2} //total 초기값이 포함됨
       println("folded: $multipleSumFold") // 14
    }

걸러내기

-  Array, Iteratable을 필터링하면 List를 얻는다.
- Map을 필터링 하면 Map을 얻는다
- Sequence를 필터링 하면 Sequence를 얻는다

flatMap, flatten

    fun main() {
      println(setOf("abc", "def", "ghi").flatMap{it.asIterable()})
       println(setOf("abc", "def", "ghi").flatMap{it.map {"num $it"}})
      
      val numbers = listOf(listOf(1,2,3), listOf(5,6,7), listOf(8,9,0))
      val result = numbers.flatten()
      println(result)
    }

associate

    fun main() {
            println(listOf("red", "green", "blue").associate {it.uppercase() to it.length})
        println(listOf("red", "green", "blue").associateBy {it.uppercase() to it.length}) //withKey
    }

하위 컬렉션 추출

Slice
지정된 위치의 element로 새로운 list를 구성한다.
range나 상수 값을 가진 collection으로 위치값을 전달할 수 있다
```
    fun main() {
      val numbers = listOf("one", "two", "three", "four", "five", "six")    
            println(numbers.slice(1..3))
            println(numbers.slice(0..4 step 2))
            println(numbers.slice(setOf(3, 5, 0)))
    }
    //[two, three, four]
    //[one, three, five]
    //[four, six, one]
```
Take and Drop


- take 첫번째 element부터 특정 개수만큼 가져온다
- takeLast 마지막 element부터 가져온다
- drop 첫번째 element부터 특정 개수를 제외하고 가져온다
- dropLast 마지막 element부터 특정 개수를 제외하고 가져온다
  fun main() {
  val numbers = listOf("one", "two", "three", "four", "five", "six")    
  println(numbers.take(3))
  println(numbers.takeLast(3))
  println(numbers.drop(1))
  println(numbers.dropLast(1))
  }
  //[one, two, three]
  //[four, five, six]
  //[two, three, four, five, six]
  //[one, two, three, four, five]

takeWhile predicate와 매칭되지 않는 첫번째 요소를 발견할 때까지 실행
takeLastWhile 마지막 element부터 매칭된 결과

    fun main() {
        val list = List(6) {it * it }
        println(list)
        println(list.takeWhile{it < 10})
        println(list.takeLastWhile{it > 10})
        println(list.dropWhile{it < 10})
        println(list.dropLastWhile{it > 10})
    }
    //[0, 1, 4, 9, 16, 25]
    //[0, 1, 4, 9]
    //[16, 25]
    //[16, 25]
    //[0, 1, 4, 9]
windowed

windowed(개수) 컬렉션에서 위치를 1씩 증가하면서 개수만큼씩 분할 (step 증가폭 지정)

    fun main() {
            val numbers = listOf(0,1,2,3,4,5,6,7,8,9)   
        println(numbers.windowed(3))
        println(numbers.windowed(3, step = 2))
    }

zip, zipWithNext()
zip 두 개의 list를 결합
인접한 2개의 요소를 묶어서 각각의 요소가 pair로 된 컬렉션 반환
zipWithNext 단일 리스트 pair

    data class Person(
        val name:String,
        val id:Int
    )
    
    fun main() {
            val left = listOf("a", "b", "c", "d")
        val right = listOf("e", "f", "g")
        val ids = listOf(1731, 9274, 8378)
       println(left.zip(right))
       //[(a, e), (b, f), (c, g)]
      val names = listOf("Bob", "Jill", "jim")
      val zipNames = names.zip(ids) { name, id ->
          Person(name, id)
      } 
      println(zipNames)
      
      println(left.zipWithNext()) //단일 list pair
    }

sort

sorted() imutable list에 적용가능하고 원본 변경 없이 정렬된 리스트를 생성하여 리턴


    fun main() {
     val list = listOf(10, 100, 50, 2, 77, 4)
    
        val sorted = list.sorted()
        println("sorted: $sorted")
        //sorted: [2, 4, 10, 50, 77, 100]
        val sortedDesending = list.sortedDescending()
        println("desending: $sortedDesending")
    }

Mutable 리스트 정렬

    fun main(args: Array<String>){
    
        val list = mutableListOf(10, 100, 50, 2, 77, 4)
    
        list.sort()
        println("sorted: $list")
    }
    //sorted: [2, 4, 10, 50, 77, 100]

역순 정렬 - ed 차이

    fun main(args: Array<String>){
      // 1. immutable list
        val list = listOf(10, 100, 50, 2, 77, 4)
        val sorted = list.sorted().reversed()
        println("sorted : $sorted")
    
        // 2. mutable list
        val list2 = mutableListOf(10, 100, 50, 2, 77, 4)
        list2.sort()
        list2.reverse()
        println("sorted : $list2")
    }

with로 규칙 추가하여 비교

    fun main(args: Array<String>){
      val list = listOf("apple", "banana", "kiwi", "orange", "watermelon")
    
        val alphabetOrder = list.sorted()
        println("Alphabet order: $alphabetOrder")
    
        val comparator : Comparator<String> = compareBy { it.length }
        val lengthOrder = list.sortedWith(comparator)
        println("Length order: $lengthOrder")
    }

with 날짜 비교

    import java.util.*
    
    fun main(args: Array<String>){
       val dates = mutableListOf(
            Date(2020, 4, 3),
            Date(2021, 5, 16),
            Date(2020, 4, 29)
        )
    
        dates.sortWith(compareBy<Date> { it.year }.thenBy { it.month }.thenBy { it.day })
    
        dates.forEach { println(it) }
    }


정렬요소 지정
```
    fun main(args: Array<String>){
        val list = mutableListOf("b" to 4, "a" to 10, "c" to 8, "h" to 5)
        println("list: $list")
    
        list.sortBy { it.first }
        println("sortByFirst: $list")
    
        list.sortBy { it.second }
        println("sortBySecond: $list")
       
    }
```

