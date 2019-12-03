package com.vkholod.wizzair.tickets_bot.telegram;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class VovaTicketsBot {

    private String chatId;
    private HttpUrl baseUrl;
    private OkHttpClient client;

    public VovaTicketsBot(OkHttpClient client, String token, String chatId) {
        this.client = client;
        this.chatId = chatId;

        this.baseUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.telegram.org")
                .addPathSegment("bot" + token)
                .build();
    }

    public void sendMessage(String text) throws IOException {
        HttpUrl sendMessageUrl = baseUrl.newBuilder()
                .addPathSegment("sendMessage")
                .addQueryParameter("chat_id", chatId)
                .addQueryParameter("text", text)
                .build();

        doGet(sendMessageUrl);
    }

    public String getUpdates() throws IOException {
        HttpUrl getUpdatesUrl = baseUrl.newBuilder()
                .addPathSegment("getUpdates")
                .build();

        return doGet(getUpdatesUrl);
    }

    private String doGet(HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}
