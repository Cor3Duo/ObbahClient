package com.coreduo.obbah.client

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.plugin.PluginManager
import com.coreduo.obbah.client.room.RoomMessage
import com.coreduo.obbah.connection.HabboNitroConnection
import com.coreduo.obbah.packet.handshake.AuthenticatedPacket
import com.coreduo.obbah.packet.room.unit.UnitChatPacket
import java.io.File
import java.util.concurrent.CountDownLatch

class ObbahClient : HabboCommunicator(HabboNitroConnection("wss://live-arena-d74c8s.habblet.city", "https://www.habblet.city")) {

    private val environment = HabboEnvironment(this)
    private val pluginManager = PluginManager(File("plugins"), environment)

    init {
        pluginManager.loadPlugins()

        getHabboConnection().listenPacket<AuthenticatedPacket> {
            environment.catalog.update()

            pluginManager.getPlugins().forEach {
                it.onAuthenticated()
            }
        }

        getHabboConnection().listenPacket<UnitChatPacket> { packet ->
            val unit = environment.room.getUnitByRoomIndex(packet.roomIndex)

            if (packet.message == ":refresh plugins") {
                pluginManager.unloadPlugins()
                pluginManager.loadPlugins()
            }

            pluginManager.getPlugins().forEach {
                it.onUnitChat(RoomMessage(packet.message, packet.bubbleId, unit))
            }
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