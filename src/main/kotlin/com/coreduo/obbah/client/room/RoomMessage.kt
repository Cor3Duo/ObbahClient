package com.coreduo.obbah.client.room

data class RoomMessage(val message: String, val bubbleId: Int, val unit: RoomUnit?) {
}