package com.example.lib_database.bean

interface IDetailBean: ISimpleBean {
    val types: List<String>?
    val description: String?
}