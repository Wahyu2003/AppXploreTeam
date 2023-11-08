package com.Try.MyApps.ui.profile
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseAuth
//
//class ProfileViewModel(private val firebase: FirebaseAuth) : ViewModel() {
//    private val _profileImageUrl = MutableLiveData<String>()
//    val profileImageUrl: LiveData<String> get() = _profileImageUrl
//    // Fungsi untuk mendapatkan data pengguna yang sedang login
//    fun getLoggedInUser(userId: Int): LiveData<> {
//        return firebaseAuth.currentUser(userId)
//    }
//
//    fun setProfileImageUrl(url: String) {
//        _profileImageUrl.value = url
//    }
//
//}
