package de.trackcovidcluster.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.source.StatusStorageSource
import de.trackcovidcluster.source.UserStorageSource
import de.trackcovidcluster.worker.GetStatusWorker

@Suppress("unused")
@Module
internal abstract class WorkerModule {

    @Binds
    @IntoMap
    @RxWorkerKey(GetStatusWorker::class)
    abstract fun bindWorkerFactory(
        getStatusWorker: GetStatusWorker.Factory
    ): IChildRxWorkerFactory


    @Binds
    abstract fun bindUserStorageSource(
        userStorageSource: UserStorageSource
    ): IUserStorageSource

    @Binds
    internal abstract fun bindStatusStorageSource(
        statusStorageSource: StatusStorageSource
    ): IStatusStorageSource
}
