package okunev.projects.easypeasytravel.core.di_hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okunev.projects.easypeasytravel.recognizer.data.repositories.RecognizerRepositoryImpl
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import okunev.projects.easypeasytravel.recognizer.domain.usecases.api.TakePhotoUseCase
import okunev.projects.easypeasytravel.recognizer.domain.usecases.api.TranslateTextUseCase
import okunev.projects.easypeasytravel.recognizer.domain.usecases.impl.TakePhotoUseCaseImpl
import okunev.projects.easypeasytravel.recognizer.domain.usecases.impl.TranslateTextUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecognizerModule {

    @Binds
    abstract fun provideRepo(impl: RecognizerRepositoryImpl): RecognizerRepository

    @Binds
    abstract fun provideTakePhotoUseCase(impl: TakePhotoUseCaseImpl): TakePhotoUseCase

    @Binds
    abstract fun provideTranslateTextUseCase(impl: TranslateTextUseCaseImpl): TranslateTextUseCase
}