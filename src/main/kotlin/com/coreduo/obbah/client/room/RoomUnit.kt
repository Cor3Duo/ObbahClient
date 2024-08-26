package com.coreduo.obbah.client.room

import com.coreduo.obbah.data.unit.RoomUnitData

open class RoomUnit(data: RoomUnitData) {
    val id: Int
    val username: String
    val motto: String
    val figure: String
    val roomIndex: Int
    val x: Int
    val y: Int
    val z: Float
    val bodyDirection: Int
    val type: Int

    init {
        id = data.id
        username = data.username
        motto = data.custom
        figure = data.figure
        roomIndex = data.roomIndex
        x = data.x
        y = data.y
        z = data.z.toFloat()
        bodyDirection = data.direction
        type = data.type
    }
}