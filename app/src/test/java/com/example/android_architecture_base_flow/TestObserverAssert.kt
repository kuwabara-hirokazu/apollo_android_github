package com.example.android_architecture_base_flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import junit.framework.TestCase
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class TestObserverAssert<T>(private val target: LiveData<T>) {

    private var latch: CountDownLatch? = null
    private val values = mutableListOf<T>()
    private val observer = Observer<T> {
        values.add(it)
        latch?.countDown()
    }

    init {
        target.observeForever(observer)
    }

    fun await(
        count: Int = 1,
        timeout: Long = 1,
        unit: TimeUnit = TimeUnit.SECONDS
    ): TestObserverAssert<T> {
        val valueCount = values.size
        if (valueCount >= count) {
            return this
        }

        val latch = CountDownLatch(count - valueCount)
        this.latch = latch
        if (!latch.await(timeout, unit)) {
            throw TimeoutException()
        }
        return this
    }

    fun shouldReceive(expected: T): TestObserverAssert<T> {
        TestCase.assertEquals(expected, values.firstOrNull())
        return this
    }

    fun shouldReceiveAt(expected: T, at: Int): TestObserverAssert<T> {
        TestCase.assertEquals(expected, values[at])
        return this
    }

    fun withValueCount(expected: Int): TestObserverAssert<T> {
        TestCase.assertEquals(expected, values.size)
        return this
    }

    fun end() {
        target.removeObserver(observer)
    }
}

// LiveData => TestObserverAssertへ変換する便利関数
fun <T> LiveData<T>.testObserver(): TestObserverAssert<T> {
    return TestObserverAssert(this)
}
