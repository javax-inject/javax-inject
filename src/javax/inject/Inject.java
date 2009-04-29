package javax.inject;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Identifies injectable constructors, methods, and fields. Applies to static
 * as well as instance members. An injectable member may have any access
 * modifier (private, package-private, protected, public). Constructors are
 * injected first, followed by fields, and then methods. Fields and methods
 * in superclasses are injected before those in subclasses. Ordering of
 * injection among fields and among methods in the same class is not specified.
 * Which values are injected depends upon the injector implementation and its
 * configuration.
 * 
 * <p>Injectable constructors are annotated with {@code @Inject} and accept
 * zero or more dependencies as arguments. {@code @Inject} can apply to at most
 * one constructor per class.
 *
 * <p><tt><blockquote style="padding-left: 2em; text-indent: -2em;">@Inject
 *       <i>ConstructorModifiers<sub>opt</sub></i>
 *       <i>SimpleTypeName</i>(<i>FormalParameterList<sub>opt</sub></i>)
 *       <i>Throws<sub>opt</sub></i>
 *       <i>ConstructorBody</i></blockquote></tt>
 *
 * <p>{@code @Inject} is optional for public, no-argument constructors when no
 * other constructors are present. This enables injectors to invoke default
 * constructors.
 *
 * <p><tt><blockquote style="padding-left: 2em; text-indent: -2em;">
 *       {@literal @}Inject<sub><i>opt</i></sub>
 *       <i>Annotations<sub>opt</sub></i>
 *       public
 *       <i>SimpleTypeName</i>()
 *       <i>Throws<sub>opt</sub></i>
 *       <i>ConstructorBody</i></blockquote></tt>
 *
 * <p>Injectable fields:
 * <ul>
 *   <li>are annotated with {@code @Inject}.
 *   <li>are not final.
 *   <li>may have any otherwise valid name.</li></ul>
 *
 * <p><tt><blockquote style="padding-left: 2em; text-indent: -2em;">@Inject
 *       <i>FieldModifiers<sub>opt</sub></i>
 *       <i>Type</i>
 *       <i>VariableDeclarators</i>;</blockquote></tt>
 *
 * <p>Injectable methods:
 * <ul>
 *   <li>are annotated with {@code @Inject}.</li>
 *   <li>are not abstract.</li>
 *   <li>do not declare type parameters of their own.</li>
 *   <li>return {@code void}.</li>
 *   <li>may have any otherwise valid name.</li>
 *   <li>accept zero or more dependencies as arguments.</li></ul>
 *
 * <p><tt><blockquote style="padding-left: 2em; text-indent: -2em;">@Inject
 *       <i>MethodModifiers<sub>opt</sub></i>
 *       void
 *       <i>Identifier</i>(<i>FormalParameterList<sub>opt</sub></i>)
 *       <i>Throws<sub>opt</sub></i>
 *       <i>MethodBody</i></blockquote></tt>
 *
 * <p>For example:
 *
 * <pre>
 *   public class Car {
 *     // Injectable constructor
 *     &#064;Inject public Car(Engine engine) { ... }
 *
 *     // Injectable field
 *     &#064;Inject private Provider&lt;Seat> seatProvider;
 *
 *     // Injectable package-private method
 *     &#064;Inject void install(Windshield windshield, Trunk trunk) { ... }
 *   }</pre>
 *
 * <p>A method annotated with {@code @Inject} that overrides another method
 * annotated with {@code @Inject} will only be injected once per injection
 * request per instance. A method with <i>no</i> {@code @Inject} annotation
 * that overrides a method annotated with {@code @Inject} will not be
 * injected.
 *
 * <p>Injection of members annotated with {@code @Inject} is required by
 * default. This behavior can be overridden by setting {@link #optional()
 * optional} equal to {@code true}.
 *
 * <p>Detecting and resolving circular dependencies is left as an exercize for
 * the injector implementation. Circular dependencies between two constructors
 * is an obvious problem, but you can also have a circular dependency between
 * injectable fields or methods:
 *
 * <pre>
 *   class A {
 *     &#064;Inject B b;
 *   }
 *   class B {
 *     &#064;Inject A a;
 *   }</pre>
 *
 * <p>When constructing an instance of {@code A}, a naive injector
 * implementation might go into an infinite loop constructing an instance of
 * {@code B} to set on {@code A}, a second instance of {@code A} to set on
 * {@code B}, a second instance of {@code B} to set on the second instance of
 * {@code A}, and so on.
 *
 * <p>A conservative injector might detect the circular dependency at build
 * time and generate an error, at which point the programmer could break the
 * circular dependency by injecting {@link Provider Provider&lt;A>} or {@code
 * Provider<B>} instead of {@code A} or {@code B} respectively. Calling {@link
 * Provider#get() get()} on the provider directly from the constructor or
 * method it was injected into defeats the provider's ability to break up
 * circular dependencies. In the case of method or field injection, scoping
 * one of the dependencies (using {@linkplain Singleton singleton scope}, for
 * example) may also enable a valid circular relationship.
 *
 * @see javax.inject.Qualifier @Qualifier
 * @see javax.inject.Provider
 */
@Target({ METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
@Documented
public @interface Inject {

    /**
     * Whether or not injection is optional. If {@code true}, the injector's
     * behavior varies depending on the type of injection point:
     *
     * <p><ul>
     *   <li><b>Constructors:</b> <i>Not allowed</i></li>
     *   <li><b>Fields:</b> If a dependency matching the field can't
     *     be found, the injector will not set the field.</li>
     *   <li><b>Methods:</b> If a dependency matching a method parameter can't
     *     be found, the injector will skip invoking the method entirely, even
     *     if other arguments could be provided.</li>
     * </ul>
     * 
     * <p>If an applicable dependency has been configured but the injector
     * encounters an error while resolving the dependency (a transitive
     * dependency could be missing, for example), the injector should generate
     * an error, not skip the injection. For example:
     *
     * <pre>
     *   &#064;Inject(optional=true) Gps gps;</pre>
     *
     * <p>If a Gps isn't available at all, the injector will simply not set the
     * {@code gps} field. If a Gps is available but an algorithm it depends
     * upon can't be found, the injector will generate an error.
     */
    boolean optional() default false;
}
