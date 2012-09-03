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

import org.mozilla.javascript.ScriptableObject;

public class TaskScriptable extends ScriptableObject {
	private long delay = 1L;
	private long repeatDelay = 0L;
	private boolean async = false;
	private Runnable runnable;

	@Override
	public String getClassName() {
		return "Task";
	}

	public TaskScriptable jsFunction_setDelay(TimeBuilder timeBuilder) {
		this.delay = timeBuilder.getTime();
		return this;
	}

	public TaskScriptable jsFunction_setRepeatDelay(TimeBuilder timeBuilder) {
		this.repeatDelay = timeBuilder.getTime();
		return this;
	}

	public TaskScriptable jsFunction_setAsync(boolean async) {
		this.async = async;
		return this;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getRepeatDelay() {
		return repeatDelay;
	}

	public void setRepeatDelay(long repeatDelay) {
		this.repeatDelay = repeatDelay;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
}
