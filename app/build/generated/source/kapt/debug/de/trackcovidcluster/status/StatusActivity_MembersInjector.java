// Generated by Dagger (https://dagger.dev).
package de.trackcovidcluster.status;

import androidx.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import dagger.internal.InjectedFieldSignature;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class StatusActivity_MembersInjector implements MembersInjector<StatusActivity> {
  private final Provider<ViewModelProvider.Factory> mViewModelFactoryProvider;

  public StatusActivity_MembersInjector(
      Provider<ViewModelProvider.Factory> mViewModelFactoryProvider) {
    this.mViewModelFactoryProvider = mViewModelFactoryProvider;
  }

  public static MembersInjector<StatusActivity> create(
      Provider<ViewModelProvider.Factory> mViewModelFactoryProvider) {
    return new StatusActivity_MembersInjector(mViewModelFactoryProvider);}

  @Override
  public void injectMembers(StatusActivity instance) {
    injectMViewModelFactory(instance, mViewModelFactoryProvider.get());
  }

  @InjectedFieldSignature("de.trackcovidcluster.status.StatusActivity.mViewModelFactory")
  public static void injectMViewModelFactory(StatusActivity instance,
      ViewModelProvider.Factory mViewModelFactory) {
    instance.mViewModelFactory = mViewModelFactory;
  }
}
