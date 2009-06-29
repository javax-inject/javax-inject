/*
 * Copyright (C) 2009 The JSR-330 Expert Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Provides annotations for dependency injection. Enables portable objects
 * but leaves external dependency configuration up to <i>the injector</i>
 * implementation.
 *
 * <p>For the purposes of this specification, the injector is anything that
 * injects objects. Injector implementations can take many forms. An injector
 * could rely on code generation or reflection. An injector could configure
 * itself using XML, annotations, a DSL (domain-specific language), or even
 * plain Java code. A "container", for some definition, can be an injector,
 * but this specification aims to minimize restrictions on injector
 * implementations.
 *
 * <p>For example, an injector that uses compile time code generation may not
 * even have its own run time representation. Other injectors may not be able
 * to generate code at all, neither at compile nor run time.
 *
 * @see javax.inject.Inject @Inject
 */
package javax.inject;
