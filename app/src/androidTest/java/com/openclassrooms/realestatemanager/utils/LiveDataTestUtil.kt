package com.openclassrooms.realestatemanager.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtil {
    @Throws(InterruptedException::class)
    inline fun <reified T> getValue(liveData: LiveData<T>): T? {
        val data = arrayOfNulls<T>(1)
        val latch = CountDownLatch(1)
        val observer: Observer<T?> = object : Observer<T?> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return data[0]
    }
}