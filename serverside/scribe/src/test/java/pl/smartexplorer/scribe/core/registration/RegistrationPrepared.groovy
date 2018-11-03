package pl.smartexplorer.scribe.core.registration

import pl.smartexplorer.scribe.core.registration.dto.Registration

import java.time.LocalDateTime

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

class RegistrationPrepared {
    def userId = "9d8329dm23d"
    def authenticationType = "SCRIBE"
    def socialServiceId = "23902302"
    def socialUsername = "Mikołaj Kopernik"
    def username = "kopernik2018"
    def password = "mik.kop"
    def passwordRepeated = "mik.kop"
    def email = "mikolaj.kopernik@gmail.com"
    def joinDate = LocalDateTime.now().toString()

    Registration prepare() {
        Registration registration = new Registration()
        registration.setUserId(userId)
        registration.setAuthenticationType(authenticationType)
        registration.setSocialServiceId(socialServiceId)
        registration.setSocialUsername(socialUsername)
        registration.setUsername(username)
        registration.setPassword(password)
        registration.setPasswordRepeated(passwordRepeated)
        registration.setEmail(email)
        registration.setJoinDate(joinDate)
        return registration
    }

}
