package com.github.rumoel.givemeslenderite;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Plugin extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getCommand("slenderite").setExecutor(this);
	}

	private final String COMMANDPART1 = "minecraft:give ";
	private final String COMMANDPART2 = " minecraft:phantom_membrane{display:{Name:'{\"text\":\"Слендерит\"}',Lore:['{\"text\":\"Игровая валюта\",\"color\":\"#1111\u200B\"}']}}";

	private final String PERMISSIONEXCEPTION = "Not perm";
	private final int limit = 2304;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {

		if (args.length != 2) {
			return false;
		}

		String nameText = args[0];
		Player target = null;
		int count;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equals(nameText)) {
				target = player;
			}
		}
		if (target == null) {
			return false;
		}
		try {
			count = Integer.parseInt(args[1]);
			if (count > limit) {
				count = limit;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		StringBuilder cmd = new StringBuilder();
		cmd.append(COMMANDPART1).append(target.getName()).append(COMMANDPART2);

		cmd.append(" ").append(count);
		if (command.getName().equalsIgnoreCase("slenderite")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("givemeslenderite.slenderite")) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.toString());
					return true;
				} else {
					sender.sendMessage(PERMISSIONEXCEPTION);
					return false;
				}
			}
			Bukkit.dispatchCommand(sender, cmd.toString());
			return true;
		}
		return false;
	}
}
