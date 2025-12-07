package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.bean.ISimpleBean
import com.example.pokemonxdigimon.base.BaseUiState

data class ListDataUiState(
    val dataList: List<ISimpleBean> = emptyList(),
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    override var error: String? = null
) : BaseUiState