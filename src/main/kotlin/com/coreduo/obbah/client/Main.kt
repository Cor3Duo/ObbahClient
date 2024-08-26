package com.coreduo.obbah.client

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.client.plugin.PluginManager
import com.coreduo.obbah.client.room.Room
import com.coreduo.obbah.client.room.RoomMessage
import com.coreduo.obbah.client.room.RoomUser
import com.coreduo.obbah.connection.HabboNitroConnection
import com.coreduo.obbah.data.unit.RoomUserData
import com.coreduo.obbah.packet.room.access.GetRoomEntryDataPacket
import com.coreduo.obbah.packet.room.access.RoomEntrySuccessPacket
import com.coreduo.obbah.packet.room.unit.RoomUnitPacket
import com.coreduo.obbah.packet.room.unit.UnitChatPacket
import java.io.File
import java.util.concurrent.CountDownLatch

class ObbahClient : HabboCommunicator(HabboNitroConnection("wss://live-arena-d74c8s.habblet.city", "https://www.habblet.city")) {

    private val pluginManager = PluginManager(File("plugins"), this)
    var room = Room()

    init {
        pluginManager.loadPlugins()

        getHabboConnection().listenPacket<RoomEntrySuccessPacket> {
            getHabboConnection().sendPacket(GetRoomEntryDataPacket())
        }

        getHabboConnection().listenPacket<RoomUnitPacket> {
            for (unit in it.units) {
                if (unit.type == 1) {
                    room.units.add(RoomUser(unit as RoomUserData))
                }
            }
        }

        getHabboConnection().listenPacket<UnitChatPacket> { packet ->
            val unit = room.getUnitByRoomIndex(packet.roomIndex)

            pluginManager.getPlugins().forEach({
                it.onUnitChat(RoomMessage(packet.message, packet.bubbleId, unit))
            })
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