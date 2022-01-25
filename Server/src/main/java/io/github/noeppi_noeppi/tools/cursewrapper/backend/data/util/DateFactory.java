package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import javax.annotation.Nullable;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

// Because CurseForge sometimes sends dates without a time zone
// First tries ISO-8601 and if it fails, tries ISO-8601 without time zone
public class DateFactory implements TypeAdapterFactory {
    
    @Override
    @Nullable
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType() == Date.class) {
            //noinspection unchecked
            return (TypeAdapter<T>) DateAdapter.INSTANCE;
        } else {
            return null;
        }
    }
    
    private static class DateAdapter extends TypeAdapter<Date> {
        
        public static final DateAdapter INSTANCE = new DateAdapter();
        
        private static final SimpleDateFormat CURSE_ALTERNATIVE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
        private DateAdapter() {
            
        }

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            out.value(ISO8601Utils.format(value));
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String dateStr = in.nextString();
            try {
                return ISO8601Utils.parse(dateStr, new ParsePosition(0));
            } catch (ParseException e) {
                return CURSE_ALTERNATIVE_FORMAT.parse(dateStr, new ParsePosition(0));
            }
        }
    }
}
