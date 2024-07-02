package com.coreduo.obbah.client.plugin

import com.coreduo.obbah.HabboCommunicator

abstract class HabboPlugin() {

    lateinit var communicator: HabboCommunicator

    abstract fun onEnable()
    abstract fun onDisable()
}