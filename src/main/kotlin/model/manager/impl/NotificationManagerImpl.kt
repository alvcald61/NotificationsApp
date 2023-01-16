package model.manager.impl

import model.InformativeNotification
import model.Notification
import model.manager.NotificationManager
import model.observer.impl.User
import model.orservable.impl.Subject


class NotificationManagerImpl(
    private val PRINTING_FORMAT: String = "%2d.    %s",
    private val userList: MutableList<User> = mutableListOf<User>(),
    private val subjectList: MutableList<Subject> = mutableListOf<Subject>(),

    ) : NotificationManager {
    
    
    override fun createUser(user: User) {
        userList.add(user)
        println("User created with id: ${user.id}")
    }

    override fun showUsers() {
        println("Showing available users: ")
        userList.forEach { println(String.format(PRINTING_FORMAT, it.id, it.name)) }
    }

    override fun createSubject(subject: Subject) {
        subjectList.add(subject)
        println("Subject created with id: ${subject.id}")
    }

    override fun showSubjects() {
        println("Showing available subjects:")
        subjectList.forEach{
            println(String.format(PRINTING_FORMAT, it.id, it.name))
        }
    }

    override fun subscribeUserToSubject(user: User, subject: Subject) {
        subject.subscribe(user)
        println("User ${user.name} register to subject ${subject.name}")
    }


    override fun unsubscribeUserFromSubject( user: User, subject: Subject) {
        subject.unsubscribe(user)
        println("User ${user.name} register to subject ${subject.name}")
    }

    override fun createNotification( subject: Subject, notification: Notification) {
        if(notification is InformativeNotification){
            subject.registerNotification(notification.copy())
            subject.notifyObservers()
        }
        else{
            subjectList.forEach { 
                it.notifications.add(notification.copy())
            }
            subject.registerNotification(notification.copy())
            userList.forEach { 
                it.update(notification.copy())
            }
        }
    }

    override fun createNotificationForUser( notification: Notification, user: User,subject: Subject) {
        validateUserInSubject(user, subject)
        user.update(notification.copy())
    }

    private fun validateUserInSubject(user: User, subject: Subject) {
        if(!subject.observers.contains(user)){
            throw IllegalAccessException("User ${user.name} is not register to subject ${subject.name}")
        }
    }


    override fun showUserNotifications( user: User) {
        user.watchNotifications()
    }

    override fun showSubjectNotification( subject: Subject) {
        subject.showNotifications()
    }

    override fun markNotificationAsRead(user: User, notification: Notification) {
        user.markNotificationAsRead(notification)
    }
}

