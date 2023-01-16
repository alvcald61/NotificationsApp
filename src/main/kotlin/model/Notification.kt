package model

import java.time.LocalDate

abstract class Notification(
    val message: String,
    val expirationDate: LocalDate? = null,
    var seen: Boolean = false,
    val id: Int = generateId()
) {
    companion object {
        private var idCounter = 0
        fun generateId() = ++idCounter
    }
    fun isAvailableForUsers() : Boolean = !seen && expirationDate?.isAfter(LocalDate.now()) ?: true
    fun isValid() : Boolean =  expirationDate?.isAfter(LocalDate.now()) ?: true
    abstract fun printNotification()
    
    abstract fun copy(): Notification
}