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

import javax.inject.Inject;
import javax.inject.Named;

public class V8Engine extends GasEngine {

    public V8Engine() {
        publicNoArgsConstructorInjected = true;
    }

    @Inject void injectPackagePrivateMethod() {
        if (packagePrivateMethodInjected) {
            moreProblems.add("Overridden package private method injected twice");
        }
        packagePrivateMethodInjected = true;
    }

    /**
     * Qualifiers are swapped from how they appear in the superclass.
     */
    public void injectQualifiers(Seat seatA, @Drivers Seat seatB,
            Tire tireA, @Named("spare") Tire tireB) {
        if ((seatA instanceof DriversSeat)
                || !(seatB instanceof DriversSeat)
                || (tireA instanceof SpareTire)
                || !(tireB instanceof SpareTire)) {
            moreProblems.add("Qualifiers inherited from overridden methods");
        }
    }

    void injectPackagePrivateMethodForOverride() {
        packagePrivateMethodForOverrideInjected = true;
    }

    @Inject public void injectTwiceOverriddenWithOmissionInMiddle() {
        overriddenTwiceWithOmissionInMiddleInjected = true;
    }

    public void injectTwiceOverriddenWithOmissionInSubclass() {
        overriddenTwiceWithOmissionInSubclassInjected = true;
    }

    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        tester.test(publicNoArgsConstructorInjected, "Public no-args constructor not injected");
        tester.test(packagePrivateMethodInjected, "Packge private method not injected");
        tester.test(!packagePrivateMethodForOverrideInjected,
                "Package private method injected, even though its override lacks @Inject");

        tester.test(overriddenTwiceWithOmissionInMiddleInjected,
                "Twice-overridden method not injected; middle override lacks @Inject");
        tester.test(!overriddenTwiceWithOmissionInSubclassInjected,
                "Twice-overridden method injected, even though its override lacks @Inject");
    }
}
