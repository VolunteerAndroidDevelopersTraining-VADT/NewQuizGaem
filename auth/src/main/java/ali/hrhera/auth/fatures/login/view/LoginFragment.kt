package ali.hrhera.auth.fatures.login.view

import ali.hrhera.auth.R
import ali.hrhera.auth.databinding.FragmentLoginBinding
import ali.hrhera.auth.fatures.register.RegisterActivity
import ali.hrhera.base.BaseDataVmFragment
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseDataVmFragment<FragmentLoginBinding, LoginViewModel>() {
    private val viewModel: LoginViewModel by activityViewModels()
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
    }

    override fun getBaseViewModel(): LoginViewModel = viewModel
    override fun afterBind() {
        binding.login.setOnClickListener {
            binding.email.setText("ali@test.dev")
            binding.password.setText("123456789")
            viewModel.startLogin(
                binding.email.text?.toString(),
                binding.password.text?.toString()
            )
        }
        binding.gotoRegister.setOnClickListener {
            requireActivity().startActivity(
                Intent(requireActivity(), RegisterActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            )
            requireActivity().finish()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
        }

    }
}