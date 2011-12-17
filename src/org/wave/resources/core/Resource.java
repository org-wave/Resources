/*
   Copyright 2011 Benedito Barbosa Ribeiro Neto/Christian Linhares Peixoto/Mauricio da Silva Marinho

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.wave.resources.core;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wave.resources.configurations.ResourcesConfiguration;


public abstract class Resource {

	protected String bundleName;

	public Resource(String bundleName) {
		this.bundleName = bundleName;
	}

	public final String getKey() {
		String className = this.getClass().getSimpleName();

		Pattern pattern = Pattern.compile("_");
		Matcher matcher = pattern.matcher(className);

		if (matcher.find()) {
			className = className.substring(matcher.end());
		}
		className = className.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), ".");

		return className.toLowerCase();
	}

	public final String getTemplate() {
		return getTemplate(this.bundleName, this.getKey());
	}

	private static String getTemplate(String bundleName, String key) {
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, ResourcesConfiguration.getLocale());

		return bundle.getString(key);
	}

	public static String getValue(String bundleName, String key, Object... params) {
		String template = Resource.getTemplate(bundleName, key);

		return MessageFormat.format(template, params);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		Resource resource = (Resource) obj;
		if (this.bundleName == null && resource.bundleName != null) {
			return false;
		} else if (!this.bundleName.equals(resource.bundleName))
			return false;

		if (this.getKey() == null && resource.getKey() != null) {
			return false;
		} else if (!this.getKey().equals(resource.getKey()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.bundleName == null) ? 0 : this.bundleName.hashCode());
		result = prime * result + ((this.getKey() == null) ? 0 : this.getKey().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Resource [bundleName=");
		builder.append(this.bundleName);
		builder.append(", key=");
		builder.append(this.getKey());
		builder.append("]");
		return builder.toString();
	}

}
