package training.squads.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import training.squads.fitnessapp.databinding.ActivityLoginBinding
import training.squads.fitnessapp.utilities.SharedPreferences

val sharedPreferences: SharedPreferences by lazy {
    LoginActivity.sharedPreferences!!
}

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    companion object {
        var sharedPreferences: SharedPreferences? = null
        lateinit var instance: LoginActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this
        sharedPreferences = SharedPreferences(applicationContext)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}