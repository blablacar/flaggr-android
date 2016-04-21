package com.comuto.flag;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.comuto.flag.model.Flag;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Downloads flags from server.
 */
public final class FlagsLoader {

    private static final String TAG = "FlagsLoader";

    private OkHttpClient client;
    private Gson gson;

    @Inject
    public FlagsLoader(OkHttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    void load(String url, @NonNull final FlagsCallback flagsCallback) throws Exception {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalStateException("Please pass a valid url");
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure() called with: e = [" + e + "]");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                Type listType = new TypeToken<List<Flag>>() {}.getType();
                final String jsonStr = response.body().string();
                try {
                    List<Flag> flags = gson.fromJson(jsonStr, listType);
                    flagsCallback.onLoadFlags(jsonStr, flags);
                } catch (JsonSyntaxException exception) {
                    throw new IOException("JSON Bad Format " + exception.getMessage());
                }
            }
        });
    }

}
