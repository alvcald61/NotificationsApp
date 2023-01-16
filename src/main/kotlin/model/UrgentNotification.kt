package model

import java.time.LocalDate

class UrgentNotification(
    message: String,
    expirationDate: LocalDate? = null,
    seen: Boolean = false,
    id: Int? = null
) : Notification(message, expirationDate, seen) {

    constructor(notification: Notification) : 
            this(message = notification.message, expirationDate = notification.expirationDate, 
                seen = notification.seen, id = notification.id){
                
            }
    
    
    override fun printNotification() {
        println("Urgent notification:")
        println("   ${this.message}")
//        this.seen = true
    }

    override fun copy() : Notification{
        return UrgentNotification(this)
    }
}
