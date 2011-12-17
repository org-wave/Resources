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
package org.wave.resources.factories;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

import org.wave.resources.annotations.Resource;
import org.wave.resources.utils.SourcesUtil;


public class PropertiesFactory {

	private static final String PARAMETER_PATTERN = "\\{[^\\{\\}]*\\}";

	private PropertiesFactory() {

	}

	public static Properties create(Element enumeration) {
		Properties properties = new Properties();

		List<Element> enumConstants = SourcesUtil.getEnumConstants(enumeration);
		for (Element enumConstant : enumConstants) {
			String key = enumConstant.getSimpleName().toString();
			key = key.replace("_", ".").toLowerCase();

			Resource annotation = enumConstant.getAnnotation(Resource.class);
			String value = annotation.template();

			Pattern pattern = Pattern.compile(PARAMETER_PATTERN);
			Matcher matcher = pattern.matcher(value);

			int i = 0;
			while (matcher.find()) {
				value = value.replace(matcher.group(), "{" + i++ + "}");
			}

			properties.put(key, value);
		}

		return properties;
	}

}
