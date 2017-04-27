package com.comuto.flag;

import android.support.annotation.NonNull;
import android.util.Log;
import com.comuto.flag.model.Flag;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import dagger.Lazy;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * Downloads flags from server.
 */
public final class FlagsLoader {

    private static final String TAG = "FlagsLoader";

    private Lazy<OkHttpClient> lazyClient;
    private Gson gson;

    @Inject
    FlagsLoader(Lazy<OkHttpClient> lazyClient, Gson gson) {
        this.lazyClient = lazyClient;
        this.gson = gson;
    }

    void load(String url, @NonNull final FlagsCallback flagsCallback) throws Exception {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalStateException("Please pass a valid url");
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        lazyClient.get().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure() called with: e = [" + e + "]");
                flagsCallback.onError(e);
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
