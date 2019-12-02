package com.vkholod.wizzair.tickets_bot.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_FORMATTER;

public class FlightDateSerializer extends StdSerializer<LocalDate> {

    protected FlightDateSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeString(value.format(DATE_FORMATTER));
    }
}
