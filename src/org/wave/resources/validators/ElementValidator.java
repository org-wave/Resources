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
package org.wave.resources.validators;

import java.util.HashSet;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.wave.resources.annotations.Resource;
import org.wave.resources.exceptions.ResourcesException;
import org.wave.resources.messages.ErrorMessage;
import org.wave.resources.utils.SourcesUtil;
import org.wave.resources.utils.StringUtil;


public class ElementValidator {

	public void validate(Element element) throws ResourcesException {
		if (!element.getKind().equals(ElementKind.ENUM)) {
			throw new ResourcesException(ErrorMessage.INVALID_ELEMENT_KIND, element.getSimpleName());
		}

		List<Element> enumConstants = SourcesUtil.getEnumConstants(element);
		for (Element enumConstant : enumConstants) {
			Resource annotation = enumConstant.getAnnotation(Resource.class);
			if (annotation == null) {
				throw new ResourcesException(ErrorMessage.INVALID_ELEMENT, element.getSimpleName());
			}

			String template = annotation.template();
			this.validateTemplate(template, enumConstant);
		}
	}

	private void validateTemplate(String template, Element enumConstant) throws ResourcesException {
		String enumConstantName = enumConstant.getSimpleName().toString();

		if (template.isEmpty()) {
			throw new ResourcesException(ErrorMessage.EMPTY_TEMPLATE, template, enumConstantName);
		}

		List<String> params = StringUtil.getParams(template);
		for (String param : params) {
			if (param.isEmpty() || !Character.isJavaIdentifierStart(param.charAt(0))) {
				throw new ResourcesException(ErrorMessage.INVALID_PARAMETER, enumConstantName, param);
			}
		}

		if (params.size() != new HashSet<String>(params).size()) {
			throw new ResourcesException(ErrorMessage.INVALID_TEMPLATE, template, enumConstantName);
		}
	}

}
