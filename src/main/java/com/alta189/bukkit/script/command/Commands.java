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

import java.util.Arrays;

import com.alta189.bukkit.script.BScript;
import com.alta189.commons.util.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

public class Commands {
	private static final CommandMap commandMap;

	static {
		commandMap = ReflectionUtil.getFieldValue(SimplePluginManager.class, Bukkit.getServer().getPluginManager(), "commandMap");
		if (commandMap == null) {
			throw new NullPointerException(Commands.class.getCanonicalName() + ".commandMap was unable to be set using reflection!");
		}
	}

	public void register(String command, ScriptCommandExecutor executor) {
		register(command, null, executor);
	}

	public void register(String command, String[] aliases, ScriptCommandExecutor executor) {
		ScriptCommand cmd = new ScriptCommand(command);
		cmd.setExecutor(executor);

		if (aliases != null && aliases.length > 0) {
			cmd.setAliases(Arrays.asList(aliases));
		}
		commandMap.register(BScript.getInstance().getName(), cmd);
	}
}
