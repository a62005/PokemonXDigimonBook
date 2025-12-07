package com.example.lib_database.bean

interface ISimpleBean {
    val id: Int
    val name: String
    val imageUrl: String

    fun getMainType(): String?
}