package net.monkeyfunky.devteam.core.packets

import org.bukkit.entity.Player

interface PacketHandler {
    fun read(player: Player?, packet: Any?): Any? { return packet }
    fun write(player: Player?, packet: Any?): Any? { return packet }
}