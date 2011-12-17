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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.wave.resources.messages.ErrorMessage;


public class SourcesUtil {

	private SourcesUtil() {

	}

	public static String getPackageName(Element element) {
		if (element.getKind().equals(ElementKind.ENUM)) {
			Name simpleName = element.getSimpleName();
			String qualifiedName = ((TypeElement) element).getQualifiedName().toString();

			return qualifiedName.replace("." + simpleName, "");
		} else {
			return SourcesUtil.getPackageName(element.getEnclosingElement()) + ".resources";
		}
	}

	public static String getSimpleName(Element element) {
		if (element.getKind().equals(ElementKind.ENUM)) {
			return element.getSimpleName() + "_";
		} else {
			String enumConstantName = element.getSimpleName().toString();
			enumConstantName = StringUtil.capitalize(enumConstantName.toLowerCase(), "_");
			enumConstantName = enumConstantName.replace("_", "");

			return SourcesUtil.getSimpleName(element.getEnclosingElement()) + enumConstantName;
		}
	}

	public static String getQualifiedName(Element element) {
		if (element.getKind().equals(ElementKind.ENUM)) {
			return ((TypeElement) element).getQualifiedName() + "_";
		} else {
			return SourcesUtil.getPackageName(element) + "." + SourcesUtil.getSimpleName(element);
		}
	}

	public static List<Element> getEnumConstants(Element element) {
		if (!element.getKind().equals(ElementKind.ENUM)) {
			throw new IllegalArgumentException(ErrorMessage.INVALID_ELEMENT_KIND.getMessage(element.getSimpleName()));
		}

		List<Element> enumConstants = new ArrayList<Element>();

		List<? extends Element> enclosedElements = element.getEnclosedElements();
		for (Element enclosedElement : enclosedElements) {
			if (enclosedElement.getKind().equals(ElementKind.ENUM_CONSTANT)) {
				enumConstants.add(enclosedElement);
			}
		}

		return enumConstants;
	}

}
