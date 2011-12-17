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
package org.wave.resources.processors;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.wave.resources.annotations.Properties;
import org.wave.resources.exceptions.ResourcesException;
import org.wave.resources.generators.FileGenerator;
import org.wave.resources.validators.ElementValidator;


@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("br.com.brasilti.resources.annotations.Properties")
public class ResourcesProcessor extends AbstractProcessor {

	private ElementValidator validator;

	private FileGenerator generator;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
		Filer filer = this.processingEnv.getFiler();

		Messager messager = this.processingEnv.getMessager();

		this.validator = new ElementValidator();

		this.generator = new FileGenerator(filer);

		Set<? extends Element> elements = environment.getElementsAnnotatedWith(Properties.class);
		for (Element element : elements) {
			try {
				this.validator.validate(element);
			} catch (ResourcesException e) {
				messager.printMessage(Kind.ERROR, e.getMessage(), element);
			}

			Properties annotation = element.getAnnotation(Properties.class);
			if (annotation.create()) {
				try {
					this.generator.generateProperties(element);
				} catch (ResourcesException e) {
					messager.printMessage(Kind.ERROR, e.getMessage(), element);
				}
			}

			try {
				this.generator.generateSources(element);
			} catch (ResourcesException e) {
				messager.printMessage(Kind.ERROR, e.getMessage(), element);
			}
		}

		return true;
	}

}
