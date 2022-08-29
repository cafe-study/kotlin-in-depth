package chap4

object Application {
    val name = "My Application"
    override fun toString() = name

    fun exit() {}
}

/*
// Java로 변환한 코드
public final class Application {
    @NotNull
    private static final String name;
    @NotNull
    public static final Application INSTANCE;

    @NotNull
    public final String getName() {
        return name;
    }

    @NotNull
    public String toString() {
        return name;
    }

    public final void exit() {
    }

    private Application() {
    }

    static {
        Application var0 = new Application();
        INSTANCE = var0;
        name = "My Application";
    }
} */

fun main() {
    println("[Chapter 4.4] 객체 ")

    println("[Chapter 4.4.1] 객체 선언 ")

    println("""
       object 키워드로 인스턴스가 하나만 존재하는 싱글턴 패턴을 선언할 수 있다!
       (class 대신 선언함)
       
       일반적으로 객체의 인스턴스가 단 하나뿐이므로, 인스턴스만 가르켜도 어떤 타입을 쓰는지 알 수 있다.
       따라서 객체를 타입으로 사용해도 무방하다. 
       객체 정의는 thread-safe 하다. 여러 쓰레드에서 싱글턴 객체에 접근하더라도 
       오직 한 인스턴스만 공유되고 초기화 코드도 단 한번만 실행되도록 보장한다. 
    """)

    fun describe(app: Application) = app.name // Application은 타입임
    println(Application) // Application은 값임

    println("""
        디컴파일 해보면 java 처럼 비공개 생성자 + static instance 로 구현된다.
    """)

    println("""
       object 객체 선언도 멤버 함수와 프로퍼티, 초기화 블록을 포함할 수 있다
       객체 인스턴스는 항상 암시적으로 만들어지기 때문에 주생성자/부생성자가 없다. (의미없음)
       객체 본문에 들어있는 클래스에는 inner 사용 X
         * 내부 클래스의 인스턴스는 항상 바깥쪽 클래스 인스턴스와 연관되는데, 객체 선언의 경우 인스턴스가 하나뿐이므로 inner 변경자가 불필요함
    """)

    println("""
       자바에는 Utiliy 클래스가 있으나 kotlin에는 static 키워드가 없다. 
         * 코틀린에서는 top-level function을 작성 후 import함. 
    """)
}