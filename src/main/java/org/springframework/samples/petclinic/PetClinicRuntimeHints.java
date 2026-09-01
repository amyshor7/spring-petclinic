/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import org.thymeleaf.expression.Numbers;
import org.thymeleaf.expression.Strings;
import org.thymeleaf.expression.Temporals;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.vet.Vet;

public class PetClinicRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.resources().registerPattern("db/*"); // https://github.com/spring-projects/spring-boot/issues/32654
		hints.resources().registerPattern("db/*/*"); // nested db/{h2,mysql,postgres}
		hints.resources().registerPattern("messages/*");
		hints.resources().registerPattern("mysql-default-conf");
		hints.reflection().registerType(BaseEntity.class, typeHint -> typeHint.withJavaSerialization(true));
		hints.reflection().registerType(Person.class, typeHint -> typeHint.withJavaSerialization(true));
		hints.reflection().registerType(Vet.class, typeHint -> typeHint.withJavaSerialization(true));
		// Thymeleaf expression objects (#strings, #numbers, #temporals) are invoked
		// reflectively through SpEL when rendering the templates
		hints.reflection().registerType(Strings.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Numbers.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(Temporals.class, MemberCategory.INVOKE_PUBLIC_METHODS);
	}

}
