package com.coreduo.obbah.client.room

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.data.unit.RoomUserData
import com.coreduo.obbah.packet.room.SendRoomMessagePacket
import com.coreduo.obbah.packet.room.access.GetRoomEntryDataPacket
import com.coreduo.obbah.packet.room.access.RoomEntrySuccessPacket
import com.coreduo.obbah.packet.room.unit.RoomUnitPacket

class Room(private val communicator: HabboCommunicator) {
    private val units: MutableList<RoomUnit> = mutableListOf()
    private var isActive: Boolean = false

    init {
        communicator.getHabboConnection().listenPacket<RoomEntrySuccessPacket> {
            flush()
            isActive = true

            communicator.getHabboConnection().sendPacket(GetRoomEntryDataPacket())
        }

        communicator.getHabboConnection().listenPacket<RoomUnitPacket> {
            for (unit in it.units) {
                if (unit.type == 1) {
                    units.add(RoomUser(unit as RoomUserData))
                }
            }
        }
    }

    fun getUnitByRoomIndex(roomIndex: Int): RoomUnit? {
        return units.find { it.roomIndex == roomIndex }
    }

    fun sendMessage(message: String, bubbleId: Int = 0) {
        if (isActive) {
            communicator.getHabboConnection().sendPacket(SendRoomMessagePacket().apply {
                this.message = message
                styledId = bubbleId
            })
        }
    }

    fun isActive(): Boolean {
        return isActive
    }

    fun flush() {
        isActive = false
        units.clear()
    }
}