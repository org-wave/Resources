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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wave.resources.annotations.Resource;
import org.wave.resources.factories.PropertiesFactory;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertiesFactoryTest {

	@Mock
	private Resource annotation;

	private List enclosedElements;

	@Mock
	private Element element;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(this.annotation.template()).thenReturn("Template {PARAM1} {PARAM2}");

		Name simpleName = mock(Name.class);
		when(simpleName.toString()).thenReturn("ENUM_CONSTANT");

		Element enumConstant = mock(Element.class);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enumConstant.getSimpleName()).thenReturn(simpleName);
		when(enumConstant.getAnnotation(Resource.class)).thenReturn(this.annotation);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		this.enclosedElements = new ArrayList();
		this.enclosedElements.add(enumConstant);
		this.enclosedElements.add(field);

		when(this.element.getKind()).thenReturn(ElementKind.ENUM);
		when(this.element.getEnclosedElements()).thenReturn(this.enclosedElements);
	}

	@Test
	public void deveCriarUmPropertiesVazioQuandoAEnumEstiverVazia() {
		this.enclosedElements = new ArrayList();
		when(this.element.getEnclosedElements()).thenReturn(this.enclosedElements);

		Properties properties = PropertiesFactory.create(this.element);

		assertTrue(properties.isEmpty());
	}

	@Test
	public void deveCriarUmPropertiesQuandoOTemplateNaoTiverParametro() {
		when(this.annotation.template()).thenReturn("Template");

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Template", properties.getProperty("enum.constant"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoOTemplateTiverUmParametro() {
		when(this.annotation.template()).thenReturn("Template {PARAM}");

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Template {0}", properties.getProperty("enum.constant"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoOTemplateTiverMaisDeUmParametro() {
		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(1, properties.size());
		assertEquals("Template {0} {1}", properties.getProperty("enum.constant"));
	}

	@Test
	public void deveCriarUmPropertiesQuandoAEnumTiverMaisDeUmaConstante() {
		Resource annotation = mock(Resource.class);
		when(annotation.template()).thenReturn("Template {PARAM}");

		Name simpleName = mock(Name.class);
		when(simpleName.toString()).thenReturn("ANOTHER_ENUM_CONSTANT");

		Element enumConstant = mock(Element.class);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(enumConstant.getSimpleName()).thenReturn(simpleName);
		when(enumConstant.getAnnotation(Resource.class)).thenReturn(annotation);

		this.enclosedElements.add(enumConstant);

		Properties properties = PropertiesFactory.create(this.element);

		assertEquals(2, properties.size());
		assertEquals("Template {0} {1}", properties.getProperty("enum.constant"));
		assertEquals("Template {0}", properties.getProperty("another.enum.constant"));
	}

	@After
	public void tearDown() {
		this.enclosedElements = null;
	}

}
