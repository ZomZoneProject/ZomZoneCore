package net.monkeyfunky.devteam.core.commands

import net.monkeyfunky.devteam.core.Core
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class DebugCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        Core.DEBUG = !Core.DEBUG
        sender.sendMessage("${ChatColor.YELLOW}DEBUG: ${Core.DEBUG}")
        return true
    }
}