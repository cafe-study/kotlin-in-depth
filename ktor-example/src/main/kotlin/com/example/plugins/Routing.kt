package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.h2

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        // p.628 - 라우팅 경로에 파리미터가 포함된 케이스 + null 파라미터도 허용하는 케이스
        get("hello/{userName?}") {
            val userName = call.parameters["userName"] ?: "모르는 분"

            call.respondHtml {
                body {
                    h1 { +"Hello, $userName" }
                }
            }
        }

        // p.629 - 파라미터 값을 세그먼트와 매치시켜야 하지만 실제로 사용하지 않는 경우
        get("hello2/*") {
            call.respondHtml {
                body {
                    h1 { +"Hello, World!" }
                }
            }
        }

        // p.630 - 파라미터 이름 뒤에 ...을 붙이면 경로 뒤쪽에 모든 세그먼트와 매치됨
        get("/calc/{data...}") {
            val data = call.parameters.getAll("data") ?: emptyList()
            call.respondHtml {
                body {
                    h1 {
                        if (data.size != 3) {
                            +"Invalid data"
                            return@h1
                        }

                        val (op, argStr1, argStr2) = data
                        val arg1 = argStr1.toBigIntegerOrNull()
                        val arg2 = argStr2.toBigIntegerOrNull()
                        if (arg1 == null || arg2 == null) {
                            +"Integer numbers expected"
                            return@h1
                        }
                        val result = when (op) {
                            "+" -> arg1 + arg2
                            "-" -> arg1 - arg2
                            "*" -> arg1 * arg2
                            "/" -> arg1 / arg2
                            else -> null
                        }
                        +(result?.toString() ?: "Invalid operation")
                    }
                }
            }
        }
        // p.631 - 트리 구조로 URL을 매치시키는 기능
        method(HttpMethod.Get) {
            route("user/{name}") {
                route("sayHello") {
                    handle {
                        call.respondText { "Hello, ${call.parameters["name"]}" }
                    }
                }
                route("sayBye") {
                    handle {
                        call.respondText { "Bye, ${call.parameters["name"]}" }
                    }
                }
            }
        }
        // p.633 - 파라미터에 따라 적절한 응답을 선택할 수 있게 하는 예제
        route("/user2/{name}", HttpMethod.Get) {
            param("action", "sayHello") {
                handle {
                    call.respondHtml {
                        body {
                            h2 {
                                +"Hello, ${call.parameters["name"]}"
                            }
                        }
                    }
                }
            }
            param("action", "sayBye") {
                handle {
                    call.respondHtml {
                        body {
                            h2 {
                                +"Bye, ${call.parameters["name"]}"
                            }
                        }
                    }
                }
            }
        }
    }
}
