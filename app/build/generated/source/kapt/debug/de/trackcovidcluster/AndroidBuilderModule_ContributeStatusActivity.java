package de.trackcovidcluster;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.trackcovidcluster.status.StatusActivity;

@Module(
  subcomponents = AndroidBuilderModule_ContributeStatusActivity.StatusActivitySubcomponent.class
)
public abstract class AndroidBuilderModule_ContributeStatusActivity {
  private AndroidBuilderModule_ContributeStatusActivity() {}

  @Binds
  @IntoMap
  @ClassKey(StatusActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      StatusActivitySubcomponent.Builder builder);

  @Subcomponent
  public interface StatusActivitySubcomponent extends AndroidInjector<StatusActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<StatusActivity> {}
  }
}
