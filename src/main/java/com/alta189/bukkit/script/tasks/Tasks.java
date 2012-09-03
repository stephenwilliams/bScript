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
package com.alta189.bukkit.script.tasks;

import com.alta189.bukkit.script.BScript;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Tasks {
	public int schedule(TaskScriptable task, Runnable runnable) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		BScript plugin = BScript.getInstance();
		if (task.isAsync()) {
			if (task.getRepeatDelay() > 0L) {
				return scheduler.scheduleAsyncRepeatingTask(plugin, runnable, task.getDelay(), task.getRepeatDelay());
			} else {
				return scheduler.scheduleAsyncDelayedTask(plugin, runnable, task.getDelay());
			}
		} else {
			if (task.getRepeatDelay() > 0L) {
				return scheduler.scheduleSyncRepeatingTask(plugin, runnable, task.getDelay(), task.getRepeatDelay());
			} else {
				return scheduler.scheduleSyncDelayedTask(plugin, runnable, task.getDelay());
			}
		}
	}
}
