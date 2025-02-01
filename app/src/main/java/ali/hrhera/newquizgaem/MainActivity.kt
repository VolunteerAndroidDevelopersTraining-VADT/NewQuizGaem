package ali.hrhera.newquizgaem

import ali.hrhera.auth.features.login.LoginActivity
import ali.hrhera.onboarding.ui.OnBoardingMainActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val startLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            // move to another activity or module
            viewModel.saveIsLogin(true)
        } else viewModel.saveIsLogin(false)

    }
    private val startOnBoarding = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.saveIsFirstTime(false)
    }
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.states.collectLatest {
                when (it) {
                    MainState.Idle -> {}
                    MainState.OpenLogin -> startLoginFlow()
                    MainState.OpenOnBoarding -> openOnBoarding()
                }
            }
        }


    }

    private fun openOnBoarding() {
        startOnBoarding.launch(
            Intent(
                this@MainActivity,
                OnBoardingMainActivity::class.java
            )
        )
    }

    private fun startLoginFlow() {
        startLogin.launch(
            Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
        )
    }
}