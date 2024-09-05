package com.coreduo.obbah.client.room

import com.coreduo.obbah.client.room.unit.RoomUnit

data class RoomMessage(val message: String, val bubbleId: Int, val unit: RoomUnit?) {
}