package com.example.pokemonxdigimon.base

import com.example.lib_database.bean.ISimpleBean

/**
 * 列表頁面的基礎 UiState 介面
 */
interface BaseListUiState<T : ISimpleBean> : BaseUiState {
    val monsterList: List<T>
    val isLoadingMore: Boolean
    val hasMore: Boolean
}
