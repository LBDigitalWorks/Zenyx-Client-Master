package com.client.draw.login.worlds

data class WorldData(
    val ip: String,
    val name: String,
    val type: WorldType,
    val spriteId: Int,
    val description: String = "-"
)