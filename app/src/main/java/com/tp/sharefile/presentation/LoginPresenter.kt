package com.tp.sharefile.presentation

import com.github.terrakok.cicerone.Router
import com.tp.sharefile.data.UserStorage
import com.tp.sharefile.navigation.Screens
import com.tp.sharefile.presentation.utils.RxPresenter
import com.tp.sharefile.ui.login.LoginView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val router: Router,
    private val userStorage: UserStorage
) : RxPresenter<LoginView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadLogin()?.let { viewState.setLogin(it) }
    }

    private fun loadLogin() = userStorage.loginName

    fun onClickLogin(login: String) {
        saveLogin(login)
        navigateToFiles()
    }

    fun pingServer() {

    }

    private fun saveLogin(login: String) {
        userStorage.loginName = login
    }

    private fun navigateToFiles() = router.navigateTo(Screens.filesScreen())
}