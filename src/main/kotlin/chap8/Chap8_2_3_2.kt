package chap8.chap8_2_3_2

sealed class Expr

data class Const(val num: Int) : Expr()
data class Neg(val operand: Expr) : Expr()
data class Plus(val op1: Expr, val op2: Expr) : Expr()
data class Mul(val op1: Expr, val op2: Expr) : Expr()

fun Expr.eval(): Int = when (this) {
    is Const -> num
    is Neg -> -operand.eval()
    is Plus -> op1.eval() + op2.eval()
    is Mul -> op1.eval() * op2.eval()
}

fun main() {
    val expr = Mul(Plus(Const(1), Const(2)), Const(3))

    println(expr)
    println(expr.eval())

    val expr2 = expr.copy(op1 = Const(2))

    println(expr2)
    println(expr2.eval())
}