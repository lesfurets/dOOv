package io.doov.core.dsl;

import static io.doov.core.dsl.DOOV.map;
import static io.doov.core.dsl.DOOV.when;
import static io.doov.core.dsl.mapping.DefaultBiTypeConverter.converter;
import static io.doov.core.dsl.mapping.DefaultGenericTypeConverter.nConverter;
import static io.doov.core.dsl.mapping.DefaultStaticTypeConverter.converter;
import static io.doov.core.dsl.mapping.DefaultTypeConverter.converter;
import static io.doov.sample.field.SampleFieldIdInfo.*;
import static io.doov.sample.model.SampleModels.sample;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.mapping.DefaultMappingRegistry;
import io.doov.sample.field.SampleFieldId;
import io.doov.sample.model.*;

public class DOOVMappingTest {

    private static final TypeConverter<String, String, SampleFieldId> STRIPPING_COUNTRY_CODE =
            converter(in -> in.startsWith("+33") ? "0" + in.substring(3, in.length()) : in, "",
                    "stripping country code");

    private static final TypeConverter<String, Integer, SampleFieldId> LENGTH_OR_ZERO =
            converter(in -> in.map(String::length).orElse(0), "string length");

    private static final BiTypeConverter<String, String, String, SampleFieldId> FULL_NAME =
            converter((i, j) -> i + " " + j, "", "", "[firstName lastName]");

    private static final BiTypeConverter<Collection<EmailType>, String, String, SampleFieldId> CONVERTER = converter((i, j) -> {
        String[] em = j.split("@");
        return em[0] + "+" + i.size() + "@" + em[1];
    }, "", "WTF");

    private static final GenericTypeConverter<Integer, SampleFieldId> EMAIL_SIZE = nConverter((model, fieldInfos) ->
            (int) fieldInfos.stream()
                    .map(f -> model.get((SampleFieldId) f.id()))
                    .filter(Objects::nonNull).count(), "favorite web site size -> email size");

    private MappingRegistry<SampleFieldId, SampleFieldId> mappings;

    private static <T> StaticStepMap<T, SampleFieldId> mapValue(Supplier<T> supplier) {
        return map(supplier);
    }

    @BeforeEach
    void setUp() {
        mappings = DefaultMappingRegistry.<SampleFieldId, SampleFieldId>createMappingRegistry().mappings(
                when(accountLanguage().eq(Language.FR)).then(
                        map(accountPhoneNumber()).using(STRIPPING_COUNTRY_CODE).to(accountPhoneNumber())),
                map(accountId()).to(configurationMaxLong()),
                map(userFirstName()).using(LENGTH_OR_ZERO).to(configurationMinAge()),
                map(userId()).to(userId()),
                map(userFirstName(), userLastName()).using(FULL_NAME).to(userFirstName()),
                when(accountAcceptEmail().isTrue()).then(
                        map(accountPreferencesMail(), accountEmail()).using(CONVERTER).to(accountEmail())),
                map(favoriteSiteName1(), favoriteSiteName2(), favoriteSiteName3())
                        .using(EMAIL_SIZE).to(configurationMaxEmailSize()),
                mapValue(() -> Country.FR)
                        .using(converter(this::countryToLanguage,""))
                                .to(accountLanguage()),
                when(accountLogin().isNotNull()).then(
                                mapValue(() -> true).to(accountAcceptEmail()))
        );
    }

    private Language countryToLanguage(Country c) {
        switch (c) {
            case US:
            case UK:
                return Language.EN;
            case CAN:
            case FR:
                return Language.FR;
        }
        return null;
    }

    @Test
    void doov() {
        SampleModelWrapper sample = new SampleModelWrapper(sample());
        SampleModelWrapper copy = new SampleModelWrapper();
        mappings.stream().forEachOrdered(m -> m.executeOn(sample, copy));
        assertThat(copy.getModel().getConfiguration().getMaxLong()).isEqualTo(9);
        assertThat(copy.getModel().getConfiguration().getMinAge()).isEqualTo(3);
        assertThat(copy.getModel().getAccount().getPhoneNumber()).isEqualTo("0102030409");
        assertThat(copy.getModel().getUser().getFirstName()).isEqualTo("Foo BAR");
        assertThat(copy.getModel().getAccount().getEmail()).isEqualTo("foo+2@bar.com");
        assertThat(copy.getModel().getConfiguration().getMaxEmailSize()).isEqualTo(3);
        assertThat(copy.getModel().getAccount().getLanguage()).isEqualTo(Language.FR);
        assertThat(copy.getModel().getAccount().getAcceptEmail()).isTrue();
        copy.stream().forEach(e -> System.out.println(e.getKey().name() + " : " + String.valueOf(e.getValue())));
    }
}
