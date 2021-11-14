package net.monkeyfunky.devteam.core.packets

import net.monkeyfunky.devteam.core.Core
import net.monkeyfunky.devteam.core.utils.NMSUtils
import org.bukkit.entity.Player
import java.lang.reflect.Field

class PacketAPI {
    private val packetHandlers: HashMap<String, PacketHandler> = hashMapOf()
    companion object PIPELINE {
        fun add(name: String, handler: PacketHandler) {
            Core.PLUGIN.getPacketAPI().packetHandlers[name] = handler
        }

        fun remove(name: String) {
            Core.PLUGIN.getPacketAPI().packetHandlers.remove(name)
        }

        fun send(player: Player?, packet: Any?) {
            try {
                val entityPlayer: Any = player?.javaClass!!.getDeclaredMethod("getHandle").invoke(player)
                val playerConnection: Field = Class.forName("net.minecraft.server." + NMSUtils.serverVersion + ".EntityPlayer").getDeclaredField("playerConnection")
                val sendPacket = Class.forName("net.minecraft.server." + NMSUtils.serverVersion + ".PlayerConnection").getDeclaredMethod("sendPacket", Class.forName("net.minecraft.server." + NMSUtils.serverVersion + ".Packet"))
                val playerConnectionObject: Any = playerConnection.get(entityPlayer)
                sendPacket.invoke(playerConnectionObject, packet)
            } catch (e: ReflectiveOperationException) {
                e.printStackTrace()
            }
        }
    }

    fun getHandlers(): Set<PacketHandler> {
        return packetHandlers.values.toSet()
    }
}