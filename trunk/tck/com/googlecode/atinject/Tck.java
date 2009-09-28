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


package com.googlecode.atinject;

import com.googlecode.atinject.auto.Car;

public class Tck {

    private final Candidate candidate;

    public Tck(Candidate candidate) {
        this.candidate = candidate;
    }

    private boolean testCompatibility() {
        Tester tester = new Tester();

        Car car;
        try {
            car = candidate.getCar();
        } catch (Throwable e) {
            System.out.println("FAIL!");
            e.printStackTrace();
            return false;
        }

        if (car == null) {
            tester.addProblem("Null returned from Candidate.getCar()");
        } else {
            car.check(tester);
        }

        if (!tester.hasProblems()) {
            System.out.println("SUCCESS!");
            return true;
        }

        System.out.println("FAIL!");
        for (String problem : tester.problems()) {
            System.out.println(problem);
        }
        return false;
    }

    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (args.length != 1) {
            System.out.println("Usage: Tck <candidate>");
            return;
        }

        Class<?> c = Class.forName(args[0]);
        Candidate candidate = (Candidate) c.newInstance();
        boolean success = new Tck(candidate).testCompatibility();
        System.exit(success ? 0 : 1);
    }
}
