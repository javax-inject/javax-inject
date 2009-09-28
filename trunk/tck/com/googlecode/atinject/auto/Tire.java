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

package com.googlecode.atinject.auto;

import com.googlecode.atinject.auto.accessories.SpareTire;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

public class Tire {

    protected static final FuelTank NEVER_INJECTED = new FuelTank();

    protected static final Set<String> moreProblems = new LinkedHashSet<String>();

    FuelTank constructorInjection = NEVER_INJECTED;
    @Inject FuelTank fieldInjection = NEVER_INJECTED;
    FuelTank methodInjection = NEVER_INJECTED;
    @Inject static FuelTank staticFieldInjection = NEVER_INJECTED;
    static FuelTank staticMethodInjection = NEVER_INJECTED;

    boolean constructorInjected;

    protected boolean superPrivateMethodInjected;
    protected boolean superPackagePrivateMethodInjected;
    protected boolean superProtectedMethodInjected;
    protected boolean superPublicMethodInjected;
    protected boolean subPrivateMethodInjected;
    protected boolean subPackagePrivateMethodInjected;
    protected boolean subProtectedMethodInjected;
    protected boolean subPublicMethodInjected;

    protected boolean superPrivateMethodForOverrideInjected;
    protected boolean superPackagePrivateMethodForOverrideInjected;
    protected boolean subPrivateMethodForOverrideInjected;
    protected boolean subPackagePrivateMethodForOverrideInjected;
    protected boolean protectedMethodForOverrideInjected;
    protected boolean publicMethodForOverrideInjected;

    @Inject public Tire(FuelTank constructorInjection) {
        this.constructorInjection = constructorInjection;
    }

    @Inject void supertypeMethodInjection(FuelTank methodInjection) {
        if (!hasTireBeenFieldInjected()) {
            moreProblems.add("Method injected before fields");
        }
        if (hasSpareTireBeenFieldInjected()) {
            moreProblems.add("Subtype field injected before supertype method");
        }
        if (hasSpareTireBeenMethodInjected()) {
            moreProblems.add("Subtype method injected before supertype method");
        }
        this.methodInjection = methodInjection;
    }

    @Inject static void supertypeStaticMethodInjection(FuelTank methodInjection) {
        if (!Tire.hasBeenStaticFieldInjected()) {
            moreProblems.add("Static method injected before static fields");
        }
        if (SpareTire.hasBeenStaticFieldInjected()) {
            moreProblems.add("Subtype static field injected before supertype static method");
        }
        if (SpareTire.hasBeenStaticMethodInjected()) {
            moreProblems.add("Subtype static method injected before supertype static method");
        }
        staticMethodInjection = methodInjection;
    }

    @Inject private void injectPrivateMethod() {
        if (superPrivateMethodInjected) {
            moreProblems.add("Overridden private method injected twice");
        }
        superPrivateMethodInjected = true;
    }

    @Inject void injectPackagePrivateMethod() {
        if (superPackagePrivateMethodInjected) {
            moreProblems.add("Overridden package private method injected twice");
        }
        superPackagePrivateMethodInjected = true;
    }

    @Inject protected void injectProtectedMethod() {
        if (superProtectedMethodInjected) {
            moreProblems.add("Overridden protected method injected twice");
        }
        superProtectedMethodInjected = true;
    }

    @Inject public void injectPublicMethod() {
        if (superPublicMethodInjected) {
            moreProblems.add("Overridden public method injected twice");
        }
        superPublicMethodInjected = true;
    }

    @Inject private void injectPrivateMethodForOverride() {
        subPrivateMethodForOverrideInjected = true;
    }

    @Inject void injectPackagePrivateMethodForOverride() {
        subPackagePrivateMethodForOverrideInjected = true;
    }

    @Inject protected void injectProtectedMethodForOverride() {
        protectedMethodForOverrideInjected = true;
    }

    @Inject public void injectPublicMethodForOverride() {
        publicMethodForOverrideInjected = true;
    }

    protected final boolean hasTireBeenFieldInjected() {
        return fieldInjection != NEVER_INJECTED;
    }

    protected boolean hasSpareTireBeenFieldInjected() {
        return false;
    }

    protected final boolean hasTireBeenMethodInjected() {
        return methodInjection != NEVER_INJECTED;
    }

    protected static boolean hasBeenStaticFieldInjected() {
        return staticFieldInjection != NEVER_INJECTED;
    }

    protected static boolean hasBeenStaticMethodInjected() {
        return staticMethodInjection != NEVER_INJECTED;
    }

    protected boolean hasSpareTireBeenMethodInjected() {
        return false;
    }
}
