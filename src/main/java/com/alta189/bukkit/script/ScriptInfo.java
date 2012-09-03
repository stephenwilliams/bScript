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

import org.mozilla.javascript.Scriptable;

public class ScriptInfo {
	private String name = "Unknown";
	private String description = "Script for Bukkit";
	private String version = "Unknown";
	private String author = "Unknown";
	private String website = "Unknown";

	public ScriptInfo() {
	}

	public ScriptInfo(Scriptable info) {
		if (info != null) {
			Object name = info.get("name", info);
			if (name != null) {
				this.name = name.toString();
			}

			Object description = info.get("description", info);
			if (description != null) {
				this.description = description.toString();
			}

			Object version = info.get("version", info);
			if (version != null) {
				this.version = version.toString();
			}

			Object author = info.get("author", info);
			if (author != null) {
				this.author = author.toString();
			}

			Object website = info.get("name", info);
			if (website != null) {
				this.website = website.toString();
			}
		}
	}

	public String getName() {
		return name;
	}

	public ScriptInfo setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ScriptInfo setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public ScriptInfo setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public ScriptInfo setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public ScriptInfo setWebsite(String website) {
		this.website = website;
		return this;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("ScriptInfo{")
				.append("name='")
				.append(name)
				.append('\'')
				.append(", description='")
				.append(description)
				.append('\'')
				.append(", version='")
				.append(version)
				.append('\'')
				.append(", author='")
				.append(author)
				.append('\'')
				.append(", website='")
				.append(website)
				.append('\'')
				.append('}')
				.toString();
	}
}
