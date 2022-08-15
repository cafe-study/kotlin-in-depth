package chap4


val prefix = "Hello, " // 최상위 불변 프로퍼티

fun main() {
    println("[Chapter 4.3] 단순한 변수 이상인 프로퍼티 ")

    println("[Chapter 4.3.1] 최상위 프로퍼티 ")

    println("""
       최상위 수준에 프로퍼티 정의하면 => 전역 변수나 상수와 비슷한 역할을 함 
    """)

    val name = readLine() ?: return
    println("$prefix$name")


    println("""
       최상위 프로퍼티에 가시성(public/internal/private) 도 지정할 수 있음 
       최상위 프로퍼티를 임포트할 수도 있음 
    """)
    /*
    // util.kt
    package util

    val prefix = "Hello, "

    // main.kt
    package main

    import util.prefix

    fun main() {
        val name = raadLine() ?: return
        println("$prefix$name")
    }
    */
}