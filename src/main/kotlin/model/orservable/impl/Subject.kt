package model.orservable.impl

import model.Notification
import model.observer.Observer
import model.orservable.Observable

data class Subject(
    val name: String,
    val id: Int = generateId(),
    val observers: MutableList<Observer> = mutableListOf(),
    val notifications: MutableList<Notification> = mutableListOf()
) : Observable {

    companion object {
        private var idCounter = 0
        fun generateId() = ++idCounter
    }
    override fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }
    
    override fun notifyObservers() {
        observers.forEach{
            it.update(notifications.last().copy())
        }
    }

    override fun registerNotification(notification: Notification) {
        notifications.add(notification)
    }

    private fun getValidNotifications(): List<Notification> = notifications.filter { it.isValid() }
    
    fun showNotifications(){
        getValidNotifications()
            .reversed()
            .forEach { 
            it.printNotification()
        }
    }
}