package ali.hrhera.auth.fatures.login

import ali.hrhera.auth.R
import ali.hrhera.auth.fatures.login.view.LoginFragment
import ali.hrhera.auth.fatures.login.view.LoginViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main, LoginFragment())
            .commit()

        viewModel.loginResponse.observe(this) {
            if (it.first) {
                setResult(RESULT_OK, Intent().apply {
                    putExtras(bundleOf("user" to it.second))
                })
                finish()
            }
        }

    }
}