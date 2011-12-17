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
package org.wave.resources.units;

import java.util.List;

import javax.lang.model.element.Element;

import org.wave.resources.utils.PropertiesUtil;
import org.wave.resources.utils.SourcesUtil;


public class PropertiesUnit implements CompilationUnit {

	@Override
	public void setPackage(Element element, StringBuilder builder) {
		builder.append("package " + SourcesUtil.getPackageName(element) + ";\n\n");
	}

	@Override
	public void setImports(Element element, StringBuilder builder) {
		List<Element> enumConstants = SourcesUtil.getEnumConstants(element);

		for (Element enumConstant : enumConstants) {
			builder.append("import " + SourcesUtil.getQualifiedName(enumConstant) + ";\n");
		}

		if (!enumConstants.isEmpty()) {
			builder.append("\n");
		}
	}

	@Override
	public void setClassName(Element element, StringBuilder builder) {
		builder.append("public class " + SourcesUtil.getSimpleName(element) + " {\n\n");
	}

	@Override
	public void setPrivateConstants(Element element, StringBuilder builder) {
		builder.append("\tprivate static final String BUNDLE_NAME = \"" + PropertiesUtil.getBundleName(element) + "\";\n\n");
	}

	@Override
	public void setPublicConstants(Element element, StringBuilder builder) {
		for (Element enumConstant : SourcesUtil.getEnumConstants(element)) {
			builder.append("\tpublic static final ");
			builder.append(SourcesUtil.getSimpleName(enumConstant));
			builder.append(" ");
			builder.append(enumConstant.getSimpleName().toString());
			builder.append(" = new ");
			builder.append(SourcesUtil.getSimpleName(enumConstant));
			builder.append("(BUNDLE_NAME);\n\n");
		}
	}

	@Override
	public void setConstructors(Element element, StringBuilder builder) {

	}

	@Override
	public void setMethods(Element element, StringBuilder builder) {

	}

}
