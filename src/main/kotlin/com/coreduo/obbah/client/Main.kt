package com.coreduo.obbah.client

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.plugin.PluginManager
import com.coreduo.obbah.connection.HabboNitroConnection
import com.coreduo.obbah.packet.room.access.EnterRoomResponsePacket
import com.coreduo.obbah.packet.room.access.GetRoomEntryDataPacket
import java.io.File
import java.util.concurrent.CountDownLatch

class ObbahClient : HabboCommunicator(HabboNitroConnection("wss://live-arena-d74c8s.habblet.city", "https://www.habblet.city")) {

    private val pluginManager = PluginManager(File("plugins"), this)

    init {
        pluginManager.loadPlugins()

        getHabboConnection().listenPacket<EnterRoomResponsePacket> {
            getHabboConnection().sendPacket(GetRoomEntryDataPacket())
        }

        print("SSO > ")
        sendHandshake(readln())
    }

    fun dispose() {
        pluginManager.unloadPlugins()
    }

}

fun main() {
    val latch = CountDownLatch(1)

    Thread {
        println("Pressione Ctrl+C para encerrar o programa.")
        try {
            latch.await()
        } catch (e: InterruptedException) {
            println("Programa interrompido.")
        }
    }.start()

    val bot = ObbahClient()

    Runtime.getRuntime().addShutdownHook(Thread {
        bot.dispose()
    })
}