package com.vkholod.wizzair.tickets_bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.*;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WizClient {

    private OkHttpClient client;
    private ObjectReader reader;
    private ObjectWriter writer;

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    private static final HttpUrl TIMETABLE_URL = buildTimetableUrl();

    public WizClient() {
        this.client = new OkHttpClient();
        this.client.setConnectTimeout(10, TimeUnit.SECONDS);
        this.client.setReadTimeout(30, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.reader = mapper.readerFor(Timetable.class);
        this.writer = mapper.writerFor(TimetableRequestDto.class);
    }

    public Timetable getTimetable(TimetableRequestDto timetableRequestDto) throws IOException {
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
                .addPathSegment("10.1.0")
                .addPathSegment("Api")
                .addPathSegment("search")
                .addPathSegment("timetable")
                .build();
    }
}
