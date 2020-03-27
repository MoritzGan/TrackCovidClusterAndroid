package de.trackcovidcluster;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.trackcovidcluster.changeStatus.ChangeStatusActivity;

@Module(
  subcomponents =
      AndroidBuilderModule_ContributeChangeStatusActivity.ChangeStatusActivitySubcomponent.class
)
public abstract class AndroidBuilderModule_ContributeChangeStatusActivity {
  private AndroidBuilderModule_ContributeChangeStatusActivity() {}

  @Binds
  @IntoMap
  @ClassKey(ChangeStatusActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      ChangeStatusActivitySubcomponent.Builder builder);

  @Subcomponent
  public interface ChangeStatusActivitySubcomponent extends AndroidInjector<ChangeStatusActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ChangeStatusActivity> {}
  }
}
