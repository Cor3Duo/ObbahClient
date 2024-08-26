package com.coreduo.obbah.client.plugin

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.HabboEnvironment
import com.coreduo.obbah.client.room.RoomMessage
import com.coreduo.obbah.packet.room.access.RoomEntryRequestPacket

open class HabboPlugin() {

    lateinit var environment: HabboEnvironment

    open fun onEnable() {}
    open fun onDisable() {}

    open fun onUnitChat(message: RoomMessage) {}
    open fun onAuthenticated() {}
}