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
package org.wave.resources.utils;

import javax.lang.model.element.Element;

import org.wave.resources.annotations.Properties;


public class PropertiesUtil {

	private PropertiesUtil() {

	}

	public static String getPackageName(Element element) {
		Properties annotation = element.getAnnotation(Properties.class);
		String bundleName = annotation.bundleName();

		if (bundleName.isEmpty()) {
			return SourcesUtil.getPackageName(element) + ".properties";
		}

		return bundleName.substring(0, bundleName.lastIndexOf("."));
	}

	public static String getPropertiesName(Element element) {
		Properties annotation = element.getAnnotation(Properties.class);
		String bundleName = annotation.bundleName();

		final String extension = ".properties";

		if (bundleName.isEmpty()) {
			return element.getSimpleName() + extension;
		}

		return bundleName.substring(bundleName.lastIndexOf(".") + 1) + extension;
	}

	public static String getBundleName(Element element) {
		Properties annotation = element.getAnnotation(Properties.class);
		String bundleName = annotation.bundleName();

		if (bundleName.isEmpty()) {
			return PropertiesUtil.getPackageName(element) + "." + element.getSimpleName();
		}

		return bundleName;
	}

}
