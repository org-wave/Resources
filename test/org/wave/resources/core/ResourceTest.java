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
package org.wave.resources.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wave.resources.configurations.ResourcesConfiguration;
import org.wave.resources.core.Resource;
import org.wave.resources.examples.MappedEnum_Enum;
import org.wave.resources.examples.MappedEnum_EnumConstant;


public class ResourceTest {

	private String bundleName;

	private Resource resource;

	@Before
	public void setUp() {
		this.bundleName = "org.wave.resources.examples.properties.MappedEnum";
	}

	@Test
	public void deveRetornarUmaChaveSimplesQuandoONomeDaClasseForSimples() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		assertEquals("enum", this.resource.getKey());
	}

	@Test
	public void deveRetornarUmaChaveCompostaQuandoONomeDaClasseForComposto() {
		this.resource = new MappedEnum_EnumConstant(this.bundleName);

		assertEquals("enum.constant", this.resource.getKey());
	}

	@Test
	public void deveRetornarOTemplatePadraoQuandoOLocaleNaoForAlterado() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		assertEquals("Enum", this.resource.getTemplate());
	}

	@Test
	public void deveRetornarUmDeterminadoTemplateQuandoOLocaleForAlterado() {
		ResourcesConfiguration.setLocale(Locale.CHINESE);

		this.resource = new MappedEnum_Enum(this.bundleName);

		assertEquals("Chinese enum", this.resource.getTemplate());

		ResourcesConfiguration.setLocale(Locale.getDefault());
	}

	@Test
	public void deveRetornarOValorQuandoNaoHouverParametros() {
		String value = Resource.getValue(this.bundleName, "enum");

		assertEquals("Enum", value);
	}

	@Test
	public void deveRetornarOValorQuandoHouverParametros() {
		String value = Resource.getValue(this.bundleName, "enum.constant", "param");

		assertEquals("Enum constant param", value);
	}

	@Test
	public void deveRetornarFalsoQuandoComparadoAUmaReferenciaNula() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		assertFalse(this.resource.equals(null));
	}

	@Test
	public void deveRetornarFalsoQuandoComparadoAUmObjetoDeOutraClasse() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		assertFalse(this.resource.equals(new String()));
	}

	@Test
	public void deveRetornarFalsoQuandoComparadoAUmRecursoDeBundleNameDiferente() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		Resource comparableResource = new MappedEnum_Enum("br.com.company.project.Resources");

		assertFalse(this.resource.equals(comparableResource));
	}

	@Test
	public void deveRetornarFalsoQuandoComparadoAUmRecursoDeChaveDiferente() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		Resource comparableResource = new MappedEnum_EnumConstant(this.bundleName);

		assertFalse(this.resource.equals(comparableResource));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoComparadoAUmRecursoDeMesmosBundleNameEChave() {
		this.resource = new MappedEnum_Enum(this.bundleName);

		Resource comparableResource = new MappedEnum_Enum(this.bundleName);

		assertTrue(this.resource.equals(comparableResource));
	}

	@After
	public void tearDown() {
		this.resource = null;
		this.bundleName = null;
	}

}
