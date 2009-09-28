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

package org.atinject.tck.auto;

import org.atinject.tck.Tester;
import org.atinject.tck.auto.accessories.SpareTire;
import org.atinject.tck.auto.accessories.Cupholder;

import junit.framework.TestCase;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class Convertible implements Car {

    @Inject DriversSeat driversSeatA;
    @Inject DriversSeat driversSeatB;
    @Inject SpareTire spareTire;
    @Inject Cupholder cupholder;
    @Inject Provider<Engine> engineProvider;

    private boolean methodWithZeroParamsInjected;
    private boolean methodWithMultipleParamsInjected;
    private boolean methodWithNonVoidReturnInjected;

    private List<String> moreProblems = new ArrayList<String>();

    private Seat constructorPlainSeat;
    private Seat constructorDriversSeat;
    private Tire constructorPlainTire;
    private Tire constructorSpareTire;
    private Provider<Seat> constructorPlainSeatProvider = nullProvider();
    private Provider<Seat> constructorDriversSeatProvider = nullProvider();
    private Provider<Tire> constructorPlainTireProvider = nullProvider();
    private Provider<Tire> constructorSpareTireProvider = nullProvider();

    @Inject Seat fieldPlainSeat;
    @Inject @Drivers Seat fieldDriversSeat;
    @Inject Tire fieldPlainTire;
    @Inject @Named("spare") Tire fieldSpareTire;
    @Inject Provider<Seat> fieldPlainSeatProvider = nullProvider();
    @Inject @Drivers Provider<Seat> fieldDriversSeatProvider = nullProvider();
    @Inject Provider<Tire> fieldPlainTireProvider = nullProvider();
    @Inject @Named("spare") Provider<Tire> fieldSpareTireProvider = nullProvider();

    private Seat methodPlainSeat;
    private Seat methodDriversSeat;
    private Tire methodPlainTire;
    private Tire methodSpareTire;
    private Provider<Seat> methodPlainSeatProvider = nullProvider();
    private Provider<Seat> methodDriversSeatProvider = nullProvider();
    private Provider<Tire> methodPlainTireProvider = nullProvider();
    private Provider<Tire> methodSpareTireProvider = nullProvider();

    @Inject static Seat staticFieldPlainSeat;
    @Inject @Drivers static Seat staticFieldDriversSeat;
    @Inject static Tire staticFieldPlainTire;
    @Inject @Named("spare") static Tire staticFieldSpareTire;
    @Inject static Provider<Seat> staticFieldPlainSeatProvider = nullProvider();
    @Inject @Drivers static Provider<Seat> staticFieldDriversSeatProvider = nullProvider();
    @Inject static Provider<Tire> staticFieldPlainTireProvider = nullProvider();
    @Inject @Named("spare") static Provider<Tire> staticFieldSpareTireProvider = nullProvider();

    private static Seat staticMethodPlainSeat;
    private static Seat staticMethodDriversSeat;
    private static Tire staticMethodPlainTire;
    private static Tire staticMethodSpareTire;
    private static Provider<Seat> staticMethodPlainSeatProvider = nullProvider();
    private static Provider<Seat> staticMethodDriversSeatProvider = nullProvider();
    private static Provider<Tire> staticMethodPlainTireProvider = nullProvider();
    private static Provider<Tire> staticMethodSpareTireProvider = nullProvider();

    @Inject Convertible(
            Seat plainSeat,
            @Drivers Seat driversSeat,
            Tire plainTire,
            @Named("spare") Tire spareTire,
            Provider<Seat> plainSeatProvider,
            @Drivers Provider<Seat> driversSeatProvider,
            Provider<Tire> plainTireProvider,
            @Named("spare") Provider<Tire> spareTireProvider) {
        constructorPlainSeat = plainSeat;
        constructorDriversSeat = driversSeat;
        constructorPlainTire = plainTire;
        constructorSpareTire = spareTire;
        constructorPlainSeatProvider = plainSeatProvider;
        constructorDriversSeatProvider = driversSeatProvider;
        constructorPlainTireProvider = plainTireProvider;
        constructorSpareTireProvider = spareTireProvider;
    }

    Convertible() {
        throw new AssertionError("Unexpected call to non-injectable constructor");
    }

    void setSeat(Seat unused) {
        throw new AssertionError("Unexpected call to non-injectable method");
    }

    @Inject void injectMethodWithZeroArgs() {
        methodWithZeroParamsInjected = true;
    }

    @Inject String injectMethodWithNonVoidReturn() {
        methodWithNonVoidReturnInjected = true;
        return "unused";
    }

    @Inject void injectInstanceMethodWithManyArgs(
            Seat plainSeat,
            @Drivers Seat driversSeat,
            Tire plainTire,
            @Named("spare") Tire spareTire,
            Provider<Seat> plainSeatProvider,
            @Drivers Provider<Seat> driversSeatProvider,
            Provider<Tire> plainTireProvider,
            @Named("spare") Provider<Tire> spareTireProvider) {
        methodWithMultipleParamsInjected = true;

        methodPlainSeat = plainSeat;
        methodDriversSeat = driversSeat;
        methodPlainTire = plainTire;
        methodSpareTire = spareTire;
        methodPlainSeatProvider = plainSeatProvider;
        methodDriversSeatProvider = driversSeatProvider;
        methodPlainTireProvider = plainTireProvider;
        methodSpareTireProvider = spareTireProvider;
    }

    @Inject static void injectStaticMethodWithManyArgs(
            Seat plainSeat,
            @Drivers Seat driversSeat,
            Tire plainTire,
            @Named("spare") Tire spareTire,
            Provider<Seat> plainSeatProvider,
            @Drivers Provider<Seat> driversSeatProvider,
            Provider<Tire> plainTireProvider,
            @Named("spare") Provider<Tire> spareTireProvider) {
        staticMethodPlainSeat = plainSeat;
        staticMethodDriversSeat = driversSeat;
        staticMethodPlainTire = plainTire;
        staticMethodSpareTire = spareTire;
        staticMethodPlainSeatProvider = plainSeatProvider;
        staticMethodDriversSeatProvider = driversSeatProvider;
        staticMethodPlainTireProvider = plainTireProvider;
        staticMethodSpareTireProvider = spareTireProvider;
    }

    /**
     * Returns a provider that always returns null. This is used as a default
     * value to avoid null checks for omitted provider injections.
     */
    private static <T> Provider<T> nullProvider() {
        return new Provider<T>() {
            public T get() {
                return null;
            }
        };
    }

    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        tester.test(methodWithZeroParamsInjected, "Zero-parmeter method not injected");
        tester.test(methodWithMultipleParamsInjected, "Multi-parameter method not injected");
        tester.test(methodWithNonVoidReturnInjected, "Non-void method not injected");
        tester.test(driversSeatA != driversSeatB, "@Singleton inherited from supertype");

        testInjectedValues(tester);
        testProviders(tester);

        Engine engine = engineProvider.get();

        if (spareTire == null || cupholder == null || engineProvider == null) {
            tester.addProblem("Fields not injected");
        } else {
            spareTire.check(tester);
            cupholder.check(tester);

            if (engine == null) {
                tester.addProblem("Provider returned null");
            } else {
                engine.check(tester);
            }
        }
    }

    private void testInjectedValues(Tester tester) {
        testExpectedValues(tester, "injected constructor",
                constructorPlainSeat, constructorDriversSeat, constructorPlainTire, constructorSpareTire);
        testExpectedValues(tester, "provider injected into a constructor", constructorPlainSeatProvider.get(),
                constructorDriversSeatProvider.get(), constructorPlainTireProvider.get(), constructorSpareTireProvider.get());
        testExpectedValues(tester, "injected field",
                fieldPlainSeat, fieldDriversSeat, fieldPlainTire, fieldSpareTire);
        testExpectedValues(tester, "provider injected into a field", fieldPlainSeatProvider.get(),
                fieldDriversSeatProvider.get(), fieldPlainTireProvider.get(), fieldSpareTireProvider.get());
        testExpectedValues(tester, "injected method",
                methodPlainSeat, methodDriversSeat, methodPlainTire, methodSpareTire);
        testExpectedValues(tester, "provider injected into a method", methodPlainSeatProvider.get(),
                methodDriversSeatProvider.get(), methodPlainTireProvider.get(), methodSpareTireProvider.get());
        testExpectedValues(tester, "injected static field",
                staticFieldPlainSeat, staticFieldDriversSeat, staticFieldPlainTire, staticFieldSpareTire);
        testExpectedValues(tester, "provider injected into a static field", staticFieldPlainSeatProvider.get(),
                staticFieldDriversSeatProvider.get(), staticFieldPlainTireProvider.get(), staticFieldSpareTireProvider.get());
        testExpectedValues(tester, "injected static method",
                staticMethodPlainSeat, staticMethodDriversSeat, staticMethodPlainTire, staticMethodSpareTire);
        testExpectedValues(tester, "provider injected into a static method", staticMethodPlainSeatProvider.get(),
                staticMethodDriversSeatProvider.get(), staticMethodPlainTireProvider.get(), staticMethodSpareTireProvider.get());
    }

    private void testProviders(Tester tester) {
        testProviderProvidesNewValuesEachTime(tester,
                constructorDriversSeatProvider, constructorPlainTireProvider, constructorSpareTireProvider);
        testProviderProvidesNewValuesEachTime(tester,
                fieldDriversSeatProvider, fieldPlainTireProvider, fieldSpareTireProvider);
        testProviderProvidesNewValuesEachTime(tester,
                methodDriversSeatProvider, methodPlainTireProvider, methodSpareTireProvider);
        testProviderProvidesSameValueEachTime(tester, constructorPlainSeatProvider);
        testProviderProvidesSameValueEachTime(tester, fieldPlainSeatProvider);
        testProviderProvidesSameValueEachTime(tester, methodPlainSeatProvider);
    }

    private void testProviderProvidesSameValueEachTime(Tester tester, Provider<Seat> provider) {
        tester.test(provider.get() == provider.get(),
                "Different instance returned by repeated calls to Provider.get()");
    }

    private void testExpectedValues(Tester tester, String injectionMechanism,
                Seat plainSeat, Seat driversSeat, Tire plainTire, Tire spareTire) {
        tester.test(!(plainSeat instanceof DriversSeat),
                "Wrong value injected for " + injectionMechanism);
        tester.test(driversSeat instanceof DriversSeat,
                "Wrong value injected for qualified " + injectionMechanism);
        tester.test(!(plainTire instanceof SpareTire),
                "Wrong value injected for " + injectionMechanism);
        tester.test(spareTire instanceof SpareTire,
                "Wrong value injected for @Named " + injectionMechanism);
    }

    private void testProviderProvidesNewValuesEachTime(Tester tester, Provider<?>... providers) {
        for (Provider provider : providers) {
            tester.test(provider.get() != provider.get(),
                    "Same instance returned by repeated calls to Provider.get()");
        }
    }

    /**
     * Tests against the Convertible instance.
     *
     * @see #car
     */
    public static class Test extends TestCase {

        /**
         * The instance to test. Making it static isn't ideal, but it saves
         * us from having to repeat a boatload of JUnit code.
         */
        public static Convertible car;

        public void testAll() {
            Tester tester = new Tester();
            car.check(tester);
            assertFalse(tester.problems().toString(),
                    tester.problems().iterator().hasNext());
        }
    }
}
