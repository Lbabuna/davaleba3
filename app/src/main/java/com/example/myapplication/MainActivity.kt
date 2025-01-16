import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.util.Patterns
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.text
import androidx.core.content.ContextCompat
import androidx.glance.visibility

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        registerButton.setOnClickListener {
            registerUser()
        }

        loginButton.setOnClickListener {
            loginUser()
        }

        checkUserLoginStatus()
    }

    private fun registerUser() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = getString(R.string.invalid_email_format)
            return
        }
        if (password.length < 6) {
            passwordInput.error = getString(R.string.password_too_short)
            return
        }

        showLoading()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                hideLoading()
                if (task.isSuccessful) {
                    saveUserLoginStatus(true)
                    Toast.makeText(this, getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
                    navigateToSecondActivity()
                } else {
                    Toast.makeText(this,
                        ContextCompat.getString(R.string.registration_failed, task.exception?.message), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginUser() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = getString(R.string.invalid_email_format)
            return
        }
        if (password.length < 6) {
            passwordInput.error = getString(R.string.password_too_short)
            return
        }

        showLoading()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                hideLoading()
                if (task.isSuccessful) {
                    saveUserLoginStatus(true)
                    Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                    navigateToSecondActivity()
                } else {
                    Toast.makeText(this,
                        ContextCompat.getString(R.string.login_failed, task.exception?.message), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
        registerButton.isEnabled = false
        loginButton.isEnabled = false
    }

    private fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
        registerButton.isEnabled = true
        loginButton.isEnabled = true
    }

    private fun saveUserLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    private fun checkUserLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            navigateToSecondActivity()
        }
    }

    private fun navigateToSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun registerUser() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = getString(R.string.invalid_email_format)
            return
        }
        if (!isValidPassword(password)) {
            passwordInput.error = "Password must contain at least 6 characters, one uppercase, one lowercase, one number and one special character"
            return
        }

        showLoading()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                hideLoading()
                if (task.isSuccessful) {
                    saveUserLoginStatus(true)
                    Toast.makeText(this, getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
                    navigateToSecondActivity()
                } else {
                    Toast.makeText(this,
                        ContextCompat.getString(R.string.registration_failed, task.exception?.message), Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun loginUser() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = getString(R.string.invalid_email_format)
            return
        }
        if (!isValidPassword(password)) {
            passwordInput.error = "Password must contain at least 6 characters, one uppercase, one lowercase, one number and one special character"
            return
        }

        showLoading()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                hideLoading()
                if (task.isSuccessful) {
                    saveUserLoginStatus(true)
                    Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                    navigateToSecondActivity()
                } else {
                    Toast.makeText(this,
                        ContextCompat.getString(R.string.login_failed, task.exception?.message), Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$".toRegex()
        return passwordPattern.matches(password)
    }
    private fun loginUser() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = getString(R.string.invalid_email_format)
            return
        }
        if (!isValidPassword(password)) {
            passwordInput.error = "Password must contain at least 6 characters, one uppercase, one lowercase, one number and one special character"
            return
        }

        showLoading()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                hideLoading()
                if (task.isSuccessful) {
                    saveUserLoginStatus(true)
                    Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                    navigateToSecondActivity()
                } else {
                    Toast.makeText(this,
                        ContextCompat.getString(R.string.login_failed, task.exception?.message), Toast.LENGTH_SHORT).show()
                }
            }
    }
}