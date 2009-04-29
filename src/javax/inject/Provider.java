package javax.inject;

/**
 * Provides instances of {@code T}. For any type {@code T} that can be
 * injected, you can also inject {@code Provider<T>}. Compared to injecting
 * {@code T} directly, injecting {@code Provider<T>} enables:
 *
 * <ul>
 *   <li>retrieving multiple instances.</li>
 *   <li>lazy or optional retrieval of an instance.</li>
 *   <li>breaking circular dependencies.</li>
 *   <li>abstracting scope so you can look up an instance in a smaller scope
 *      from an instance in a containing scope.</li>
 * </ul>
 *
 * <p>For example:
 *
 * <pre>
 *   class Car {
 *     &#064;Inject Car(Provider&lt;Seat> seatProvider) {
 *       Seat driver = seatProvider.get();
 *       Seat passenger = seatProvider.get();
 *       ...
 *     }
 *   }</pre>
 */
public interface Provider<T> {

    // TODO: Specify OutOfScopeException (or IllegalStateException) and
    //  ProvisionException?

    /**
     * Provides an instance of {@code T}.
     */
    T get();
}
