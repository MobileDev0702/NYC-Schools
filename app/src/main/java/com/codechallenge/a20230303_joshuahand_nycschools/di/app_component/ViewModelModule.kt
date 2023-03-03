package com.codechallenge.a20230303_joshuahand_nycschools.di.app_component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codechallenge.a20230303_joshuahand_nycschools.view.school_list_activity.SchoolListViewModelImpl
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SchoolListViewModelImpl::class)
    internal abstract fun schoolListViewModelImpl(viewModel: SchoolListViewModelImpl): ViewModel

}