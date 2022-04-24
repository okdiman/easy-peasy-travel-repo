package okunev.projects.easypeasytravel.core.di_hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okunev.projects.easypeasytravel.recognizer.data.repositories.RecognizerRepositoryImpl
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecognizerRepositoryModule {

    @Binds
    abstract fun provideRepo(impl: RecognizerRepositoryImpl): RecognizerRepository
}