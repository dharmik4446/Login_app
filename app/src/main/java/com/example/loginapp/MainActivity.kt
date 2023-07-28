package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signup: Button = findViewById(R.id.sign_up)
        signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        textView  = findViewById(R.id.Log_in)
        val text="already have an account? Log in"

        val spannableString= SpannableString(text)
        val clickablespan: ClickableSpan= object : ClickableSpan(){
            override fun onClick(p0: View) {
                val intent1 = Intent(this@MainActivity,LogInActivity::class.java)
                startActivity(intent1)
//                Toast.makeText(this@MainActivity,"clicked successfully",Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickablespan,25,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(spannableString, TextView.BufferType.SPANNABLE)
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
