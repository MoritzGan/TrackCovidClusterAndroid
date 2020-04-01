// Generated by Dagger (https://dagger.dev).
package de.trackcovidcluster;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ApplicationModule_ProvideSharedPreferencesFactory implements Factory<SharedPreferences> {
  private final ApplicationModule module;

  private final Provider<Context> contextProvider;

  public ApplicationModule_ProvideSharedPreferencesFactory(ApplicationModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public SharedPreferences get() {
    return provideSharedPreferences(module, contextProvider.get());
  }

  public static ApplicationModule_ProvideSharedPreferencesFactory create(ApplicationModule module,
      Provider<Context> contextProvider) {
    return new ApplicationModule_ProvideSharedPreferencesFactory(module, contextProvider);
  }

  public static SharedPreferences provideSharedPreferences(ApplicationModule instance,
      Context context) {
    return Preconditions.checkNotNull(instance.provideSharedPreferences(context), "Cannot return null from a non-@Nullable @Provides method");
  }
}
