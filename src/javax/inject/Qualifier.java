package javax.inject;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

/**
 * Identifies qualifier annotations. Anyone can define a new qualifier. A
 * qualifier annotation:
 *
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
