package com.comuto.flag;

import android.content.Context;
import android.content.SharedPreferences;

import com.comuto.flag.operators.Operator;
import com.comuto.flag.parsing.CustomJsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static android.content.Context.MODE_PRIVATE;

@Module
public class FlaggrModule {

    protected static final String SHARED_PREF_SUFFIX = "_flaggr_prefs";

    private final Context applicationContext;

    public FlaggrModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides public Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Operator.class, new CustomJsonDeserializer());
        return gson.create();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context appContext) {
        return appContext.getSharedPreferences(appContext.getPackageName().concat(SHARED_PREF_SUFFIX), MODE_PRIVATE);
    }

}
