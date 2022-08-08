package com.serbyn.currency_calculator.di;

import com.serbyn.currency_calculator.data.CurrencyRepositoryImpl
import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.currency_calculator.domain.dispatchers.CoroutinesDispatchersImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindCoroutineDispatchers(
            dispatcherImpl: CoroutinesDispatchersImpl
    ) : CoroutinesDispatchers

    @Binds
    abstract fun bindCurrencyRepository(
        repositoryImpl: CurrencyRepositoryImpl
    ) : CurrencyRepository

}
