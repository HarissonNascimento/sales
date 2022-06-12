package br.com.harisson.sales.tester

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TesterApplication

fun main(args: Array<String>) {
	runApplication<TesterApplication>(*args)
}
