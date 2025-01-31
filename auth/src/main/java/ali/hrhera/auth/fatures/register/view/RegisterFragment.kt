package ali.hrhera.auth.fatures.register.view

import ali.hrhera.auth.R
import ali.hrhera.auth.databinding.FragmentRegisterBinding
import ali.hrhera.auth.fatures.login.LoginActivity
import ali.hrhera.base.BaseDataVmFragment
import ali.hrhera.base.showDoneToast
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseDataVmFragment<FragmentRegisterBinding, RegisterViewModel>() {
    private val viewModel: RegisterViewModel by activityViewModels()
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
    }

    override fun getBaseViewModel(): RegisterViewModel = viewModel
    override fun afterBind() {
        binding.register.setOnClickListener {
            viewModel.startRegisterNewUser(
                email = binding.email.text.toString(),
                password = binding.password.text.toString(),
                phone = binding.phone.text.toString(),
                userName = binding.name.text.toString()
            )
        }

        viewModel.registerResponse.observe(this) {
            if (it) {
                showDoneToast("register successfully, please login") {
                    requireActivity().startActivity(
                        Intent(requireActivity(), LoginActivity::class.java)
                    )
                    requireActivity().finish()
                }
            }
        }

    }
}