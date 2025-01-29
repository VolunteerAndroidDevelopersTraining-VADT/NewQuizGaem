package ali.hrhera.login.di

import ali.hrhera.login.data.LoginRepo
import ali.hrhera.login.data.LoginRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LoginRepoProvider {

    @Provides
    @Singleton
    fun provideLoginRepo(): LoginRepo = LoginRepoImp()
}