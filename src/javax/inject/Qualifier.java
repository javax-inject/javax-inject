package javax.inject;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

/**
 * Identifies qualifier annotations. A qualifier annotates an injectable field
 * or parameter and, combined with the type, identifies the implementation to
 * inject. Qualifiers are optional and no more than one should annotate a
 * single field or parameter. The qualifiers are bold in the following
 * example:
 *
 * <pre>
 *   public class Car {
 *     &#064;Inject private <b>@Leather</b> Provider&lt;Seat> seatProvider;
 *
 *     &#064;Inject void install(<b>@Tinted</b> Windshield windshield,
 *         <b>@Big</b> Trunk trunk) { ... }
 *   }</pre>
 *
 * <p>If one injectable method overrides another, the overriding method's
 * parameters do not automatically inherit qualifiers from the overridden
 * method's parameters.
 *
 * <p>Anyone can define a new qualifier. A qualifier annotation:
 * <ul>
 *   <li>is annotated with {@code @Qualifier}, {@code @Retention(RUNTIME)},
 *      and typically {@code @Documented}.</li>
 *   <li>can have attributes.</li>
 *   <li>is <i>not</i> annotated with {@code @Target}. While this
 *      specification only covers applying qualifiers to fields and parameters,
 *      some injector configurations might use qualifier annotations in other
 *      places (on methods or classes for example).</li>
 *   <li>may be part of the public API, much like the dependency type, but
 *      unlike implementation types which needn't be part of the public
 *      API.</li>
 * </ul>
 *
 * <p>For example:
 *
 * <pre>
 *   &#064;java.lang.annotation.Documented
 *   &#064;java.lang.annotation.Retention(RUNTIME)
 *   &#064;javax.inject.Qualifier
 *   public @interface Leather {
 *     Color color() default Color.TAN;
 *     public enum Color { RED, BLACK, TAN }
 *   }</pre>
 *
 * @see javax.inject.Named @Named
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface Qualifier {}
