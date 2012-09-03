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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alta189.bukkit.script.BScript;
import com.alta189.commons.util.ReflectionUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EventScanner {
	private static final Map<Plugin, Set<Class<? extends Event>>> pluginEvents = new HashMap<Plugin, Set<Class<? extends Event>>>();
	private static final Map<String, Class<? extends Event>> bukkitEvent = new HashMap<String, Class<? extends Event>>();
	private static final Map<String, Class<? extends Event>> events = new HashMap<String, Class<? extends Event>>();
	private static final Map<String, Class<? extends Event>> simpleNameEvents = new HashMap<String, Class<? extends Event>>();
	private static final String LINE_SEPARATOR = SystemUtils.LINE_SEPARATOR;

	public static void scanBukkit() {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.bukkit")))
				.setUrls(ClasspathHelper.forClassLoader(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader())));

		Set<Class<? extends Event>> classes = reflections.getSubTypesOf(Event.class);

		BScript plugin = BScript.getInstance();
		plugin.info("Found " + classes.size() + " classes extending " + Event.class.getCanonicalName() + " in Bukkit");
		for (Class<? extends Event> clazz : classes) {
			if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
				continue;
			}
			bukkitEvent.put(clazz.getSimpleName(), clazz);

			String className = clazz.getCanonicalName();
			if (className == null) {
				className = clazz.getName();
			}
			events.put(className, clazz);
			simpleNameEvents.put(clazz.getSimpleName(), clazz);
			plugin.debug(className);
		}
	}

	public static void removeEvents(Plugin plugin) {
		if (pluginEvents.containsKey(plugin)) {
			Set<Class<? extends Event>> events = pluginEvents.get(plugin);

			for (Class<? extends Event> clazz : events) {
				String className = clazz.getCanonicalName();
				if (className == null) {
					className = clazz.getName();
				}
				EventScanner.events.remove(className);
			}

			pluginEvents.remove(plugin);
		}
	}

	public static void scanPlugin(Plugin plugin) {
		if (pluginEvents.containsKey(plugin)) {
			return;
		}
		BScript.getInstance().debug("Scanning plugin " + plugin.getName());
		if (plugin instanceof JavaPlugin) {
			ClassLoader loader = ReflectionUtil.getFieldValue(JavaPlugin.class, plugin, "classLoader");
			Reflections reflections = new Reflections(new ConfigurationBuilder()
					.filterInputsBy(new FilterBuilder().exclude(FilterBuilder.prefix("org.bukkit")))
					.setUrls(ClasspathHelper.forClassLoader(loader)));

			Set<Class<? extends Event>> classes = reflections.getSubTypesOf(Event.class);

			BScript.getInstance().info("Found " + classes.size() + " classes extending " + Event.class.getCanonicalName() + " in " + plugin.getName());
			for (Class<? extends Event> clazz : classes) {
				if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
					continue;
				}
				BScript.getInstance().debug(clazz.getCanonicalName());

				String className = clazz.getCanonicalName();
				if (className == null) {
					className = clazz.getName();
				}
				BScript.getInstance().debug(className);
				events.put(className, clazz);
				String simpleName = clazz.getSimpleName();
				if (simpleNameEvents.get(simpleName) != null) {
					simpleNameEvents.remove(simpleName);
				} else {
					simpleNameEvents.put(simpleName, clazz);
				}
			}
			if (classes.size() > 0) {
				pluginEvents.put(plugin, classes);
			}
		} else {
			BScript.getInstance().debug("Plugin is not JavaPlugin " + plugin.getName());
		}
	}

	public static Class<? extends Event> getEventClass(String event) {
		if (event.contains(":")) {
			String[] args = event.split(":");
			if (args.length != 2) {
				throw new IllegalArgumentException("'event' can only have one ':' in it");
			}
			return getEventClass(args[0], args[1]);
		} else {
			if (!event.contains(".")) {
				return simpleNameEvents.get(event);
			} else {
				return events.get(event);
			}
		}
	}

	private static Class<? extends Event> getEventClass(String pluginName, String event) {
		Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
		Set<? extends Class<? extends Event>> classes = pluginEvents.get(plugin);
		if (plugin != null) {
			if (event.contains(".")) {
				for (Class<? extends Event> clazz : classes) {
					String className = clazz.getCanonicalName();
					if (className == null) {
						className = clazz.getName();
					}
					if (className.equals(event)) {
						return clazz;
					}
				}
			} else {
				for (Class<? extends Event> clazz : classes) {
					if (clazz.getSimpleName().equals(event)) {
						return clazz;
					}
				}
			}
		}
		return null;
	}

	public static void writeEvents() {
		File file = new File(BScript.getInstance().getDataFolder(), "events.txt");
		if (file.exists()) {
			file.delete();
		}

		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		StringBuilder builder = new StringBuilder();
		builder.append("Bukkit Event Classes")
				.append(LINE_SEPARATOR)
				.append("####################")
				.append(LINE_SEPARATOR)
				.append(LINE_SEPARATOR);

		for (Map.Entry<String, Class<? extends Event>> entry : bukkitEvent.entrySet()) {
			String className = entry.getValue().getCanonicalName();
			if (className == null) {
				className = entry.getValue().getName();
			}
			builder.append("Full Name: ")
					.append(className)
					.append(LINE_SEPARATOR)
					.append("Simple Name: ")
					.append(entry.getKey())
					.append(LINE_SEPARATOR)
					.append(LINE_SEPARATOR);
		}

		builder.append("Plugin Event Classes")
				.append(LINE_SEPARATOR)
				.append("####################")
				.append(LINE_SEPARATOR)
				.append(LINE_SEPARATOR);

		for (Map.Entry<Plugin, Set<Class<? extends Event>>> entry : pluginEvents.entrySet()) {

			builder.append("Plugin: ")
					.append(entry.getKey().getName())
					.append(LINE_SEPARATOR)
					.append("---------------------")
					.append(LINE_SEPARATOR)
					.append(LINE_SEPARATOR);

			for (Class<? extends Event> clazz : entry.getValue()) {
				String className = clazz.getCanonicalName();
				if (className == null) {
					className = clazz.getName();
				}
				String simpleName = clazz.getSimpleName();
				builder.append("Full Name: ")
						.append(entry.getKey().getName())
						.append(":")
						.append(className)
						.append(LINE_SEPARATOR)
						.append("Simple Name: ")
						.append(entry.getKey().getName())
						.append(":")
						.append(simpleName);

				if (simpleNameEvents.get(simpleName) != null) {
					builder.append(LINE_SEPARATOR)
							.append("Simplest Name: ")
							.append(simpleName);
				}
				builder.append(LINE_SEPARATOR)
						.append(LINE_SEPARATOR);
			}
		}
		builder.append(LINE_SEPARATOR);
		try {
			FileUtils.write(file, builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
