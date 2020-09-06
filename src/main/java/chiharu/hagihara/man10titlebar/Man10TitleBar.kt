package chiharu.hagihara.man10titlebar

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin


class Man10TitleBar : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    var prefix = "§e§l[Man10Title]§f§l"

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name == "mtitle") {
            if (args.isEmpty()) {
                sender.sendMessage("§e==========$prefix§e==========")
                sender.sendMessage("")
                sender.sendMessage("§d/mtitle <main> | <sub> | <time>")
                sender.sendMessage("")
                sender.sendMessage("§e===================================")
                return false
            }
            if (!sender.hasPermission("mtitle.use")) {
                sender.sendMessage("$prefix §c§lYou don't have permission.")
                return false
            }
            var skip = false
            var time = 50
            var main = ""
            var mode = "main"
            var sub = ""
            for (i in args.indices) {
                if (mode == "main") {
                    if (args[i] == "|" && !skip) {
                        mode = "sub"
                        skip = true
                    }
                    main = main + " " + args[i].replace("&", "§").replace("|", "")
                }
                if (mode == "sub") {
                    if (args[i] == "|" && !skip) {
                        mode = "time"
                        skip = true
                    }
                    skip = false
                    sub = sub + " " + args[i].replace("&", "§").replace("|", "")
                }
                if (mode == "time") {
                    if (!skip) {
                        try {
                            time = args[i].toInt() * 20
                        } catch (e: NumberFormatException) {
                        }
                    }
                    skip = false
                }
            }
            for (player in Bukkit.getOnlinePlayers()) {
                player.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 1f, 1f)
                player.sendTitle(main, sub, 0, time, 0)
            }
        }
        return false
    }
}