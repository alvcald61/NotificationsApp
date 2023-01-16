package model.manager

import model.Notification
import model.observer.impl.User
import model.orservable.impl.Subject

interface NotificationManager {
   fun createUser(user: User)
   fun showUsers()
   fun createSubject(subject: Subject)
   fun showSubjects()
   fun subscribeUserToSubject(user: User, subject: Subject)
   fun unsubscribeUserFromSubject(user: User, subject: Subject)
   fun createNotification(subject: Subject, notification: Notification)
   fun createNotificationForUser(notification: Notification, user: User, subject: Subject)
   fun showUserNotifications(user: User)
   fun  showSubjectNotification(subject: Subject)
}