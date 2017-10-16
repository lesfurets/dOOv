package org.modelmap.sample.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumSet;

import org.modelmap.core.FieldModel;

public class SampleModels {

    public static SampleModel sample() {
        SampleModel model = new SampleModel();

        User user = new User();
        user.setId(1);
        user.setFirstName("Foo");
        user.setLastName("Bar");
        user.setBirthDate(LocalDate.of(1980, 8, 1));
        model.setUser(user);

        Account account = new Account();
        account.setId(9);
        account.setAcceptEmail(true);
        account.setEmail("foo@bar.com");
        account.setEmailTypes(EnumSet.of(EmailType.ADMINISTRATOR, EmailType.PRIVATE));
        account.setLanguage(Language.EN);
        account.setCountry(Country.UK);
        account.setLogin("foobar");
        account.setPassword("PqssW0rd");
        account.setPhoneNumber("+45102030409");
        account.setTimezone(Timezone.ETC_GMT);
        account.setMaxEmailSize(25);
        account.setTop3WebSite(Arrays.asList(
                        FavoriteWebsite.webSite("Google", "www.google.com"),
                        FavoriteWebsite.webSite("Bing", "www.bing.com"),
                        FavoriteWebsite.webSite("Yahoo", "www.yahoo.com")));
        model.setAccount(account);

        return model;
    }

    public static FieldModel wrapper() {
        return new SampleModelWrapper(sample());
    }
}
