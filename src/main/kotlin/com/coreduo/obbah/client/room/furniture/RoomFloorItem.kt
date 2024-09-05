package com.coreduo.obbah.client.room.furniture

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.data.room.furniture.types.ObjectDataBase

class RoomFloorItem(private val communicator: HabboCommunicator) {
    var id: Int = 0
    var spriteId: Int = 0
    var x: Int = 0
    var y: Int = 0
    var rotation: Int = 0
    var z: Float = 0f
    var stackHeight: Float = 0f
    var extra: Int = 0
    var data: ObjectDataBase? = null
    var state: Int = 0
    var expires: Int = 0
    var usagePolicy: Int = 0
    var userId: Int = 0
    var username: String = ""
    var spriteName: String = ""

    fun pickup() {
//        communicator.getHabboConnection().sendPacket()
    }

    fun move(x: Int, y: Int) {
//        communicator.getHabboConnection().sendPacket()
    }

    fun rotate(rotation: Int) {
//        communicator.getHabboConnection().sendPacket()
    }
}