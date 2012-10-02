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

import com.alta189.commons.util.CastUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import org.bukkit.Bukkit;

public class ScriptLoader {
	public Script loadScript(Context context, Scriptable scope, File file) throws Exception {
		String contents = FileUtils.readFileToString(file);
		String name = FilenameUtils.getBaseName(file.getName());

		context.evaluateString(scope, "importPackage(\"org.bukkit\");", name, 1, null);

		if (Bukkit.getPluginManager().getPlugin("Spout") != null) {
			context.evaluateString(scope, "importPackage(\"org.getspout.spoutapi\"", name, 1, null);
		}

		context.evaluateString(scope, "var info = {};", name, 1, null);
		context.evaluateString(scope, contents, name, 1, null);

		Scriptable infoObject = CastUtil.safeCast(ScriptableObject.getProperty(scope, "info"));
		ScriptInfo info = new ScriptInfo(infoObject);
		if (info.getName() != null) {
			info.setName(name);
		}

		return new Script(file, info);
	}
}
