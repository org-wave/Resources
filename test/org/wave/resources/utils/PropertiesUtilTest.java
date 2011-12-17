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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wave.resources.annotations.Properties;
import org.wave.resources.utils.PropertiesUtil;


public class PropertiesUtilTest {

	@Mock
	private Properties annotation;

	@Mock
	private TypeElement element;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(this.annotation.bundleName()).thenReturn("");

		Name simpleName = mock(Name.class);
		when(simpleName.toString()).thenReturn("MappedEnum");

		Name qualifiedName = mock(Name.class);
		when(qualifiedName.toString()).thenReturn("br.com.company.project.enums.MappedEnum");

		when(this.element.getKind()).thenReturn(ElementKind.ENUM);
		when(this.element.getAnnotation(Properties.class)).thenReturn(this.annotation);
		when(this.element.getSimpleName()).thenReturn(simpleName);
		when(this.element.getQualifiedName()).thenReturn(qualifiedName);
	}

	@Test
	public void getPackageName_deveRetornarONomeDoPacoteQuandoOBundleNameForVazio() {
		assertEquals("br.com.company.project.enums.properties", PropertiesUtil.getPackageName(this.element));
	}

	@Test
	public void getPackageName_deveRetornarONomeDoPacoteQuandoOBundleNameEstiverPreenchido() {
		when(this.annotation.bundleName()).thenReturn("br.com.company.project.properties.Resources");

		assertEquals("br.com.company.project.properties", PropertiesUtil.getPackageName(this.element));
	}

	@Test
	public void getPropertiesName_deveRetornarONomeDoArquivoDePropriedadesQuandoOBundleNameForVazio() {
		assertEquals("MappedEnum.properties", PropertiesUtil.getPropertiesName(this.element));
	}

	@Test
	public void getPropertiesName_deveRetornarONomeDoArquivoDePropriedadesQuandoOBundleNameEstiverPreenchido() {
		when(this.annotation.bundleName()).thenReturn("br.com.company.project.properties.Resources");

		assertEquals("Resources.properties", PropertiesUtil.getPropertiesName(this.element));
	}

	@Test
	public void getBundleName_deveRetornarOBundleNameQuandoOBundleNameForVazio() {
		assertEquals("br.com.company.project.enums.properties.MappedEnum", PropertiesUtil.getBundleName(this.element));
	}

	@Test
	public void getBundleName_deveRetornarOBundleNameQuandoOBundleNameEstiverPreenchido() {
		when(this.annotation.bundleName()).thenReturn("br.com.company.project.properties.Resources");

		assertEquals("br.com.company.project.properties.Resources", PropertiesUtil.getBundleName(this.element));
	}

}
