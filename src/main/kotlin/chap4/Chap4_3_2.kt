package chap4

import java.io.File

fun main() {
    println("[Chapter 4.3.2] 늦은 초기화 ")

    println("""
       어떤 프로퍼티는 클래스 인스턴스가 생성된 뒤에 + 해당 프로퍼티가 사용되는 시점보다는 이전에 초기화되어야 할 수도 있다.
       (프로퍼티를 꼭 사용하는 게 아니라면 메모리 손해를 볼 수 있음) 
       
       보통은 아래와 같이 디폴트 값(ex. null)을 대입 후 실제 값이 필요할 때 대입하는데,
       loadFile()로 파일을 읽어온다고 가졍 -> 실제 값이 사용 전에 항상 초기화될 것이므로, 
       절대 null이 될 수 없는 값이라는 사실을 알고 있음에도 null 가능성을 처리해야한다. 
    """)

    class Content {
        var text: String? = null

        fun loadFile(file: File) {
            text = file.readText()
        }
    }
    fun getContentSize(content: Content) = content.text?.length ?: 0

    println("""
       lateinit 표시가 붙은 프로퍼티는 값을 읽을 때 프로퍼티가 초기화되었는지 검사 
        -> 초기화 안되었으면 UninitializedPropertyAccessException 발생함. 
       
       lateinit 프로퍼티를 만들기 위해서는
        * 1) 프로퍼티가 변경되어야 하므로 var 로 지정해야 함
        * 2) 프로퍼티의 타입은 널이 아닌 타입이어야 함
        * 3) Int나 Boolean같은 Primitive 값을 표현하는 타입이 아니어야 함 
        * 4) lateinit 프로퍼티를 정의하면서 초기화 식을 지정해 값을 바로 대입할 수 없음 (lateinit의 의미가 없음) 
    """)

    lateinit var text: String
    fun readText() {
        text = readLine()!!
    }
    readText()
    println(text)

}