package model.observer

import model.Notification

interface Observer {
    fun addNotification(notification: Notification)
    fun update(notification: Notification)
}