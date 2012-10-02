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

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import com.alta189.bukkit.script.command.Commands;
import com.alta189.bukkit.script.event.Events;
import com.alta189.bukkit.script.functions.ColorFunction;
import com.alta189.bukkit.script.functions.FormatFunction;
import com.alta189.bukkit.script.functions.PrintFunction;
import com.alta189.bukkit.script.functions.StripColorsFunction;
import com.alta189.bukkit.script.tasks.TaskScriptable;
import com.alta189.bukkit.script.tasks.Tasks;
import com.alta189.bukkit.script.tasks.TimeBuilder;
import com.alta189.bukkit.script.util.FileList;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import org.bukkit.Bukkit;

public class ScriptManager {
	private final File scriptsDirectory;
	private final BScript plugin;
	private final ScriptLoader loader;

	public ScriptManager() {
		scriptsDirectory = new File(BScript.getInstance().getDataFolder(), "scripts");
		if (!scriptsDirectory.exists()) {
			scriptsDirectory.mkdirs();
		}

		plugin = BScript.getInstance();
		loader = new ScriptLoader();
	}

	public void loadScripts() {
		Context context = Context.enter();
		Scriptable mainScope = new ImporterTopLevel(context);
		context.getWrapFactory().setJavaPrimitiveWrap(false);

		// Set up functions
		mainScope.put("color", mainScope, new ColorFunction());
		mainScope.put("stripColors", mainScope, new StripColorsFunction());
		mainScope.put("format", mainScope, new FormatFunction());

		// Set up events
		Object events = Context.javaToJS(new Events(), mainScope);
		mainScope.put("events", mainScope, events);

		// Set up commands
		Object commands = Context.javaToJS(new Commands(), mainScope);
		mainScope.put("commands", mainScope, commands);

		// Set up tasks
		try {
			ScriptableObject.defineClass(mainScope, TimeBuilder.class);
			ScriptableObject.defineClass(mainScope, TaskScriptable.class);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Object tasks = Context.javaToJS(new Tasks(), mainScope);
		mainScope.put("tasks", mainScope, tasks);

		// Set up server
		Object server = Context.javaToJS(Bukkit.getServer(), mainScope);
		mainScope.put("server", mainScope, server);

		// Set up plugins
		Object plugins = Context.javaToJS(Bukkit.getServer().getPluginManager(), mainScope);
		mainScope.put("plugins", mainScope, plugins);

		FileList fileList = new FileList();
		fileList.add(scriptsDirectory, "*.js");

		if (fileList.size() < 1) {
			plugin.info("No scripts found");
			return;
		}

		plugin.info("Found " + fileList.size() + " scripts in script folder");
		for (File file : fileList) {
			String scriptFile = file.getName();
			plugin.info("Loading script '" + scriptFile + "'");
			Scriptable scope = context.newObject(mainScope);
			scope.setPrototype(mainScope);
			scope.setParentScope(null);
			scope.put("print", scope, new PrintFunction(scriptFile));
			try {
				loader.loadScript(context, scope, file);
			} catch (Exception e) {
				plugin.severe("Error loading script '" + scriptFile + "'", e);
			}
		}
	}
}
