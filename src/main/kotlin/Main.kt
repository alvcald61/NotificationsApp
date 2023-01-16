import model.InformativeNotification
import model.Notification
import model.UrgentNotification
import model.manager.NotificationManager
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
    val notificationManager = NotificationManagerImpl(userList = userList, subjectList = subjectList)
    initLists(userList, subjectList, notificationManager)
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
                    getNewNotification()
                )

                8 -> notificationManager.createNotificationForUser(
                    getNewNotification(),
                    getUserFromConsole(userList),
                    getSubjectFromConsole(subjectList)
                )

                9 -> showNotificationUserMenu(notificationManager, userList)
                10 -> showNotificationSubjectMenu(notificationManager, subjectList)
                11 -> markNotificationAsRead(notificationManager, userList)
                12 -> break
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
    println("----------------------------------------------------------------")
}

fun markNotificationAsRead(notificationManager: NotificationManagerImpl, userList: MutableList<User>) {
    val user = getUserFromConsole(userList)
    notificationManager.markNotificationAsRead(user, getNotificationFromConsole(user))
}


fun showNotificationSubjectMenu(notificationManager: NotificationManagerImpl, subjectList: MutableList<Subject>) {
    notificationManager.showSubjects()
    println("Select the subject by typing the id:")
    notificationManager.showSubjectNotification(getSubjectFromConsole(subjectList))
}

fun initLists(
    userList: MutableList<User>,
    subjectList: MutableList<Subject>, 
    notificationManager: NotificationManager
) {
    userList.addAll(
        listOf(
            User("Jan Kowalski"),
            User("Adam Nowak"),
            User("Piotr Kowalski"),
            User("Krzysztof Nowak"),
            User("Jan Nowak"),
        )
    )
    subjectList.addAll(
        listOf(
            Subject("Math"),
            Subject("Physics"),
            Subject("Chemistry"),
            Subject("Biology"),
            Subject("History"),
        )
    )
    notificationManager.subscribeUserToSubject(userList[0], subjectList[0])
    notificationManager.subscribeUserToSubject(userList[1], subjectList[0])
    notificationManager.subscribeUserToSubject(userList[2], subjectList[0])
    notificationManager.subscribeUserToSubject(userList[3], subjectList[0])
    notificationManager.subscribeUserToSubject(userList[4], subjectList[0])
    notificationManager.subscribeUserToSubject(userList[0], subjectList[1])
    notificationManager.subscribeUserToSubject(userList[2], subjectList[1])
    notificationManager.subscribeUserToSubject(userList[2], subjectList[2])
    notificationManager.subscribeUserToSubject(userList[3], subjectList[3])
    notificationManager.subscribeUserToSubject(userList[4], subjectList[4])
    notificationManager.subscribeUserToSubject(userList[4], subjectList[2])
}

fun showNotificationUserMenu(notificationManager: NotificationManager, userList: List<User>) {
    println("Select the user by typing the id:")
    notificationManager.showUserNotifications(getUserFromConsole(userList))
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
    options[options.size + 1] = "Mark User Notification as Read"
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
    if(name.isEmpty()) throw Exception("Name cannot be empty")
    return User(name)

}

private fun getSubjectFromConsole(subjectList: List<Subject>): Subject {
    subjectList.forEach {
        println(String.format(PRINTING_FORMAT, it.id, it.name))
    }
    println("Enter subject id:")
    val subjectId = readln().toIntOrNull() ?: throw Exception("Id must be a number")
    return findSubjectInList(subjectId, subjectList)
}

private fun findUserInList(userId: Int, userList: List<User>): User {
    return userList.find { it.id == userId } ?: throw Exception("User not found")
}

private fun findSubjectInList(subjectId: Int, subjectList: List<Subject>): Subject {
    return subjectList.find { it.id == subjectId } ?: throw Exception("Subject not found")
}

private fun getUserFromConsole(userList: List<User>): User {
    userList.forEach {
        println(String.format(PRINTING_FORMAT, it.id, it.name))
    }
    println("Enter user id:")
    val userId = readln().toIntOrNull()?: throw Exception("Id must be a number")
    return findUserInList(userId, userList)
}

fun getNotificationFromConsole(user: User): Notification{
    println("Select the notification by typing the id:")
    user.notifications.forEach {
        println(String.format(PRINTING_FORMAT, it.id, it.message))
    }
    val notificationId = readln().toIntOrNull()?: throw Exception("Id must be a number")
    return user.notifications.find { it.id == notificationId }?: throw Exception("Notification not found")
}

private fun getNewNotification(): Notification {
    println("Enter type of notification: (1: informative, 2: Urgent)")
    val notificationType = readln().toIntOrNull()?: throw Exception("Type must be a number")
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

//fun showUserSelectionMenu(notificationManager: NotificationManager) {
//    notificationManager.showUsers()
//    println("Select the user by typing the id:")
//    
//}
//
//fun showSubjectSelectionMenu(notificationManager: NotificationManager){
//    notificationManager.showSubjects()
//    println("Select the subject by typing the id:")
//    
//}
//
