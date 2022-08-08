package chap2

fun main() {
    println("[Chapter 2.1.5] 식과 연산자")

    println("""
        연산자 우선순위
        https://kotlinlang.org/docs/reference/grammar.html#expressions 
        Precedence	Title	Symbols
        Highest	Postfix	++, --, ., ?., ?
                Prefix	-, +, ++, --, !, label
                Type RHS	:, as, as?
                Multiplicative	*, /, %
                Additive	+, -
                Range	..
                Infix function	simpleIdentifier
                Elvis	?:
                Named checks	in, !in, is, !is
                Comparison	<, >, <=, >=
                Equality	==, !=, ===, !==
                Conjunction	&&
                Disjunction	||
                Spread operator	*
        Lowest	Assignment	=, +=, -=, *=, /=, %=
    """)

}