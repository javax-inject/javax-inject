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

import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;

public class Tire {

    protected static final V8Engine NEVER_INJECTED = new V8Engine();

    protected final List<String> moreProblems = new ArrayList<String>();

    V8Engine constructorInjection = NEVER_INJECTED;
    @Inject V8Engine fieldInjection = NEVER_INJECTED;
    V8Engine methodInjection = NEVER_INJECTED;

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

    @Inject
    public Tire(V8Engine constructorInjection) {
        this.constructorInjection = constructorInjection;
    }

    @Inject void supertypeMethodInjection(V8Engine methodInjection) {
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

    protected boolean hasSpareTireBeenMethodInjected() {
        return false;
    }
}
