package com.serbyn.currency_calculator

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestWatcher() {

    private val testCoroutinesScope: TestScope = TestScope()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(
            testCoroutinesScope.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        )
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}