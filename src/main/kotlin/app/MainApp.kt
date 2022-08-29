package app


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
class MainApp
    val logger by LoggerDelegate()

    fun main(args:Array<String>) {
        //logger.info("Hello, Kotlin Verse!")
        runApplication<MainApp>(*args)
    }

