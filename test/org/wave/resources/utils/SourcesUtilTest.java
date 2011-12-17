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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wave.resources.messages.ErrorMessage;
import org.wave.resources.utils.SourcesUtil;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class SourcesUtilTest {

	@Mock
	private TypeElement enclosingElement;

	@Mock
	private Element enclosedElement;

	private List enclosedElements;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Name enclosingSimpleName = mock(Name.class);
		when(enclosingSimpleName.toString()).thenReturn("MappedEnum");

		Name enclosingQualifiedName = mock(Name.class);
		when(enclosingQualifiedName.toString()).thenReturn("br.com.company.project.enums.MappedEnum");

		when(this.enclosingElement.getKind()).thenReturn(ElementKind.ENUM);
		when(this.enclosingElement.getSimpleName()).thenReturn(enclosingSimpleName);
		when(this.enclosingElement.getQualifiedName()).thenReturn(enclosingQualifiedName);

		Name enclosedSimpleName = mock(Name.class);
		when(enclosedSimpleName.toString()).thenReturn("ENUM_CONSTANT");

		when(this.enclosedElement.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
		when(this.enclosedElement.getSimpleName()).thenReturn(enclosedSimpleName);
		when(this.enclosedElement.getEnclosingElement()).thenReturn(this.enclosingElement);

		this.enclosedElements = new ArrayList();
	}

	@Test
	public void getPackageName_deveRetornarONomeDoPacoteQuandoOElementoForEnum() {
		assertEquals("br.com.company.project.enums", SourcesUtil.getPackageName(this.enclosingElement));
	}

	@Test
	public void getPackageName_deveRetornarONomeDoPacoteQuandoOElementoForEnumConstant() {
		assertEquals("br.com.company.project.enums.resources", SourcesUtil.getPackageName(this.enclosedElement));
	}

	@Test
	public void getSimpleName_deveRetornarONomeSimplesDaClasseQuandoOElementoForEnum() {
		assertEquals("MappedEnum_", SourcesUtil.getSimpleName(this.enclosingElement));
	}

	@Test
	public void getSimpleName_deveRetornarONomeSimplesDaClasseQuandoOElementoForEnumConstant() {
		assertEquals("MappedEnum_EnumConstant", SourcesUtil.getSimpleName(this.enclosedElement));
	}

	@Test
	public void getQualifiedName_deveRetornarONomeQualificadoDaClasseQuandoOElementForEnum() {
		assertEquals("br.com.company.project.enums.MappedEnum_", SourcesUtil.getQualifiedName(this.enclosingElement));
	}

	@Test
	public void getQualifiedName_deveRetornarONomeQualificadoDaClasseQuandoOElementForEnumConstant() {
		assertEquals("br.com.company.project.enums.resources.MappedEnum_EnumConstant", SourcesUtil.getQualifiedName(this.enclosedElement));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getEnumConstants_deveLancarExcecaoQuandoOElementoNaoForEnumException() {
		SourcesUtil.getEnumConstants(this.enclosedElement);
	}

	@Test
	public void getEnumConstants_deveLancarExcecaoQuandoOElementoNaoForEnum() {
		try {
			SourcesUtil.getEnumConstants(this.enclosedElement);
		} catch (IllegalArgumentException e) {
			assertEquals(ErrorMessage.INVALID_ELEMENT_KIND.getMessage(this.enclosedElement.getSimpleName()), e.getMessage());
		}
	}

	@Test
	public void getEnumConstants_deveRetornarUmaListaVaziaQuandoAEnumEstiverVazia() {
		when(this.enclosingElement.getEnclosedElements()).thenReturn(this.enclosedElements);

		assertTrue(SourcesUtil.getEnumConstants(this.enclosingElement).isEmpty());
	}

	@Test
	public void getEnumConstants_deveRetornarUmaListaVaziaQuandoAEnumNaoTiverConstantes() {
		when(this.enclosingElement.getEnclosedElements()).thenReturn(this.enclosedElements);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		this.enclosedElements.add(field);

		assertTrue(SourcesUtil.getEnumConstants(this.enclosingElement).isEmpty());
	}

	@Test
	public void getEnumConstants_deveRetornarUmaListaPreenchidaQuandoAEnumTiverConstantes() {
		when(this.enclosingElement.getEnclosedElements()).thenReturn(this.enclosedElements);

		Element enumConstant = mock(Element.class);
		when(enumConstant.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);

		Element field = mock(Element.class);
		when(field.getKind()).thenReturn(ElementKind.FIELD);

		this.enclosedElements.add(enumConstant);
		this.enclosedElements.add(field);

		List<Element> enumConstants = SourcesUtil.getEnumConstants(this.enclosingElement);
		assertEquals(1, enumConstants.size());
		assertEquals(ElementKind.ENUM_CONSTANT, enumConstants.get(0).getKind());
	}

	@After
	public void tearDown() {
		this.enclosedElements = null;
	}

}
