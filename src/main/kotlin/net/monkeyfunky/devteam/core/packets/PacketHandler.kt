package net.monkeyfunky.devteam.core.packets

import org.bukkit.entity.Player

interface PacketHandler {
    /**
     * On Recieve Packet
     */
    fun read(player: Player?, packet: Any?): Any? { return packet }

    /**
     * On Send Packet
     */
    fun write(player: Player?, packet: Any?): Any? { return packet }
}