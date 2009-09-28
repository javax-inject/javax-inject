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

import org.atinject.tck.auto.Seat;
import org.atinject.tck.Tester;

import javax.inject.Singleton;
import javax.inject.Provider;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;

@Singleton
public class Cupholder {

    protected final List<String> moreProblems = new ArrayList<String>();

    private final Provider<Seat> seatProvider;

    @Inject
    public Cupholder(Provider<Seat> seatProvider) {
        this.seatProvider = seatProvider;
    }

    public void check(Tester tester) {
        tester.addProblems(moreProblems);

        tester.test(seatProvider.get().getCupholder() == this, "Circularly dependent singletons not singleton");
    }
}
