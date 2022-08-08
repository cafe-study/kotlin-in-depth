package chap2

fun main() {
    println("[2.2.5] 기본타입 > 문자 타입 Char")

    println("""
       Char 타입은 유니코드 한 글자를 표현하며 16비트 (2byte)
       리터럴 표현식은 '(작은 따옴표) 사이에 문자를 기록한다.
    """)

    val z = 'z'
    val han = '한'

    println("""
        newline 과 같은 특수문자 입력을 위해 escape 를 제공한다.
        \t : tab
        \b : back space
        \n : new line
        \r : carriage return
        \' : single quote
        \" : double quote
        \\ : backslash
        \$ : dollar sign
    """)

    val quote = '\''
    val newline = '\n'

    println("""
        \u 다음에 네자리 16진수를 넣는 시퀀스를 사용해 유니코드 문자를 리터럴에 넣을 수 있음
    """)

    val pi = '\u03c0' // π


    println("""
       Char 값은 문자 코드뿐이지만, Char 자체를 "수 타입을 취급하지 않는다" 
       그러나, +, - 연산자을 통한 산술 연산은 허용한다.
    """)

    var a = 'a'
    var h = 'h'
    println(a+5) // f
    println(a-5) // \
    println(h - a) // 7
    println(--h) // g
    println(++a) // b
    // println(a*2) // Compiler Error, Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
}