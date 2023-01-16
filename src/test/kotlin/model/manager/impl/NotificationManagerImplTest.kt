package model.manager.impl

import model.InformativeNotification
import model.UrgentNotification
import model.observer.impl.User
import model.orservable.impl.Subject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class NotificationManagerImplTest {
    private lateinit var notificationManager: NotificationManagerImpl
    private val users = mutableListOf(User("user1"), User("user2"), User("user3"))
    private val subjects = mutableListOf(Subject("topic1"), Subject("topic2"), Subject("topic3"))
    @BeforeEach
    fun setUp() {
        this.notificationManager = NotificationManagerImpl(userList = users, subjectList = subjects)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun createUser() {
        val user = User("user4")
        notificationManager.createUser(user)
        assertTrue(users.contains(user))
    }

    @Test
    fun createTopic() {
        val topic = Subject("topic4")
        notificationManager.createSubject(topic)
        assertTrue(subjects.contains(topic))
    }

    @Test
    fun subscribeUserToTopic() {
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        notificationManager.subscribeUserToSubject(user, subject)
        assertTrue(subject.observers.contains(user))
    }

    @Test
    fun unsubscribeUserFromTopic() {
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        notificationManager.unsubscribeUserFromSubject(user, subject)
        assertFalse(subject.observers.contains(user))
    }

    @Test
    fun createInformativeNotification() {
        val subject = subjects.find { it.name == "topic1" }!!
        val notification = InformativeNotification("message")
        notificationManager.createNotification(subject, notification)
        assertTrue(subject.notifications.any { it.message == notification.message })
    }
    
    @Test
    fun userReceiveNotification(){
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        val notification = InformativeNotification("message")
        notificationManager.subscribeUserToSubject(user, subject)
        notificationManager.createNotification(subject, notification)
        assertTrue(user.notifications.any{ it.message == notification.message })
        assertFalse(user.notifications.last().seen)
        assertTrue(user.notifications.last().message == notification.message)
    }
    
    @Test
    fun createUrgentNotification(){
        val subjectObj = subjects.find { it.name == "topic1" }!!
        val notification = UrgentNotification("message")
        notificationManager.createNotification(subjectObj, notification)
        assertTrue(subjects.all { subject -> subject.notifications.any { it.message == notification.message } })
    }
    
    @Test 
    fun allUsersReceivedUrgentNotification(){
        val subject = subjects.find { it.name == "topic1" }!!
        val notification = UrgentNotification("message")
        notificationManager.createNotification(subject, notification)
        assertTrue(users.all { user -> user.notifications.any { it.message == notification.message } })
    }

    @Test
    fun userNotInSubject(){
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        notificationManager.unsubscribeUserFromSubject(user, subject)
        val notification = UrgentNotification("message")
        assertThrows(IllegalAccessException::class.java){
            notificationManager.createNotificationForUser(notification, user, subject)
        }
    }
    
    @Test
    fun createNotificationForUser() {
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        val notification = InformativeNotification("message")
        notificationManager.subscribeUserToSubject(user, subject)
        notificationManager.createNotificationForUser(notification, user, subject)
        assertTrue(user.notifications.any { it.message == notification.message })
    }
    
    @Test
    fun onlyOneUserHasNotification(){
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        val notification = InformativeNotification("message")
        subjects.forEach { 
            notificationManager.subscribeUserToSubject(user, it)
        }
        notificationManager.createNotificationForUser(notification, user, subject)
        var onlyOneUserHasNotification = true
        for (userItem in users){
            if(userItem.notifications.contains(notification) && userItem.name != "user1"){
                onlyOneUserHasNotification = false
                break
            }
        }
        assertTrue(onlyOneUserHasNotification)
    }
    //testear que solo ese usuario tenga la notificacion
    //testear error cuando el usuario no estpe en ese tema 
    

    @Test
    fun showUserNotifications() {
        val user = users.find { it.name == "user1" }!!
        val subject = subjects.find { it.name == "topic1" }!!
        val notification = InformativeNotification("message")
        notificationManager.subscribeUserToSubject(user, subject)
        notificationManager.createNotification(subject, notification)
        notificationManager.showUserNotifications(user)
        assertTrue(user.notifications.last().seen)
    }
}