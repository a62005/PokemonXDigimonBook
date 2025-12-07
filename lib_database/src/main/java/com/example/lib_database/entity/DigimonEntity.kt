package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lib_database.bean.IDetailBean
import com.example.lib_database.bean.ISimpleBean

@Entity
data class DigimonEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val name: String,
    override val imageUrl: String,
    override val types: List<String>?,
    override val description: String?
): IDetailBean {
    override fun getMainType(): String? {
        return types?.firstOrNull()
    }
}

data class SimpleDigimonBean(
    override val id: Int,
    override val name: String,
    override val imageUrl: String
): ISimpleBean {
    override fun getMainType(): String? {
        return null
    }
}
