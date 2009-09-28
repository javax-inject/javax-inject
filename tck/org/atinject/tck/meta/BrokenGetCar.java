package org.atinject.tck.meta;

import org.atinject.tck.Tck;
import org.atinject.tck.Tester;
import org.atinject.tck.auto.Car;

import junit.framework.Test;
import junit.framework.TestResult;

/**
 * We expect these tests to fail.
 */
public class BrokenGetCar {

    static class ThrowsException extends Tck {
        protected Car getCar() {
            throw new UnsupportedOperationException();
        }
    }

    static class ReturnsNull extends Tck {
        protected Car getCar() {
            return null;
        }
    }

    static class WrongType extends Tck {
        protected Car getCar() {
            return new Car() {
                public void check(Tester tester) {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    public static Test suite() {
        return new Test() {
            public int countTestCases() {
                return 3;
            }
            public void run(TestResult result) {
                new ThrowsException().run(result);
                new ReturnsNull().run(result);
                new WrongType().run(result);
            }
        };
    }
}
