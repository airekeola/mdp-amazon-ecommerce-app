package com.airekeola.amazonecommerceapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airekeola.amazonecommerceapp.databinding.ActivityMainBinding
import com.airekeola.amazonecommerceapp.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var layout: ActivityMainBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var users: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // bind to layout
        layout = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layout.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.initialize()
    }

    private fun initialize() {
        // add event handlers
        layout.createAccountBtn.isClickable = true
        layout.createAccountBtn.setOnClickListener(this::onCreateAccountBtnClick)

        layout.signInBtn.isClickable = true;
        layout.signInBtn.setOnClickListener(this::onSignInBtnClick)

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    val newUser = intent?.getSerializableExtra("newUser") as User;
                    // Use the result
                    users.add(newUser);

                    Toast.makeText(this, "Account created successfully.", Toast.LENGTH_LONG).show()
                }
            }

        users = mutableListOf(User("John Doe", "john@gmail.com", "12345"))
    }

    private fun onCreateAccountBtnClick(view: View) {
        val intent = Intent(this, CreateAccountActivity::class.java)
        val existingEmails = users.map { u -> u.email }.toTypedArray()
        intent.putExtra("emails", existingEmails);
        startForResult.launch(intent)
    }

    private fun onSignInBtnClick(view: View) {
        val email = layout.emailText.text.toString()
        val password = layout.passwordText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_LONG).show()
        } else if (!users.any { u ->
                u.email.equals(
                    email,
                    ignoreCase = true
                ) && u.password == password
            }) {
            Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_LONG).show()
        } else {
            val user = users.first { u ->
                u.email.equals(
                    email,
                    ignoreCase = true
                ) && u.password == password
            };

            val intent = Intent(this, ShoppingCategoryActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }
}
