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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class BukkitScriptListener implements Listener {
	@EventHandler
	public void onPluginEnable(PluginEnableEvent event) {
		EventScanner.scanPlugin(event.getPlugin());
	}
}
