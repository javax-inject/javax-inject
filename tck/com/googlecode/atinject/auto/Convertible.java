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

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Convertible implements Car {

    private static final V8Engine NEVER_INJECTED = new V8Engine();

    @Inject V8Engine engineA = NEVER_INJECTED;

    @Inject DriversSeat driversSeatA;
    @Inject DriversSeat driversSeatB;

    private boolean constructorInjected = false;
    private boolean methodsInjected = false;

    private List<String> moreProblems = new ArrayList<String>();


    @Inject Convertible(Seat a, Seat b, Seat c) {
        if (methodsInjected) {
            moreProblems.add("Expected constructor to be injected before methods");
        }
        if (engineA != NEVER_INJECTED) {
            moreProblems.add("Expected constructor to be injected before fields");
        }
        constructorInjected = true;
    }

    Convertible() {}

    @Inject void injectSomeStuff(Seat a, Seat b) {
        if (methodsInjected) {
            moreProblems.add("Expected methods to be injected only once");
        }
        if (engineA == NEVER_INJECTED) {
            moreProblems.add("Expected fields to be injected before methods");
        }
        methodsInjected = true;
    }

    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        // values are injected
        tester.test(constructorInjected, "Expected constructor to be injected");
        tester.test(methodsInjected, "Expected methods to be injected");
        tester.test(engineA != NEVER_INJECTED, "Expected fields to be injected");

        // singleton is not @Inherited
        tester.test(driversSeatA != driversSeatB, "Expected @Singleton to not be inherited");
    }
}
