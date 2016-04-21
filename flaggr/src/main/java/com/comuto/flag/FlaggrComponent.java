package com.comuto.flag;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { FlaggrModule.class })
public interface FlaggrComponent {
    void inject(Flaggr flaggr);

    final class Initializer {
        private Initializer() {}

        static FlaggrComponent init(Context appContext) {
            return DaggerFlaggrComponent.builder().flaggrModule(new FlaggrModule(appContext)).build();
        }
    }

}
