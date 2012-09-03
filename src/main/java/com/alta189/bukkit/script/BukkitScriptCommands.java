/*
 * This file is part of bScript.
 *
 * Copyright (c) 2012, alta189 <http://alta189.com/>
 * bScript is licensed under the GPL.
 *
 * bScript is free software: you can redistribute it and/or modify
 * it under the terms of the GPL as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * bScript is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GPL for more details.
 *
 * You should have received a copy of the GPL
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.bukkit.script;

import com.alta189.bukkit.script.event.EventScanner;
import com.alta189.bukkitplug.command.Command;
import com.alta189.bukkitplug.command.CommandContext;
import com.alta189.bukkitplug.command.Description;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class BukkitScriptCommands {
	@Command
	@Description("Writes possible events to a file")
	public void writeEvents(CommandSender sender, CommandContext context) {
		if (!(sender instanceof ConsoleCommandSender)) {
			sender.sendMessage("You can only execute this command via the console");
			return;
		}

		EventScanner.writeEvents();
	}
}
