package br.com.harisson.sales.fraudcheck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FraudCheckApplication

fun main(args: Array<String>) {
	runApplication<FraudCheckApplication>(*args)
}
