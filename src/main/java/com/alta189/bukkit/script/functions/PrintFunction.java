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

import com.alta189.bukkit.script.BScript;

import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class PrintFunction extends BaseFunction {
	private final String tag;

	public PrintFunction(String script) {
		super();
		this.tag = "[" + script + "] ";
	}

	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		throw new IllegalArgumentException("Cannot invoke format() as a constructor");
	}

	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (args.length < 1) {
			return null;
		}

		String str = StringUtils.join(args, " ");
		BScript.getInstance().info(tag + str);

		return super.call(cx, scope, thisObj, args);
	}
}
