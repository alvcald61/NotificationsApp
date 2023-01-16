package model.orservable

import model.Notification
import model.observer.Observer

interface Observable {
    fun subscribe(observer: Observer)
    fun unsubscribe(observer: Observer)
    fun notifyObservers()
    fun registerNotification(notification: Notification)
}