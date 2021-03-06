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
package com.alta189.bukkit.script.functions;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import org.bukkit.ChatColor;

public class StripColorsFunction extends BaseFunction {
	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		throw new IllegalArgumentException("Cannot invoke stripColor() as a constructor");
	}

	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (args.length < 1) {
			return null;
		}

		String result = ChatColor.stripColor(args[0].toString());
		if (result != null) {
			return Context.javaToJS(result, scope);
		}

		return super.call(cx, scope, thisObj, args);
	}
}
