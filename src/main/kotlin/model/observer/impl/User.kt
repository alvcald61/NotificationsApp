package model.observer.impl

import model.Notification
import model.observer.Observer

data class User(
    val name: String,
    val id: Int = generateId(),
    val notifications: MutableList<Notification> = mutableListOf()
) : Observer{
    companion object {
        private var idCounter = 0
        fun generateId() = ++idCounter
    }
    override fun update(notification: Notification) {
        println("User $id notification received")
        addNotification(notification)
    }

    override fun addNotification(notification: Notification) {
        notifications.add(notification)
    }
    
    private fun getValidNotifications(): List<Notification> = notifications.filter { it.isAvailableForUsers()}
    
    fun watchNotifications(){
        getValidNotifications()
            .forEach { 
                it.printNotification()
        }
    }

    override fun toString(): String {
        return this.id.toString()
    }
}
