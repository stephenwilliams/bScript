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

import java.util.List;

import com.alta189.bukkit.script.BScript;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public class ScriptCommand extends Command implements PluginIdentifiableCommand {
	private ScriptCommandExecutor executor;

	protected ScriptCommand(String name) {
		super(name);
	}

	protected ScriptCommand(String name, String description, String usageMessage, List<String> aliases) {
		super(name, description, usageMessage, aliases);
		if (usageMessage == null) {
			setUsage("<command>");
		}
	}

	@Override
	public boolean execute(CommandSender sender, String s, String[] args) {
		CommandSource source = new CommandSource(sender);
		CommandContext context = new CommandContext(s, args);
		try {
			executor.call(source, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public ScriptCommandExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ScriptCommandExecutor executor) {
		this.executor = executor;
	}

	@Override
	public Plugin getPlugin() {
		return BScript.getInstance();
	}
}
