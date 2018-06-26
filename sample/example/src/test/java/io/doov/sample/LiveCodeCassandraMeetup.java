/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.sample;

import static com.datastax.driver.core.DataType.text;

import java.time.LocalDate;
import java.util.Collection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;

import io.doov.core.FieldInfo;
import io.doov.sample.model.Company;
import io.doov.sample.model.Country;
import io.doov.sample.model.EmailType;
import io.doov.sample.model.Language;
import io.doov.sample.model.Timezone;

/**
 * Create a keyspace before starting the live code <tt>
 * CREATE KEYSPACE meetup WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};
 * </tt>
 */
public class LiveCodeCassandraMeetup {

    public static void main(String[] args) {
    }

    static void execute(Statement statement) {
        try (Cluster cluster = cluster(); Session session = cluster.connect()) {
            session.execute(statement);
        }
    }

    static Cluster cluster() {
        return new Cluster.Builder().addContactPoint("localhost").withCodecRegistry(codecRegistry()).build();
    }

    static CodecRegistry codecRegistry() {
        final CodecRegistry registry = new CodecRegistry();
        registry.register(LocalDateCodec.instance);
        registry.register(new EnumNameCodec<>(Country.class));
        registry.register(new EnumNameCodec<>(EmailType.class));
        registry.register(new EnumNameCodec<>(Language.class));
        registry.register(new EnumNameCodec<>(Timezone.class));
        registry.register(new EnumNameCodec<>(Company.class));
        return registry;
    }

    static DataType cqlType(FieldInfo info) {
        if (String.class.equals(info.type())) {
            return text();
        } else if (Boolean.class.equals(info.type()) || Boolean.TYPE.equals(info.type())) {
            return DataType.cboolean();
        } else if (Integer.class.equals(info.type()) || Integer.TYPE.equals(info.type())) {
            return DataType.cint();
        } else if (Double.class.equals(info.type()) || Double.TYPE.equals(info.type())) {
            return DataType.cdouble();
        } else if (Float.class.equals(info.type()) || Float.TYPE.equals(info.type())) {
            return DataType.cfloat();
        } else if (Long.class.equals(info.type()) || Long.TYPE.equals(info.type())) {
            return DataType.cint();
        } else if (LocalDate.class.equals(info.type())) {
            return DataType.date();
        } else if (Enum.class.isAssignableFrom(info.type())) {
            return DataType.text();
        } else if (Collection.class.isAssignableFrom(info.type())) {
            return DataType.set(text());
        }
        throw new IllegalArgumentException("unknown type " + info.type() + " for " + info.id());
    }
}
