package com.example.kafkatest

import com.example.kafkatest.dto.SomeAttrDto
import com.example.kafkatest.dto.SomeDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    @GetMapping
    fun produce() {
        val someDto = SomeDto(
            id = 1,
            name = "black",
            attr = SomeAttrDto(
                age = 35,
                tall = 185,
                weight = 80,
                addr = "잠실동"
            )
        )
        kafkaTemplate.send("test.consume", "testKey", someDto)
    }
}