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

/**
 * An injector with the following configuration:
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
public interface Candidate {

    /**
     * Returns an injected car instance.
     */
    public Car getCar();
}
