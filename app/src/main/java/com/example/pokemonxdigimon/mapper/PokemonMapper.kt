package com.example.pokemonxdigimon.mapper

import com.example.lib_database.entity.PokemonEntity
import com.example.lib_database.entity.Stat
import com.example.network.data.pokemon.PokemonDetailModel

object PokemonMapper {
    
    fun toEntity(detail: PokemonDetailModel): PokemonEntity {
        return PokemonEntity(
            id = detail.id,
            name = detail.name,
            height = detail.height,
            weight = detail.weight,
            types = detail.types.map { it.type.name },
            stat = Stat(
                hp = detail.stats.find { it.stat.name == "hp" }?.baseStat ?: 0,
                attack = detail.stats.find { it.stat.name == "attack" }?.baseStat ?: 0,
                defense = detail.stats.find { it.stat.name == "defense" }?.baseStat ?: 0,
                speed = detail.stats.find { it.stat.name == "speed" }?.baseStat ?: 0,
                exp = detail.baseExperience
            )
        )
    }
}
