package com.coreduo.obbah.client.room

class Room {
    val units: MutableList<RoomUnit> = mutableListOf()

    fun getUnitByRoomIndex(roomIndex: Int): RoomUnit? {
        return units.find { it.roomIndex == roomIndex }
    }

    fun flush() {
    }
}