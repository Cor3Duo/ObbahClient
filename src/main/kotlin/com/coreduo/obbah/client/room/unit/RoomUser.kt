package com.coreduo.obbah.client.room.unit

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.data.unit.RoomUserData

class RoomUser(data: RoomUserData, private val communicator: HabboCommunicator) : RoomUnit(data) {
    var sex: String
    var groupId: Int
    var groupStatus: Int
    var groupName: String
    var swimFigure: String
    var activityPoints: Int
    var isModerator: Boolean

    init {
        sex = data.sex
        groupId = data.groupId
        groupStatus = data.groupStatus
        groupName = data.groupName
        swimFigure = data.swimFigure
        activityPoints = data.activityPoints
        isModerator = data.isModerator
    }

    fun sendFriendInvitation() {
//        communicator.getHabboConnection().sendPacket(FriendInvitationPacket(this))
    }

    fun sendWhisper(message: String) {
//        communicator.getHabboConnection().sendPacket(WhisperPacket(this, message))
    }

    fun kick() {
//        communicator.getHabboConnection().sendPacket(KickPacket(this))
    }

    fun ban() {
//        communicator.getHabboConnection().sendPacket(BanPacket(this))
    }
}