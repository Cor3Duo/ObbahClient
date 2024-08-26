package com.coreduo.obbah.client.room

import com.coreduo.obbah.data.unit.RoomUserData

class RoomUser(data: RoomUserData) : RoomUnit(data) {
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
}