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

package org.atinject.tck;

import org.atinject.tck.auto.Car;
import org.atinject.tck.auto.Convertible;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

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
            delegate = new CarWontStart("getCar() threw an exception", t);
            return;
        }

        if (car == null) {
            delegate = new CarWontStart("getCar() returned null",
                    new NullPointerException("getCar() returned null"));
            return;
        }

        if (!(car instanceof Convertible)) {
            delegate = new CarWontStart(
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
     * Returns a {@link org.atinject.tck.auto.Car} constructed by an
     * injector with the following configuration:
     *
     * <ul>
     *   <li>{@link org.atinject.tck.auto.Car} is implemented by
     *       {@link org.atinject.tck.auto.Convertible Convertible}.
     *   <li>{@link org.atinject.tck.auto.Drivers @Drivers} {@link org.atinject.tck.auto.Seat Seat} is
     *       implemented by {@link org.atinject.tck.auto.DriversSeat DriversSeat}.
     *   <li>{@link org.atinject.tck.auto.Engine Engine} is implemented by
     *       {@link org.atinject.tck.auto.V8Engine V8Engine}.
     *   <li>{@link javax.inject.Named @Named("spare")} {@link org.atinject.tck.auto.Tire Tire} is implemented by
     *       {@link org.atinject.tck.auto.accessories.SpareTire SpareTire}.
     *   <li>The following concrete classes may also be injected: {@link org.atinject.tck.auto.accessories.Cupholder
     *       Cupholder}, {@link org.atinject.tck.auto.Tire Tire} and {@link org.atinject.tck.auto.FuelTank
     *       FuelTank}.
     * </ul>
     *
     * <p>The static members of the following types shall also be injected: {@link org.atinject.tck.auto.Convertible
     * Convertible}, {@link org.atinject.tck.auto.Tire Tire}, and {@link
     * org.atinject.tck.auto.accessories.SpareTire SpareTire}.
     */
    protected abstract Car getCar();
}
