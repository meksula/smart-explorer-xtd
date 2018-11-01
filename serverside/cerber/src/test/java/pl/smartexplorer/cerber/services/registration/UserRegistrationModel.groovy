package pl.smartexplorer.cerber.services.registration

import pl.smartexplorer.cerber.model.user.AuthenticationType
import pl.smartexplorer.cerber.model.user.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.time.LocalDateTime

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

class UserRegistrationModel {
    public static password = "kopernik"
    def authenticationType
    def socialServiceId
    def socialUsername
    def username
    def encryptedPassword
    def email
    def joinDate

    static User buildUser() {
        User user = new User()
        user.setAuthenticationType(AuthenticationType.SCRIBE.name())
        user.socialServiceId = "5532f43f4f43f43f4f34d3hbr"
        user.socialUsername = "Mikołaj Kopernik"
        user.username = "mikolajkopernik"
        user.encryptedPassword = new BCryptPasswordEncoder().encode(password)
        user.email = "kopernik.mikolaj@gmail.com"
        user.joinDate = LocalDateTime.now()
        return user
    }

}
