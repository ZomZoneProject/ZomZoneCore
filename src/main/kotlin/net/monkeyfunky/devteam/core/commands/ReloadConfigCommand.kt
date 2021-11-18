package net.monkeyfunky.devteam.core.commands

import net.monkeyfunky.devteam.core.Core
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ReloadConfigCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        Core.PLUGIN.reloadConfig()
        sender.sendMessage("${ChatColor.GREEN}[Core] config has been reloaded")
        return true
    }
}