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

package org.atinject.tck.auto.accessories;

import org.atinject.tck.Tester;
import org.atinject.tck.auto.FuelTank;
import org.atinject.tck.auto.Tire;

import javax.inject.Inject;

public class SpareTire extends Tire {

    FuelTank constructorInjection = NEVER_INJECTED;
    @Inject FuelTank fieldInjection = NEVER_INJECTED;
    FuelTank methodInjection = NEVER_INJECTED;
    @Inject static FuelTank staticFieldInjection = NEVER_INJECTED;
    static FuelTank staticMethodInjection = NEVER_INJECTED;

    @Inject public SpareTire(FuelTank forSupertype, FuelTank forSubtype) {
        super(forSupertype);
        this.constructorInjection = forSubtype;
    }

    @Inject void subtypeMethodInjection(FuelTank methodInjection) {
        if (!hasSpareTireBeenFieldInjected()) {
            moreProblems.add("Methods injected before fields");
        }
        this.methodInjection = methodInjection;
    }

    @Inject static void subtypeStaticMethodInjection(FuelTank methodInjection) {
        if (!hasBeenStaticFieldInjected()) {
            moreProblems.add("Static methods injected before static fields");
        }
        staticMethodInjection = methodInjection;
    }

    @Inject private void injectPrivateMethod() {
        if (subPrivateMethodInjected) {
            moreProblems.add("Overridden private method injected twice");
        }
        subPrivateMethodInjected = true;
    }

    @Inject void injectPackagePrivateMethod() {
        if (subPackagePrivateMethodInjected) {
            moreProblems.add("Overridden package private method injected twice");
        }
        subPackagePrivateMethodInjected = true;
    }

    @Inject protected void injectProtectedMethod() {
        if (subProtectedMethodInjected) {
            moreProblems.add("Overridden protected method injected twice");
        }
        subProtectedMethodInjected = true;
    }

    @Inject public void injectPublicMethod() {
        if (subPublicMethodInjected) {
            moreProblems.add("Overridden public method injected twice");
        }
        subPublicMethodInjected = true;
    }

    private void injectPrivateMethodForOverride() {
        superPrivateMethodForOverrideInjected = true;
    }

    void injectPackagePrivateMethodForOverride() {
        superPackagePrivateMethodForOverrideInjected = true;
    }

    protected void injectProtectedMethodForOverride() {
        protectedMethodForOverrideInjected = true;
    }

    public void injectPublicMethodForOverride() {
        publicMethodForOverrideInjected = true;
    }

    protected boolean hasSpareTireBeenFieldInjected() {
        return fieldInjection != NEVER_INJECTED;
    }

    protected boolean hasSpareTireBeenMethodInjected() {
        return methodInjection != NEVER_INJECTED;
    }

    public static boolean hasBeenStaticFieldInjected() {
        return staticFieldInjection != NEVER_INJECTED;
    }

    public static boolean hasBeenStaticMethodInjected() {
        return staticMethodInjection != NEVER_INJECTED;
    }

    /**
     * This class is used to check inheritance. By existing in a separate package
     * from Tire, it can make sure the injector does the right thing with overrides
     * of package-private methods.
     */
    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        tester.test(hasSpareTireBeenFieldInjected(), "Subtype fields not injected");
        tester.test(hasSpareTireBeenMethodInjected(), "Subtype methods not injected");
        tester.test(hasTireBeenFieldInjected(), "Supertype fields not injected");
        tester.test(hasTireBeenMethodInjected(), "Supertype methods not injected");
        tester.test(SpareTire.hasBeenStaticFieldInjected(), "Subtype static fields not injected");
        tester.test(SpareTire.hasBeenStaticMethodInjected(), "Subtype static methods not injected");
        tester.test(Tire.hasBeenStaticFieldInjected(), "Supertype static fields not injected");
        tester.test(Tire.hasBeenStaticMethodInjected(), "Supertype static methods not injected");

        tester.test(superPrivateMethodInjected, "Supertype private method not injected");
        tester.test(superPackagePrivateMethodInjected, "Supertype private method not injected");
        tester.test(!superProtectedMethodInjected, "Overridden protected method injected!");
        tester.test(!superPublicMethodInjected, "Overridden public method injected!");
        tester.test(subPrivateMethodInjected, "Subtype private method not injected");
        tester.test(subPackagePrivateMethodInjected, "Subtype private method not injected");
        tester.test(subProtectedMethodInjected, "Subtype protected method not injected");
        tester.test(subPublicMethodInjected, "Subtype public method not injected");

        tester.test(subPrivateMethodForOverrideInjected,
                "Private method not injected when a subtype has a similar method that lacks @Inject");
        tester.test(subPackagePrivateMethodForOverrideInjected,
                "Package private method not injected when a subtype has a similar method that lacks @Inject");
        tester.test(!superPrivateMethodForOverrideInjected,
                "Private method injected when a supertype has a similar method annotated @Inject");
        tester.test(!superPackagePrivateMethodForOverrideInjected,
                "Package private method injected when a supertype has a similar method annotated @Inject");
        tester.test(!protectedMethodForOverrideInjected,
                "Protected method injected, even though its override lacks @Inject");
        tester.test(!publicMethodForOverrideInjected,
                "Public method injected, even though its override lacks @Inject");
    }
}
