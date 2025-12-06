package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DigimonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val imageUrl: String,
    val type: List<String>,
    val description: String
)

data class SimpleDigimonBean(
    val id: Int,
    val name: String,
    val imageUrl: String
)
