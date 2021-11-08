package net.monkeyfunky.devteam.core

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class Core : JavaPlugin() {
    companion object {
        lateinit var PLUGIN : Core private set
    }

    override fun onEnable() {
        PLUGIN = this
        Bukkit.getServer().pluginManager.registerEvents(EventListener(), this)

        saveDefaultConfig()
        val confFilePath = dataFolder.toString() + File.separator + "config.yml"
        try {
            InputStreamReader(FileInputStream(confFilePath), StandardCharsets.UTF_8).use { reader ->
                val conf: FileConfiguration = YamlConfiguration()
                conf.load(reader)

            }
        } catch (e: Exception) {
            println(e.message)
            onDisable()
        }
    }
}