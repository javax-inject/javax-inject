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

public abstract class Engine {
    
    protected final List<String> moreProblems = new ArrayList<String>();

    protected boolean packagePrivateMethodInjected;
    protected boolean packagePrivateMethodForOverrideInjected;

    @Inject void injectPackagePrivateMethod() {
        moreProblems.add("Unexpected call to supertype package private method");
    }

    @Inject void injectPackagePrivateMethodForOverride() {
        moreProblems.add("Unexpected call to supertype package private method");
    }
}
