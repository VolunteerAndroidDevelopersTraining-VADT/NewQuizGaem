package ali.hrhera.onboarding.data.di

import ali.hrhera.onboarding.data.OnBoardingRepo
import ali.hrhera.onboarding.data.OnBoardingRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OnboardingRepoProvider {

    @Provides
    @Singleton
    fun provideRepo(): OnBoardingRepo = OnBoardingRepoImp()
}