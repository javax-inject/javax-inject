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

package com.googlecode.atinject;

import com.googlecode.atinject.auto.Car;
import com.googlecode.atinject.auto.Convertible;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.framework.TestCase;

/**
 * Extend this class, implement {@link #getCar()}, and declare a static
 * {@code suite} method (a JUnit convention):
 *
 * <pre>
 * public class MyTck extends Tck {
 *   protected Car getCar() {
 *      return new MyInjector().getInstance(Car.class);
 *   }
 *   public static Test suite() {
 *     return new MyTck();
 *   }
 * }
 * </pre>
 *
 * <p>Then, run the tests using JUnit. For example:
 *
 * <pre>
 * java junit.textui.TestRunner MyTck
 * </pre>
 */
public abstract class Tck implements Test {

    private final Test delegate;

    protected Tck() {
        Car car;
        try {
            car = getCar();
        } catch (Throwable t) {
            delegate = new Failure("getCar() threw an exception", t);
            return;
        }

        if (car == null) {
            delegate = new Failure("getCar() returned null",
                    new NullPointerException());
            return;
        }

        if (!(car instanceof Convertible)) {
            delegate = new Failure(
                    "getCar() did not return an instance of Convertible",
                    new ClassCastException("Expected Convertible, got "
                            + car.getClass().getName()));
            return;
        }

        Convertible.Test.car = (Convertible) car;
        delegate = new TestSuite(Convertible.Test.class);
    }

    public int countTestCases() {
        return delegate.countTestCases();
    }

    public void run(TestResult result) {
        delegate.run(result);
    }

    /**
     * Returns a {@link com.googlecode.atinject.auto.Car} constructed by an
     * injector with the following configuration:
     *
     * <ul>
     *   <li>{@link com.googlecode.atinject.auto.Car} is implemented by
     *       {@link com.googlecode.atinject.auto.Convertible Convertible}.
     *   <li>{@link com.googlecode.atinject.auto.Drivers @Drivers} {@link com.googlecode.atinject.auto.Seat Seat} is
     *       implemented by {@link com.googlecode.atinject.auto.DriversSeat DriversSeat}.
     *   <li>{@link com.googlecode.atinject.auto.Engine Engine} is implemented by
     *       {@link com.googlecode.atinject.auto.V8Engine V8Engine}.
     *   <li>{@link javax.inject.Named @Named("spare")} {@link com.googlecode.atinject.auto.Tire Tire} is implemented by
     *       {@link com.googlecode.atinject.auto.accessories.SpareTire SpareTire}.
     *   <li>The following concrete classes may also be injected: {@link com.googlecode.atinject.auto.accessories.Cupholder
     *       Cupholder}, {@link com.googlecode.atinject.auto.Tire Tire} and {@link com.googlecode.atinject.auto.FuelTank
     *       FuelTank}.
     * </ul>
     *
     * <p>The static members of the following types shall also be injected: {@link com.googlecode.atinject.auto.Convertible
     * Convertible}, {@link com.googlecode.atinject.auto.Tire Tire}, and {@link
     * com.googlecode.atinject.auto.accessories.SpareTire SpareTire}.
     */
    protected abstract Car getCar();

    static class Failure extends TestCase {
        /*
         * We have to extend TestCase instead of implementing Test so we
         * can set the name.
         */

        final Throwable t;
        Failure(String name, Throwable t) {
            setName(name);
            this.t = t;
        }
        @Override public int countTestCases() {
            return 1;
        }
        @Override public void run(TestResult result) {
            result.startTest(this);
            result.addError(this, t);
            result.endTest(this);
        }
    }
}
