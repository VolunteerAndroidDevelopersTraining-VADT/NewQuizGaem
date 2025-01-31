package ali.hrhera.auth.di

import ali.hrhera.auth.data.AuthRepo
import ali.hrhera.auth.data.AuthRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthRepoProvider {

    @Provides
    @Singleton
    fun provideRepo(): AuthRepo = AuthRepoImp()
}