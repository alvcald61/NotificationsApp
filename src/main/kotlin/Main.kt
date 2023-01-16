import model.InformativeNotification
import model.Notification
import model.UrgentNotification
import model.manager.impl.NotificationManagerImpl
import model.observer.impl.User
import model.orservable.impl.Subject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val PRINTING_FORMAT = "%2d.    %s"
val options = createOptions()

fun main(args: Array<String>) {
    val userList: MutableList<User> = mutableListOf<User>()
    val subjectList: MutableList<Subject> = mutableListOf<Subject>()
    val notificationManager = NotificationManagerImpl()
    while (true) {
        printManu(options)
        try {
            when (readln().toInt()) {
                1 -> notificationManager.createUser(getNewUser())
                2 -> notificationManager.showUsers()
                3 -> notificationManager.createSubject(getNewSubject())
                4 -> notificationManager.showSubjects()
                5 -> notificationManager.subscribeUserToSubject(
                    getUserFromConsole(userList),
                    getSubjectFromConsole(subjectList)
                )

                6 -> notificationManager.unsubscribeUserFromSubject(
                    getUserFromConsole(userList),
                    getSubjectFromConsole(subjectList)
                )

                7 -> notificationManager.createNotification(
                    getSubjectFromConsole(subjectList),
                    getNotificationFromConsole()
                )

                8 -> notificationManager.createNotificationForUser(
                    getNotificationFromConsole(),
                    getUserFromConsole(userList),
                    getSubjectFromConsole(subjectList)
                )

                9 -> notificationManager.showUserNotifications(getUserFromConsole(userList))
                10 -> notificationManager.showSubjectNotification(getSubjectFromConsole(subjectList))
                11 -> break
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
    println("----------------------------------------------------------------")
}

fun getNewSubject(): Subject {
    println("Enter subject name: ")
    val subjectName = readln()
    return Subject(subjectName)
}

fun createOptions(): MutableMap<Int, String> {
    val options = mutableMapOf<Int, String>()
    options[options.size + 1] = "Create User"
    options[options.size + 1] = "Show Users"
    options[options.size + 1] = "Create Topic"
    options[options.size + 1] = "Show Topics"
    options[options.size + 1] = "Subscribe User to Topic"
    options[options.size + 1] = "Unsubscribe User to Topic"
    options[options.size + 1] = "Create Notification to Topic"
    options[options.size + 1] = "Create Notification to User"
    options[options.size + 1] = "Show Notifications from User"
    options[options.size + 1] = "Show Notifications from Topic"
    options[options.size + 1] = "Exit"
    return options
}

fun printManu(options: Map<Int, String>) {
    println("----------------------------------------------------------------")
    println("Main menu:")
    println("Enter the number of the chosen option:")
    options.forEach {
        println(String.format(PRINTING_FORMAT, it.key, it.value))
    }
    println("----------------------------------------------------------------")
}

fun getNewUser(): User {
    print("Enter user name: ")
    val name = readln()
    return User(name)

}

private fun getSubjectFromConsole(subjectList: List<Subject>): Subject {
    println("Enter subject id:")
    val subjectId = readln().toInt()
    return findSubjectInList(subjectId, subjectList)
}

private fun findUserInList(userId: Int, userList: List<User>): User {
    return userList.find { it.id == userId } ?: throw Exception("User not found")
}

private fun findSubjectInList(subjectId: Int, subjectList: List<Subject>): Subject {
    return subjectList.find { it.id == subjectId } ?: throw Exception("Subject not found")
}

private fun getUserFromConsole(userList: List<User>): User {
    println("Enter user id:")
    val userId = readln().toInt()
    return findUserInList(userId, userList)
}

private fun getNotificationFromConsole(): Notification {
    println("Enter type of notification: (1: informative, 2: Urgent")
    val notificationType = readln().toInt()
    println("Enter message of the notification")
    val message = readln()
    println("Enter the expiration date of notification in the format: day-month-year")
    println("If you don't want to enter a date leave it empty")
    val stringDate = readln()
    var date: LocalDate? = null
    if (stringDate.isNotEmpty()) {
        date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }
    return if (notificationType == 1) InformativeNotification(message, date) else UrgentNotification(message, date)
}