package model

import java.time.LocalDate

class InformativeNotification(
    message: String,
    expirationDate: LocalDate? = null,
    seen: Boolean = false, 
    id: Int? = null
) : Notification(
    message,
    expirationDate,
    seen
) {

    constructor(notification: Notification) :
            this(message = notification.message, expirationDate = notification.expirationDate,
                seen = notification.seen, id = notification.id){

    }
    override fun printNotification() {
        println("Informative notification:")
        println("   ${this.message}")
//        this.seen = true
    }

    override fun copy() : Notification{
        return InformativeNotification(this)
    }
}