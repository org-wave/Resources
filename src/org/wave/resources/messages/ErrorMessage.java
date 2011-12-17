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
package org.wave.resources.messages;

import java.text.MessageFormat;

public enum ErrorMessage {

	INVALID_ELEMENT_KIND("The element kind of {0} is invalid."),
	INVALID_ELEMENT("The element {0} is invalid."),
	EMPTY_TEMPLATE("The template {0} from {1} is empty."),
	INVALID_TEMPLATE("The template {0} from {1} is invalid."),
	NULL_STRING("String is null."),
	NULL_DELIMITER("Delimiter is null."),
	UNEXPECTED_EXCEPTION("Unexpected exception: {0}"),
	INVALID_PARAMETER("A parameter from {0} is invalid: {1}.");

	private String value;

	private ErrorMessage(String value) {
		this.value = value;
	}

	public String getMessage(Object... params) {
		return MessageFormat.format(this.value, params);
	}

}
