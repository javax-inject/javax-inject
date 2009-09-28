package org.atinject.tck;

import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * Used when we can't even get a Car to test against.
 */
class CarWontStart extends TestCase {
    /*
     * We have to extend TestCase instead of implementing Test so we
     * can set the name. JUnit sucks (or at least the tools do).
     */

    final Throwable t;

    CarWontStart(String name, Throwable t) {
        setName(name);
        this.t = t;
    }

    @Override public int countTestCases() {
        return 1;
    }

    @Override public void run(TestResult result) {
        result.startTest(this);
        result.addError(this, t);
        result.endTest(this);
    }
}
