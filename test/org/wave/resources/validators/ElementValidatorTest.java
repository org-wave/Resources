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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wave.resources.annotations.Resource;
import org.wave.resources.exceptions.ResourcesException;
import org.wave.resources.messages.ErrorMessage;
import org.wave.resources.validators.ElementValidator;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class ElementValidatorTest {

	@Mock
	private Element enumConstant;

	@Mock
	private Element element;

	private ElementValidator validator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("Template");

		Name enumConstantSimpleName = mock(Name.class);
		when(enumConstantSimpleName.toString()).thenReturn("ENUM_CONSTANT");

		when(this.enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);
		when(this.enumConstant.getSimpleName()).thenReturn(enumConstantSimpleName);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		List enclosedElements = new ArrayList();
		enclosedElements.add(this.enumConstant);
		enclosedElements.add(field);

		Name simpleName = mock(Name.class);
		when(simpleName.toString()).thenReturn("MappedEnum");

		when(this.element.getKind()).thenReturn(ElementKind.ENUM);
		when(this.element.getSimpleName()).thenReturn(simpleName);
		when(this.element.getEnclosedElements()).thenReturn(enclosedElements);

		this.validator = new ElementValidator();
	}

	@Test(expected = ResourcesException.class)
	public void deveLancarExcecaoQuandoOElementoNaoForUmaEnumException() throws ResourcesException {
		when(this.element.getKind()).thenReturn(ElementKind.CLASS);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOElementoNaoForUmaEnum() {
		when(this.element.getKind()).thenReturn(ElementKind.CLASS);

		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			assertEquals(ErrorMessage.INVALID_ELEMENT_KIND.getMessage(this.element.getSimpleName()), e.getMessage());
		}
	}

	@Test(expected = ResourcesException.class)
	public void deveLancarExcecaoQuandoAConstanteDaEnumNaoEstiverAnotadaException() throws ResourcesException {
		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(null);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoAConstanteDaEnumNaoEstiverAnotada() {
		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(null);

		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			assertEquals(ErrorMessage.INVALID_ELEMENT.getMessage(this.element.getSimpleName()), e.getMessage());
		}
	}

	@Test(expected = ResourcesException.class)
	public void deveLancarExcecaoQuandoOTemplateForVazioException() throws ResourcesException {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOTemplateForVazio() {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			assertEquals(ErrorMessage.EMPTY_TEMPLATE.getMessage(annotation.template(), this.enumConstant.getSimpleName()), e.getMessage());
		}
	}

	@Test(expected = ResourcesException.class)
	public void deveLancarExcecaoQuandoOTemplateTiverParametroVazioException() throws ResourcesException {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("{}");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOTemplateTiverParametroVazio() {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("{}");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			assertEquals(ErrorMessage.INVALID_PARAMETER.getMessage(this.enumConstant.getSimpleName(), ""), e.getMessage());
		}
	}

	@Test(expected = ResourcesException.class)
	public void deveLancarExcecaoQuandoOTemplateTiverParametrosInvalidosException() throws ResourcesException {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("{0}");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOTemplateTiverParametrosInvalidos() {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("{0}");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			assertEquals(ErrorMessage.INVALID_PARAMETER.getMessage(this.enumConstant.getSimpleName(), "0"), e.getMessage());
		}
	}

	@Test(expected = ResourcesException.class)
	public void deveLancarExcecaoQuandoOTemplateTiverParametrosIguaisException() throws ResourcesException {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("{PARAM} {PARAM}");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		this.validator.validate(this.element);
	}

	@Test
	public void deveLancarExcecaoQuandoOTemplateTiverParametrosIguais() {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("{PARAM} {PARAM}");

		when(this.enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			assertEquals(ErrorMessage.INVALID_TEMPLATE.getMessage(annotation.template(), this.enumConstant.getSimpleName()), e.getMessage());
		}
	}

	@Test
	public void naoDeveLancarExcecaoQuandoOElementoForUmaEnumValida() {
		try {
			this.validator.validate(this.element);
		} catch (ResourcesException e) {
			fail(e.getMessage());
		}
	}

	@After
	public void tearDown() {
		this.validator = null;
	}

}
