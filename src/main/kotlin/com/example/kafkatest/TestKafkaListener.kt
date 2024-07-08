package com.example.kafkatest

import com.example.kafkatest.dto.SomeDto
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TestKafkaListener {

    @KafkaListener(topics = ["test.consume"])
    fun onMessage(someDto: SomeDto) {
        println("============= result")
        println(someDto)
        throw RuntimeException("예외다 예외!!")
    }

    @KafkaListener(topics = ["dead.letter"])
    fun onDeadLetter(deadLetter: Any) {
        println("dead letter: ${deadLetter}")
    }
}