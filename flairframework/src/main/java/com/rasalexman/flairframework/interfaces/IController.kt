package com.rasalexman.flairframework.interfaces

import com.rasalexman.flairframework.patterns.observer.Observer
import kotlin.reflect.full.createInstance

/**
 * Created by a.minkin on 21.11.2017.
 */
interface IController : IMultitonKey {
    val commandMap: MutableMap<String, ICommand?>
    val view: IView
}

/**
 * If an `ICommand` has previously been registered to handle a
 * the given `INotification`, then it is executed.
 *
 * @param notification
 * an `INotification`
 */
fun IController.executeCommand(notification: INotification) {
    this.commandMap[notification.name]?.execute(notification)
}

/**
 * Check if a Command is registered for a given Notification
 *
 * @param notificationName
 * @return whether a Command is currently registered for the given `notificationName`.
 */
fun IController.hasCommand(notificationName: String): Boolean = this.commandMap[notificationName] != null

/**
 * Remove a previously registered `ICommand` to
 * `INotification` mapping.
 *
 * @param notificationName
 * the name of the `INotification` to remove the
 * `ICommand` mapping for
 */
fun IController.removeCommand(notificationName: String) {
    // if the Command is registered...
    if (this.hasCommand(notificationName)) {
        // remove the observer
        this.view.removeObserver(notificationName, this)
        this.commandMap.remove(notificationName)
    }
}


/**
 * Register a particular `ICommand` class as the handler for a
 * particular `INotification`.
 *
 * @param notificationName
 * the name of the `INotification`
 */
inline fun <reified T : ICommand> IController.registerCommand(notificationName: String) {
    if (this.commandMap.containsKey(notificationName)) {
        return
    }
    this.view.registerObserver(notificationName, Observer(::executeCommand, this))
    this.commandMap.getOrPut(notificationName) {
        val inst = T::class.createInstance()
        // Only fresh commands can register observers
        if (inst.multitonKey.isEmpty()) {
            inst.multitonKey = this.multitonKey
        }
         inst
    }
}

