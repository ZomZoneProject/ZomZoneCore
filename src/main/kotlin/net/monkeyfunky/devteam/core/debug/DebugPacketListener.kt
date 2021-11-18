package net.monkeyfunky.devteam.core.debug

import net.monkeyfunky.devteam.core.Core
import net.monkeyfunky.devteam.core.packets.PacketHandler
import org.bukkit.entity.Player

class DebugPacketListener : PacketHandler {
    override fun write(player: Player?, packet: Any?): Any? {
        if (Core.DEBUG) {
            if (packet?.javaClass!!.name.contains("PacketPlayOutPlayerInfo")) {
                Core.PLUGIN.logger.info(packet.toString())
            }
        }
        return super.write(player, packet)
    }
}