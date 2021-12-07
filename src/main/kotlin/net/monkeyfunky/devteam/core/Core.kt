package net.monkeyfunky.devteam.core

import net.monkeyfunky.devteam.core.commands.DebugCommand
import net.monkeyfunky.devteam.core.commands.ReloadConfigCommand
import net.monkeyfunky.devteam.core.commands.ToGiveCommand
import net.monkeyfunky.devteam.core.debug.DebugPacketListener
import net.monkeyfunky.devteam.core.events.LogInOutListener
import net.monkeyfunky.devteam.core.events.PacketListener
import net.monkeyfunky.devteam.core.events.TabListListener
import net.monkeyfunky.devteam.core.packets.PacketAPI
import net.monkeyfunky.devteam.core.tablist.TabList
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.properties.Delegates

/**
 * ZomZoneCore Plugin
 *
 * @author eight_y_88
 *
 */

class Core : JavaPlugin() {
    companion object {
        /**
         * Plugin instance
         */
        lateinit var PLUGIN : Core private set

        /**
         * Debug flag
         */
        var DEBUG by Delegates.notNull<Boolean>()

        /**
         * Log to console
         */
        fun log(vararg strings: String) {
            strings.forEach { PLUGIN.logger.info(it) }
        }
    }

    /**
     * PacketAPI instance
     */
    private lateinit var packetAPI: PacketAPI

    /**
     * TabList instance
     */
    private lateinit var tabList: TabList

    override fun onEnable() {
        PLUGIN = this
        DEBUG = false

        registerEvents(
            EventListener(),

            PacketListener(),
            TabListListener(),
            LogInOutListener()
        )

        getCommand("reloadcore")?.setExecutor(ReloadConfigCommand())
        getCommand("debugcore")?.setExecutor(DebugCommand())
        getCommand("togive")?.setExecutor(ToGiveCommand())

        packetAPI = PacketAPI()

        tabList = TabList()

        PacketAPI.add("DEBUG", DebugPacketListener())

        saveDefaultConfig()
        loadConfig()
    }

    override fun onDisable() {
        PacketAPI.removeAll()
        tabList.disable()
    }

    /**
     * Reload Config
     */
    override fun reloadConfig() {
        super.reloadConfig()
        loadConfig()
    }

    private fun loadConfig() {
        val configFilePath = dataFolder.toString() + File.separator + "config.yml"
        try {
            InputStreamReader(FileInputStream(configFilePath), StandardCharsets.UTF_8).use { reader ->
                val config: FileConfiguration = YamlConfiguration()
                config.load(reader)

                LogInOutListener.FIRST_JOIN_MESSAGE = config.getString("message.join.first").toString()
                LogInOutListener.NORMAL_JOIN_MESSAGE = config.getString("message.join.normal").toString()
                LogInOutListener.QUIT_MESSAGE = config.getString("message.left").toString()
            }
        } catch (e: Exception) {
            println(e.message)
            onDisable()
        }
    }

    /**
     * Returns PacketAPI
     */
    fun getPacketAPI(): PacketAPI {
        return packetAPI
    }

    /**
     * Returns TabList
     */
    fun getTabList(): TabList {
        return tabList
    }

    private fun registerEvents(vararg listeners: Listener) {
        listeners.forEach { Bukkit.getPluginManager().registerEvents(it, this) }
    }
}