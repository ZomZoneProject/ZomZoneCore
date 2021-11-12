package net.monkeyfunky.devteam.core.events

import io.netty.channel.*
import net.monkeyfunky.devteam.core.Core
import net.monkeyfunky.devteam.core.packets.PacketHandler
import net.monkeyfunky.devteam.core.utils.NMSUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.lang.reflect.Field


class PacketListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val channelDuplexHandler: ChannelDuplexHandler = object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext?, msg: Any) {
                var packet: Any? = msg
                for (handler: PacketHandler in Core.PLUGIN.getPacketAPI().getHandlers()) {
                    packet = handler.read(player, packet)
                }
                super.channelRead(ctx, packet)
            }

            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                var packet: Any? = msg
                for (handler: PacketHandler in Core.PLUGIN.getPacketAPI().getHandlers()) {
                    packet = handler.write(player, packet)
                }
                super.write(ctx, packet, promise)
            }
        }
        try {
            val playerClass = Class.forName("org.bukkit.craftbukkit." + NMSUtils.serverVersion + ".entity.CraftPlayer")
            val craftPlayer = playerClass.cast(player)
            val handle = playerClass.getMethod("getHandle").invoke(craftPlayer)
            val playerConnectionField: Field = handle.javaClass.getDeclaredField("playerConnection")
            val playerConnection: Any = playerConnectionField.get(handle)
            val networkManagerField: Field = playerConnection.javaClass.getDeclaredField("networkManager")
            val networkManager: Any = networkManagerField.get(playerConnection)
            val channelField: Field = networkManager.javaClass.getField("channel")
            val channel: Channel = channelField.get(networkManager) as Channel
            val pipeline: ChannelPipeline = channel.pipeline()
            pipeline.addBefore("packet_handler", player.name + ".CORE", channelDuplexHandler)
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        try {
            val playerClass = Class.forName("org.bukkit.craftbukkit." + NMSUtils.serverVersion + ".entity.CraftPlayer")
            val craftPlayer = playerClass.cast(player)
            val handle = playerClass.getMethod("getHandle").invoke(craftPlayer)
            val playerConnectionField: Field = handle.javaClass.getDeclaredField("playerConnection")
            val playerConnection: Any = playerConnectionField.get(handle)
            val networkManagerField: Field = playerConnection.javaClass.getDeclaredField("networkManager")
            val networkManager: Any = networkManagerField.get(playerConnection)
            val channelField: Field = networkManager.javaClass.getField("channel")
            val channel: Channel = channelField.get(networkManager) as Channel
            channel.eventLoop().submit { channel.pipeline().remove(event.player.name + ".CORE") }
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
        }
    }
}