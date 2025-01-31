package ali.hrhera.base.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideAppDataStore {

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context) = AppDataStore(context)
}