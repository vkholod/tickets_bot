package com.vkholod.wizzair.tickets_bot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_FORMATTER;

public class FlightDateDeserializer extends StdDeserializer<LocalDate> {

    protected FlightDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(parser.readValueAs(String.class), DATE_FORMATTER);
    }
}
