package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.CurseEnum;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumFactory implements TypeAdapterFactory {

    private final Map<Class<?>, EnumAdapter<?>> instances = new HashMap<>();
    
    @Override
    @Nullable
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType().isEnum() && CurseEnum.class.isAssignableFrom(type.getRawType())) {
            //noinspection unchecked,rawtypes
            return (TypeAdapter<T>) this.instances.computeIfAbsent(type.getRawType(), cls -> new EnumAdapter<>((Class<Enum>) cls));
        } else {
            return null;
        }
    }
    
    private static class EnumAdapter<T extends Enum<T>> extends TypeAdapter<T> {

        private final Class<T> cls;
        private final List<T> values;
        private final T defaultValue;

        private EnumAdapter(Class<T> cls) {
            this.cls = cls;
            this.values = Arrays.stream(cls.getEnumConstants()).toList();
            T defaultValue = null;
            try {
                // Don't hardcode this to 0 as CF is inconsistent
                defaultValue = Enum.valueOf(cls, "OTHER");
            } catch (IllegalArgumentException e) {
                //
            }
            this.defaultValue = defaultValue;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            out.value(value.ordinal());
        }

        @Override
        public T read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return this.defaultValue;
            } else {
                int idx = in.nextInt();
                if (idx > 0 && idx < this.values.size()) {
                    return this.values.get(idx);
                } else if (this.defaultValue != null) {
                    return this.defaultValue;
                } else {
                    throw new JsonParseException("Invalid enum constant " + idx + " for " + this.cls.getSimpleName());
                }
            }
        }
    }
}
