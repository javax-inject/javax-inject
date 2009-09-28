package com.googlecode.atinject.meta;

import com.googlecode.atinject.Tck;
import com.googlecode.atinject.Tester;
import com.googlecode.atinject.auto.Car;

import junit.framework.Test;
import junit.framework.TestSuite;

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
        TestSuite suite = new TestSuite();
        suite.addTest(new ThrowsException());
        suite.addTest(new ReturnsNull());
        suite.addTest(new WrongType());
        return suite;
    }
}
