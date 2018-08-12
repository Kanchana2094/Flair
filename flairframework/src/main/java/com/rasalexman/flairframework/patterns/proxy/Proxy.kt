package com.rasalexman.flairframework.patterns.proxy

import com.rasalexman.flairframework.interfaces.IProxy
import com.rasalexman.flairframework.patterns.observer.Notifier

/**
 * Created by a.minkin on 21.11.2017.
 * @param data - stored data
 */
abstract class Proxy<T>(override var data: T?) : Notifier(), IProxy<T?> {

    /**
     * Called by the Model when the Proxy is registered.
     */
    override fun onRegister() {}

    /**
     * Called by the Model when the Proxy is removed.
     */
    override fun onRemove() {}
}