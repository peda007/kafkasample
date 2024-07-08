package com.example.kafkatest.config

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerInterceptor
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.RecordInterceptor

class KafkaListenerInterceptor : RecordInterceptor<String, Any> {
    override fun intercept(
        record: ConsumerRecord<String, Any>,
        consumer: Consumer<String, Any>
    ): ConsumerRecord<String, Any> {
        println("=========${record.topic()}: ${record.key()}-${record.value()}")
        return record
    }
}