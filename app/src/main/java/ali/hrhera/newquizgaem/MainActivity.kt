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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val startLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            // move to another activity or module
            viewModel.saveIsLogin(true)
        }

    }
    private val startOnBoarding = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.saveIsFirstTime(false)
        Log.w("TAG", "Test: ${it.resultCode} ")
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

        viewModel.states.observe(this) {
            Log.w("TAG", "onCreate: $it")
            if (it.first) {
                startOnBoarding.launch(
                    Intent(
                        this,
                        OnBoardingMainActivity::class.java
                    )
                )
                return@observe
            }
            if (!it.second) {
                startLogin.launch(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }
        }


    }
}