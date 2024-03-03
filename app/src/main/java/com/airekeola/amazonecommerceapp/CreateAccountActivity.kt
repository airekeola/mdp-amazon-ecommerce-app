package com.airekeola.amazonecommerceapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airekeola.amazonecommerceapp.databinding.ActivityCreateAccountBinding
import com.airekeola.amazonecommerceapp.model.User

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var layout: ActivityCreateAccountBinding
    private lateinit var emails: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // bind to layout
        layout = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(layout.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
    }

    private fun initialize(){
        // add event handlers
        layout.continueBtn.setOnClickListener(this::onContinueBtnClick)

        emails = intent.getStringArrayExtra("emails") as Array<String>;
    }

    private fun onContinueBtnClick(view:View){
        val name = layout.nameText.text.toString()
        val email = layout.emailText.text.toString()
        val password= layout.passwordText.text.toString()
        val password2 = layout.passwordText2.text.toString()

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()){
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_LONG).show()
        }else if(emails.contains(email)){
            Toast.makeText(this, "Email '$email' already exist.", Toast.LENGTH_SHORT).show()
        }else{
            val user = User(name, email, password);
            val returnIntent = Intent()
            returnIntent.putExtra("newUser", user)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        super.onBackPressed()
    }
}
