package net.pro.comtam.presentation.profile


sealed class ProfileEvent {
    data class EnterUsername (val value: String): ProfileEvent()
    data class EnteredPhoneNumber(val value: String) : ProfileEvent()
    data class EnteredWard(val value: String) : ProfileEvent()
    data class EnteredStreet(val value: String) : ProfileEvent()
    data class EnteredHouseNumber(val value: String) : ProfileEvent()
    data class SaveProfile(val onSave: () -> Unit) : ProfileEvent()
}
