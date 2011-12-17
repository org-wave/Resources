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

import java.util.List;

import org.junit.Test;
import org.wave.resources.messages.ErrorMessage;
import org.wave.resources.utils.StringUtil;


public class StringUtilTest {

	@Test(expected = IllegalArgumentException.class)
	public void capitalize_deveLancarExcecaoQuandoAStringForNulaException() {
		StringUtil.capitalize(null);
	}

	@Test
	public void capitalize_deveLancarExcecaoQuandoAStringForNula() {
		try {
			StringUtil.capitalize(null);
		} catch (Exception e) {
			assertEquals(ErrorMessage.NULL_STRING.getMessage(), e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void capitalize_deveLancarExcecaoQuandoODelimitadorForNuloException() {
		StringUtil.capitalize("word", null);
	}

	@Test
	public void capitalize_deveLancarExcecaoQuandoODelimitadorForNulo() {
		try {
			StringUtil.capitalize("word", null);
		} catch (Exception e) {
			assertEquals(ErrorMessage.NULL_DELIMITER.getMessage(), e.getMessage());
		}
	}

	@Test
	public void capitalize_deveCapitalizarUmaStringQuandoEstiverEmMinusculas() {
		assertEquals("Word", StringUtil.capitalize("word"));
	}

	@Test
	public void capitalize_deveCapitalizarUmaStringQuandoEstiverEmMaiusculas() {
		assertEquals("WORD", StringUtil.capitalize("WORD"));
	}

	@Test
	public void capitalize_deveCapitalizarUmaStringQuandoEstiverEmMinusculasETiverDelimitador() {
		assertEquals("Compound_Word", StringUtil.capitalize("compound_word", "_"));
	}

	@Test
	public void capitalize_deveCapitalizarUmaStringQuandoEstiverEmMaiusculasETiverDelimitador() {
		assertEquals("COMPOUND_WORD", StringUtil.capitalize("COMPOUND_WORD", "_"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getParams_deveLancarExcecaoQuandoAStringForNulaException() {
		StringUtil.getParams(null);
	}

	@Test
	public void getParams_deveLancarExcecaoQuandoAStringForNula() {
		try {
			StringUtil.getParams(null);
		} catch (Exception e) {
			assertEquals(ErrorMessage.NULL_STRING.getMessage(), e.getMessage());
		}
	}

	@Test
	public void getParams_deveRetornarUmaListaVaziaQuandoAStringNaoTiverParametros() {
		List<String> params = StringUtil.getParams("PARAM");

		assertTrue(params.isEmpty());
	}

	@Test
	public void getParams_deveRetornarUmaListaPreenchidaQuandoAStringTiverParametros() {
		List<String> params = StringUtil.getParams("{PARAM}");

		assertEquals(1, params.size());
		assertEquals("param", params.get(0));
	}

}
