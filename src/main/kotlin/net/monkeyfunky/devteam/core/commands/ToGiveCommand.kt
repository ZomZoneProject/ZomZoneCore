package net.monkeyfunky.devteam.core.commands

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import net.monkeyfunky.devteam.core.utils.NBTUtils
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ToGiveCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.inventory.itemInMainHand.type == Material.AIR) return false

            val message = TextComponent("${ ChatColor.AQUA }[クリックでコピーする]")

            message.clickEvent = ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, NBTUtils.toGiveCommand(sender.inventory.itemInMainHand))

            sender.sendMessage(NBTUtils.toGiveCommand(sender.inventory.itemInMainHand))
            sender.spigot().sendMessage(ChatMessageType.CHAT, message)
        }
        return false
    }

}