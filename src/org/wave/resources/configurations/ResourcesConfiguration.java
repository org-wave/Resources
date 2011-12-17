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
package org.wave.resources.configurations;

import java.util.Locale;

public class ResourcesConfiguration {

	private static Locale locale = Locale.getDefault();

	private ResourcesConfiguration() {

	}

	public static Locale getLocale() {
		return locale;
	}

	public static void setLocale(Locale locale) {
		ResourcesConfiguration.locale = locale;
	}

}
