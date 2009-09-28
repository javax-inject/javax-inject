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

public class V8Engine extends Engine {

    @Inject void injectPackagePrivateMethod() {
        if (packagePrivateMethodInjected) {
            moreProblems.add("Overridden package private method injected twice");
        }
        packagePrivateMethodInjected = true;
    }

    void injectPackagePrivateMethodForOverride() {
        packagePrivateMethodForOverrideInjected = true;
    }

    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        tester.test(packagePrivateMethodInjected, "Packge private method not injected");
        tester.test(!packagePrivateMethodForOverrideInjected,
                "Package private method injected, even though its override lacks @Inject");
    }
}
