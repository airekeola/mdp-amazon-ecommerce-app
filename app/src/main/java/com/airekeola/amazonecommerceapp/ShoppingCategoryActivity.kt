package com.airekeola.amazonecommerceapp

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airekeola.amazonecommerceapp.databinding.ActivityShoppingCategoryBinding
import com.airekeola.amazonecommerceapp.model.User

class ShoppingCategoryActivity : AppCompatActivity() {
    private lateinit var layout:ActivityShoppingCategoryBinding
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        layout = ActivityShoppingCategoryBinding.inflate(layoutInflater)
        setContentView(layout.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
    }

    private fun initialize(){
        user = intent.getSerializableExtra("user") as User;
        layout.welcomeText.text = getString(R.string.welcome, user.name);
    }

    public fun onImageBtnClick(view: View){
        val btn = view as ImageButton;
        Toast.makeText(this, btn.contentDescription, Toast.LENGTH_SHORT).show()
    }
}
