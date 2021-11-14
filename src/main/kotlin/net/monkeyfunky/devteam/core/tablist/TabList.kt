package net.monkeyfunky.devteam.core.tablist

import com.google.common.util.concurrent.ThreadFactoryBuilder
import net.monkeyfunky.devteam.core.Core
import net.monkeyfunky.devteam.core.packets.PacketAPI
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TabList {
    private var executorService: ExecutorService = Executors.newCachedThreadPool(ThreadFactoryBuilder().setNameFormat("Core-TabList").build())

    init {
        object : BukkitRunnable() {
            override fun run() {
            }
        }.runTaskLater(Core.PLUGIN, 1L)
        PacketAPI.add("TabList", TabListPacketListener())
    }

    fun disable() {
        executorService.shutdown()
        PacketAPI.remove("TabList")
    }
}