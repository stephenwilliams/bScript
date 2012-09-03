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
package com.alta189.bukkit.script.event;

import com.alta189.bukkit.script.BScript;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

public class Events {
	public Events() {
		EventScanner.scanBukkit();

		for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
			EventScanner.scanPlugin(p);
		}
	}

	public void on(String event, ScriptEventListener listener) {
		if (event == null || event.isEmpty()) {
			throw new IllegalArgumentException("event must be defined");
		}
		if (listener == null) {
			throw new IllegalArgumentException("listener must be defined");
		}

		Class<? extends Event> clazz = EventScanner.getEventClass(event);
		if (clazz == null) {
			throw new IllegalArgumentException("Event '" + event + "' could not be found");
		}

		Bukkit.getPluginManager().registerEvent(clazz, listener, EventPriority.NORMAL, ScriptEventExecutor.getInstance(), BScript.getInstance());
	}
}
