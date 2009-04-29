// TODO: When generating Javadocs, include:
//  -header "<font color='red'><b>This is a DRAFT specification.</b></font>"

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
