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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wave.resources.messages.ErrorMessage;


public class StringUtil {

	private static final String PARAMETER_PATTERN = "\\{[^\\{\\}]*\\}";

	private StringUtil() {

	}

	public static String capitalize(String value) {
		return StringUtil.capitalize(value, "");
	}

	public static String capitalize(String value, String delimiter) {
		if (value == null) {
			throw new IllegalArgumentException(ErrorMessage.NULL_STRING.getMessage());
		}

		if (delimiter == null) {
			throw new IllegalArgumentException(ErrorMessage.NULL_DELIMITER.getMessage());
		}

		StringBuilder builder = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(value, delimiter, true);

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			token = String.format("%s%s", Character.toUpperCase(token.charAt(0)), token.substring(1));
			builder.append(token);
		}

		return builder.toString();
	}

	public static List<String> getParams(String value) {
		if (value == null) {
			throw new IllegalArgumentException(ErrorMessage.NULL_STRING.getMessage());
		}

		List<String> params = new ArrayList<String>();

		Pattern pattern = Pattern.compile(PARAMETER_PATTERN);
		Matcher matcher = pattern.matcher(value);

		while (matcher.find()) {
			params.add(matcher.group().replace("{", "").replace("}", "").toLowerCase());
		}

		return params;
	}

}
