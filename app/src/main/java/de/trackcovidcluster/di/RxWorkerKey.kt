package de.trackcovidcluster.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class RxWorkerKey(val value: KClass<out ListenableWorker>)
