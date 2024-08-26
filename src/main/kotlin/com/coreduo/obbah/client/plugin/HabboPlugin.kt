package com.coreduo.obbah.client.plugin

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.room.RoomMessage

open class HabboPlugin() {

    lateinit var communicator: HabboCommunicator

    open fun onEnable() {}
    open fun onDisable() {}
    open fun onUnitChat(message: RoomMessage) {}
}