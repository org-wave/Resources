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

import org.wave.resources.annotations.Resource;
import org.wave.resources.utils.SourcesUtil;
import org.wave.resources.utils.StringUtil;


public class ResourceUnit implements CompilationUnit {

	@Override
	public void setPackage(Element element, StringBuilder builder) {
		builder.append("package " + SourcesUtil.getPackageName(element) + ";\n\n");
	}

	@Override
	public void setImports(Element element, StringBuilder builder) {
		builder.append("import br.com.brasilti.resources.core.Resource;\n\n");
	}

	@Override
	public void setClassName(Element element, StringBuilder builder) {
		builder.append("public class " + SourcesUtil.getSimpleName(element) + " extends Resource {\n\n");
	}

	@Override
	public void setPrivateConstants(Element element, StringBuilder builder) {

	}

	@Override
	public void setPublicConstants(Element element, StringBuilder builder) {

	}

	@Override
	public void setConstructors(Element element, StringBuilder builder) {
		builder.append("\tpublic " + SourcesUtil.getSimpleName(element) + "(String bundleName) {\n");
		builder.append("\t\tsuper(bundleName);\n");
		builder.append("\t}\n\n");
	}

	@Override
	public void setMethods(Element element, StringBuilder builder) {
		Resource annotation = element.getAnnotation(Resource.class);
		String template = annotation.template();

		List<String> params = StringUtil.getParams(template);

		this.appendMethodDoc(template, params, builder);

		this.appendMethodDeclaration(params, builder);

		this.appendMethodReturn(params, builder);

		builder.append("\t}\n\n");
	}

	private void appendMethodDoc(String template, List<String> params, StringBuilder builder) {
		builder.append("\t/**\n");
		builder.append("\t* Template: " + template + "\n");
		builder.append("\t* \n");

		for (String param : params) {
			builder.append("\t* @param " + param + "\n");
		}

		builder.append("\t* @return MessageFormat.format(template");

		for (String param : params) {
			builder.append(", " + param);
		}

		builder.append(");\n");

		builder.append("\t*/\n");
	}

	private void appendMethodDeclaration(List<String> params, StringBuilder builder) {
		builder.append("\tpublic String getValue(");

		for (int i = 0; i < params.size(); i++) {
			if (i != 0) {
				builder.append(", ");
			}

			builder.append("String " + params.get(i));
		}

		builder.append(") {\n");
	}

	private void appendMethodReturn(List<String> params, StringBuilder builder) {
		builder.append("\t\treturn Resource.getValue(this.bundleName, this.getKey()");

		for (String param : params) {
			builder.append(", " + param);
		}

		builder.append(");\n");
	}

}
