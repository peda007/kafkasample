package com.example.kafkatest.config

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff
import java.lang.Exception

@Configuration
class KafkaConfig(
    private val defaultKafkaConsumerFactory: DefaultKafkaConsumerFactory<String, Any>,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        return ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            consumerFactory = defaultKafkaConsumerFactory
            setCommonErrorHandler(customErrorHandler())
            setRecordInterceptor(KafkaListenerInterceptor())
        }
    }

    fun customErrorHandler(): CommonErrorHandler {
        val doOnError: (t: ConsumerRecord<*, *>, u: Exception) -> Unit = { record, exception ->

            exception.printStackTrace()

            val key = record.key()
                ?.toString()
                ?: "emptyKey"
            val producerRecord: ProducerRecord<String, Any> = ProducerRecord(
                "dead.letter",
                record.partition(),
                key,
                record.value(),
                record.headers()
            )
            kafkaTemplate.send(producerRecord)
        }
        return DefaultErrorHandler(doOnError, FixedBackOff(1000L, 2))
    }
}