package by.ttre16.briana

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
@EnableEurekaClient
class BrianaMail

fun main(args: Array<String>) {
    runApplication<BrianaMail>(*args)
}
