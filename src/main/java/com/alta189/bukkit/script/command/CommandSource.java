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
package com.alta189.bukkit.script.command;

import com.alta189.commons.util.CastUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandSource {
	private final CommandSender sender;

	public CommandSource(CommandSender sender) {
		this.sender = sender;
	}

	private boolean isPlayer() {
		return sender instanceof Player;
	}

	public Player getPlayer() {
		return CastUtil.safeCast(sender);
	}

	private boolean isConsole() {
		return sender instanceof ConsoleCommandSender;
	}

	public void sendMessage(String message) {
		sender.sendMessage(message);
	}
}
