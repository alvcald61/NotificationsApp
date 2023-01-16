package model

import java.time.LocalDate

class InformativeNotification(
    message: String,
    expirationDate: LocalDate? = null,
    seen: Boolean = false
) : Notification(
    message,
    expirationDate,
    seen
) {
    override fun printNotification() {
        println("Normal notification:")
        println("   ${this.message}")
        this.seen = true
    }

    override fun copy() : Notification{
        return InformativeNotification(message, expirationDate, seen)
    }
}