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

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class Tester {
    private final List<String> problems = new ArrayList<String>();

    /**
     * @param problem a brief description of what went wrong.
     */
    public void addProblem(String problem) {
        problems.add(problem);
    }

    /**
     * Adds a problem if {@code condition} is not true.
     *
     * @param problem a brief description of what went wrong.
     */
    public void test(boolean condition, String problem) {
        if (!condition) {
            problems.add(problem);
        }
    }

    public boolean hasProblems() {
        return !problems.isEmpty();
    }

    public Iterable<String> problems() {
        return problems;
    }

    public void addProblems(Collection<String> problems) {
        this.problems.addAll(problems);
    }
}
