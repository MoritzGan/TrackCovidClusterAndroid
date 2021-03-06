// Generated by Dagger (https://dagger.dev).
package de.trackcovidcluster.main;

import dagger.internal.Factory;
import de.trackcovidcluster.source.UserStorageSource;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainActivityViewModel_Factory implements Factory<MainActivityViewModel> {
  private final Provider<UserStorageSource> mUserStorageSourceProvider;

  public MainActivityViewModel_Factory(Provider<UserStorageSource> mUserStorageSourceProvider) {
    this.mUserStorageSourceProvider = mUserStorageSourceProvider;
  }

  @Override
  public MainActivityViewModel get() {
    return newInstance(mUserStorageSourceProvider.get());
  }

  public static MainActivityViewModel_Factory create(
      Provider<UserStorageSource> mUserStorageSourceProvider) {
    return new MainActivityViewModel_Factory(mUserStorageSourceProvider);
  }

  public static MainActivityViewModel newInstance(UserStorageSource mUserStorageSource) {
    return new MainActivityViewModel(mUserStorageSource);
  }
}
