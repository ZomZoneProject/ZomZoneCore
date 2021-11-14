package net.monkeyfunky.devteam.core.tablist

import com.google.common.util.concurrent.ThreadFactoryBuilder
import net.monkeyfunky.devteam.core.Core
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TabList {
    var executorService: ExecutorService private set
    var tabListManager: TabListManager private set
    private var id: Int

    init {
        executorService = Executors.newCachedThreadPool(ThreadFactoryBuilder().setNameFormat("Core-TabList").build())
        tabListManager = TabListManager()
        object : BukkitRunnable() {
            override fun run() {
                val iterator: Iterator<*> = Bukkit.getOnlinePlayers().iterator()
                while (iterator.hasNext()) {
                    val player = iterator.next() as Player
                    try {
                        tabListManager.createTabList(player)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }.runTaskLater(Core.PLUGIN, 1L)

        val runnable = object : BukkitRunnable() {
            override fun run() {
                for (player: Player in Bukkit.getOnlinePlayers()) {
                    getManager().getTabList(player)?.sendTabList(getManager().builder.header, getManager().builder.footer, getManager().builder.getProfiles(player))
                }
            }
        } as Runnable

        id = Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(Core.PLUGIN, runnable, 0, 5)
    }

    companion object {
        fun getExecutor(): ExecutorService {
            return Core.PLUGIN.getTabList().executorService
        }

        fun getManager(): TabListManager {
            return Core.PLUGIN.getTabList().tabListManager
        }
    }

    fun disable() {
        executorService.shutdown()
        Bukkit.getScheduler().cancelTask(id)
    }
}
