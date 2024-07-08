package com.example.kafkatest.dto

data class SomeDto(
    val id: Long,
    val name: String,
    val attr: SomeAttrDto
)

data class SomeAttrDto(
    val age: Long,
    val tall: Long,
    val weight: Long,
    val addr: String
)