package net.monkeyfunky.devteam.core.packets

import net.monkeyfunky.devteam.core.Core

class PacketAPI {
    private val packetHandlers: HashMap<String, PacketHandler> = hashMapOf<String, PacketHandler>()
    companion object PIPELINE {
        fun add(name: String, handler: PacketHandler) {
            Core.PLUGIN.getPacketAPI().packetHandlers[name] = handler
        }

        fun remove(name: String) {
            Core.PLUGIN.getPacketAPI().packetHandlers.remove(name)
        }
    }

    fun getHandlers(): Set<PacketHandler> {
        return packetHandlers.values.toSet()
    }
}