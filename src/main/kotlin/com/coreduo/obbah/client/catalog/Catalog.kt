package com.coreduo.obbah.client.catalog

import com.coreduo.obbah.HabboCommunicator
import com.coreduo.obbah.data.catalog.CatalogNodeData
import com.coreduo.obbah.packet.catalog.CatalogIndexRequestPacket
import com.coreduo.obbah.packet.catalog.CatalogIndexResponsePacket
import com.coreduo.obbah.packet.catalog.CatalogPurchaseGiftPacket

class Catalog(private val communicator: HabboCommunicator) {
    private var root: CatalogNodeData? = null

    init {
        communicator.getHabboConnection().listenPacket<CatalogIndexResponsePacket> {
            root = it.root
        }
    }

    fun update() {
        communicator.getHabboConnection().sendPacket(CatalogIndexRequestPacket().apply {
            mode = "NORMAL"
        })
    }

    fun purchaseGift(itemId: Int, pageId: Int, receivingName: String, spriteId: Int, giftMessage: String = "", showMyFace: Boolean = true, ribbonId: Int = 0, boxId: Int = 0, extraData: String = "") {
        communicator.getHabboConnection().sendPacket(CatalogPurchaseGiftPacket().apply {
            this.boxId = boxId
            this.extraData = extraData
            this.giftMessage = giftMessage
            this.itemId = itemId
            this.pageId = pageId
            this.receivingName = receivingName
            this.spriteId = spriteId
            this.showMyFace = showMyFace
            this.ribbonId = ribbonId
        })
    }

}