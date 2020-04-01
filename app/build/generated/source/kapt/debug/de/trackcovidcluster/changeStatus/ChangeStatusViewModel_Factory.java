// Generated by Dagger (https://dagger.dev).
package de.trackcovidcluster.changeStatus;

import dagger.internal.Factory;
import de.trackcovidcluster.source.StatusStorageSource;
import de.trackcovidcluster.source.UserStorageSource;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ChangeStatusViewModel_Factory implements Factory<ChangeStatusViewModel> {
  private final Provider<UserStorageSource> mUserStorageSourceProvider;

  private final Provider<StatusStorageSource> mStatusStorageSourceProvider;

  public ChangeStatusViewModel_Factory(Provider<UserStorageSource> mUserStorageSourceProvider,
      Provider<StatusStorageSource> mStatusStorageSourceProvider) {
    this.mUserStorageSourceProvider = mUserStorageSourceProvider;
    this.mStatusStorageSourceProvider = mStatusStorageSourceProvider;
  }

  @Override
  public ChangeStatusViewModel get() {
    return newInstance(mUserStorageSourceProvider.get(), mStatusStorageSourceProvider.get());
  }

  public static ChangeStatusViewModel_Factory create(
      Provider<UserStorageSource> mUserStorageSourceProvider,
      Provider<StatusStorageSource> mStatusStorageSourceProvider) {
    return new ChangeStatusViewModel_Factory(mUserStorageSourceProvider, mStatusStorageSourceProvider);
  }

  public static ChangeStatusViewModel newInstance(UserStorageSource mUserStorageSource,
      StatusStorageSource mStatusStorageSource) {
    return new ChangeStatusViewModel(mUserStorageSource, mStatusStorageSource);
  }
}
