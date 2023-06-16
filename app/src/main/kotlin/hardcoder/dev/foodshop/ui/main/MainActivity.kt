package hardcoder.dev.foodshop.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import hardcoderdev.foodshop.app.R
import hardcoderdev.foodshop.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }
}