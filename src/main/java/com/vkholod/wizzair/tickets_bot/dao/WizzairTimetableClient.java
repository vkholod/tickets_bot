package com.vkholod.wizzair.tickets_bot.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.*;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;

import java.io.IOException;

public class WizzairTimetableClient {

    private OkHttpClient client;
    private ObjectReader reader;
    private ObjectWriter writer;

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    private static final HttpUrl TIMETABLE_URL = buildTimetableUrl();

    public WizzairTimetableClient(ObjectMapper mapper, OkHttpClient client) {
        this.client = client;

        this.reader = mapper.readerFor(Timetable.class);
        this.writer = mapper.writerFor(TimetableRequestDto.class);
    }

    public Timetable fetchTimetable(TimetableRequestDto timetableRequestDto) throws IOException {
        String payload = writer.writeValueAsString(timetableRequestDto);

        Request request = new Request.Builder()
                .url(TIMETABLE_URL)
                .post(RequestBody.create(JSON_MEDIA_TYPE, payload))
                .build();

        Response response = client.newCall(request).execute();

        String respBody = response.body().string();

        return reader.readValue(respBody);
    }

    private static HttpUrl buildTimetableUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("be.wizzair.com")
                .addPathSegment("10.2.0")
                .addPathSegment("Api")
                .addPathSegment("search")
                .addPathSegment("timetable")
                .build();
    }
}
