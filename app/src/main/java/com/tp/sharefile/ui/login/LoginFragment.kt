package com.tp.sharefile.ui.login

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tp.sharefile.R
import com.tp.sharefile.databinding.LoginFragmentBinding
import com.tp.sharefile.di.appComponent
import com.tp.sharefile.di.presentation.LoginComponent
import com.tp.sharefile.presentation.LoginPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginFragment : MvpAppCompatFragment(R.layout.login_fragment), LoginView {
    private val binding: LoginFragmentBinding by viewBinding()

    @Inject
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter =
        LoginComponent.build(requireContext().appComponent).providePresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loginButton.setOnClickListener { presenter.onClickLogin(login.text.toString()) }
        }
    }

    override fun setLogin(login: String) {
        with(binding) {
            this.login.setText(login)
        }
    }
}