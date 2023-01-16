package model

import java.time.LocalDate

class UrgentNotification(
    message: String,
    expirationDate: LocalDate? = null,
    seen: Boolean = false
) : Notification(message, expirationDate, seen) {

    override fun printNotification() {
        println("Urgent notification:")
        println("   ${this.message}")
        this.seen = true
    }

    override fun copy() : Notification{
        return UrgentNotification(message, expirationDate, seen)
    }
}
