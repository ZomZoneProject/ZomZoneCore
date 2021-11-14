package net.monkeyfunky.devteam.core.tablist

import net.monkeyfunky.devteam.core.packets.PacketHandler
import org.bukkit.entity.Player

class TabListPacketListener : PacketHandler {
    override fun write(player: Player?, packet: Any?): Any? {
        if (packet?.javaClass!!.name.contains("PacketPlayOutPlayerInfo")) {

        }
        return super.write(player, packet)
    }
}