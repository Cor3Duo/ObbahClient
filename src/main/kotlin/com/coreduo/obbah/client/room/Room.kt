package com.coreduo.obbah.client.room

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.room.furniture.RoomFloorItem
import com.coreduo.obbah.client.room.unit.RoomUnit
import com.coreduo.obbah.client.room.unit.RoomUser
import com.coreduo.obbah.data.unit.RoomUserData
import com.coreduo.obbah.packet.room.SendRoomMessagePacket
import com.coreduo.obbah.packet.room.access.GetRoomEntryDataPacket
import com.coreduo.obbah.packet.room.access.RoomEntrySuccessPacket
import com.coreduo.obbah.packet.room.furniture.FurnitureFloorPacket
import com.coreduo.obbah.packet.room.unit.RoomUnitPacket

class Room(private val communicator: HabboCommunicator) {
    private val units: MutableList<RoomUnit> = mutableListOf()
    private val furnis: MutableList<RoomFloorItem> = mutableListOf()
    private var isActive: Boolean = false

    init {
        communicator.getHabboConnection().listenPacket<RoomEntrySuccessPacket> {
            flush()
            isActive = true

            communicator.getHabboConnection().sendPacket(GetRoomEntryDataPacket())
        }

        communicator.getHabboConnection().listenPacket<FurnitureFloorPacket> {
            for (item in it.items) {
                val floorItem = RoomFloorItem(communicator)
                floorItem.id = item.id
                floorItem.spriteId = item.spriteId
                floorItem.x = item.x
                floorItem.y = item.y
                floorItem.rotation = item.rotation
                floorItem.z = item.z
                floorItem.stackHeight = item.stackHeight
                floorItem.extra = item.extra
                floorItem.data = item.data
                floorItem.state = item.state
                floorItem.expires = item.expires
                floorItem.usagePolicy = item.usagePolicy
                floorItem.userId = item.userId
                floorItem.username = it.owners[item.userId]!!
                floorItem.spriteName = item.spriteName
            }
        }

        communicator.getHabboConnection().listenPacket<RoomUnitPacket> {
            for (unit in it.units) {
                if (unit.type == 1) {
                    units.add(RoomUser(unit as RoomUserData, communicator))
                }
            }
        }
    }

    fun getUnitByRoomIndex(roomIndex: Int): RoomUnit? {
        return units.find { it.roomIndex == roomIndex }
    }

    fun getUserByRoomIndex(roomIndex: Int): RoomUser? {
        return units.filterIsInstance<RoomUser>().find { it.roomIndex == roomIndex }
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