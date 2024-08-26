package com.coreduo.obbah.client

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.room.Room
import com.coreduo.obbah.packet.room.access.RoomEntryRequestPacket

class HabboEnvironment(private val communicator: HabboCommunicator) {
    var room = Room(communicator)

    fun enterRoom(roomId: Int, password: String = "") {
        communicator.getHabboConnection().sendPacket(RoomEntryRequestPacket().apply {
            this.roomId = roomId
            this.password = password
        })
    }
}