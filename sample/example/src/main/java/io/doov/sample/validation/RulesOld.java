/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.sample.validation;

import static java.time.temporal.ChronoUnit.YEARS;

import java.time.LocalDate;

import io.doov.sample.model.*;

public class RulesOld {

    public static boolean validateEmail(Account account) {
        if (account == null) {
            return true;
        }
        if (account.getEmail() == null) {
            return true;
        }
        if (account.getEmail().matches("\\w+[@]\\w+\\.com")) {
            return true;
        }
        if (account.getEmail().matches("\\w+[@]\\w+\\.fr")) {
            return true;
        }
        return false;
    }

    public static boolean validateAccount(User user, Account account, Configuration config) {
        if (config == null) {
            return false;
        }
        if (user == null || user.getBirthDate() == null) {
            return false;
        }
        if (account == null || account.getCountry() == null || account.getPhoneNumber() == null) {
            return false;
        }
        if (YEARS.between(user.getBirthDate(), LocalDate.now()) >= 18
                && account.getEmail().length() <= config.getMaxEmailSize()
                && account.getCompany() == Company.LES_FURETS
                && account.getPhoneNumber().startsWith("+33")) {
            return true;
        }
        return false;
    }

}
