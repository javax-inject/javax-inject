/**
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.googlecode.atinject.auto;

import com.googlecode.atinject.Tester;
import com.googlecode.atinject.auto.accessories.SpareTire;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class Convertible implements Car {

    @Inject DriversSeat driversSeatA;
    @Inject DriversSeat driversSeatB;
    @Inject V8Engine engine;
    @Inject SpareTire spareTire;

    private boolean methodWithZeroParamsInjected;
    private boolean methodWithMultipleParamsInjected;
    private boolean methodWithNonVoidReturnInjected;

    private List<String> moreProblems = new ArrayList<String>();

    private Seat constructorPlainSeat;
    private Seat constructorDriversSeat;
    private Tire constructorPlainTire;
    private Tire constructorSpareTire;
    private Provider<Seat> constructorPlainSeatProvider;
    private Provider<Seat> constructorDriversSeatProvider;
    private Provider<Tire> constructorPlainTireProvider;
    private Provider<Tire> constructorSpareTireProvider;

    @Inject Seat fieldPlainSeat;
    @Inject @Drivers Seat fieldDriversSeat;
    @Inject Tire fieldPlainTire;
    @Inject @Named("spare") Tire fieldSpareTire;
    @Inject Provider<Seat> fieldPlainSeatProvider;
    @Inject @Drivers Provider<Seat> fieldDriversSeatProvider;
    @Inject Provider<Tire> fieldPlainTireProvider;
    @Inject @Named("spare") Provider<Tire> fieldSpareTireProvider;

    private Seat methodPlainSeat;
    private Seat methodDriversSeat;
    private Tire methodPlainTire;
    private Tire methodSpareTire;
    private Provider<Seat> methodPlainSeatProvider;
    private Provider<Seat> methodDriversSeatProvider;
    private Provider<Tire> methodPlainTireProvider;
    private Provider<Tire> methodSpareTireProvider;

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

    @Inject void injectMethodWithManyArgs(
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

    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        // values are injected
        tester.test(methodWithZeroParamsInjected, "Zero-parmeter method not injected");
        tester.test(methodWithMultipleParamsInjected, "Multi-parameter method not injected");
        tester.test(methodWithNonVoidReturnInjected, "Non-void method not injected");

        // singleton is not @Inherited
        tester.test(driversSeatA != driversSeatB, "@Singleton inherited from supertype");

        testInjectedValues(tester);
        testProviders(tester);

        spareTire.check(tester);
        engine.check(tester);
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
                "Wrong type injected for " + injectionMechanism);
        tester.test(driversSeat instanceof DriversSeat,
                "Wrong type injected for qualified " + injectionMechanism);
        tester.test(!(plainTire instanceof SpareTire),
                "Wrong type injected for " + injectionMechanism);
        tester.test(spareTire instanceof SpareTire,
                "Wrong type injected for @Named " + injectionMechanism);
    }

    private void testProviderProvidesNewValuesEachTime(Tester tester, Provider<?>... providers) {
        for (Provider provider : providers) {
            tester.test(provider.get() != provider.get(),
                    "Same instance returned by repeated calls to Provider.get()");
        }
    }
}
