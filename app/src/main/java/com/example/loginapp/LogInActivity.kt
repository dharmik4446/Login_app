package com.example.loginapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText


@Suppress("DEPRECATION", "KotlinConstantConditions")
class LogInActivity : AppCompatActivity() {
    lateinit var loginBtn: AppCompatButton
    lateinit var emailValidator: TextInputEditText
    lateinit var Password: TextInputEditText
    lateinit var facebook: ImageView
    lateinit var twitter: ImageView
    lateinit var google: ImageView
    lateinit var signup: TextView
    lateinit var forgotpass: TextView
    lateinit var sharePreference: SharedPreferences
    lateinit var popmailid: TextInputEditText
    lateinit var newpassword: TextInputEditText
    lateinit var ok: AppCompatButton
    lateinit var cancel: AppCompatButton
    lateinit var db: DbHelper

    @SuppressLint("MissingInflatedId", "CutPasteId")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        var newPassword: String = ""
        db= DbHelper(this)
//-------------clickable links----------
        signup = findViewById(R.id.sign_up)
        val text = "Don't have an account? Sign Up"
        val spannableString = SpannableString(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(this@LogInActivity, SignupActivity::class.java)
                startActivity(intent)
            }
        }
        spannableString.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        signup.setText(spannableString, TextView.BufferType.SPANNABLE)
        signup.movementMethod = LinkMovementMethod.getInstance()


//        shared pref
        sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val regMailid = sharePreference.getString("EMAIL", "")
        var regPassword = sharePreference.getString("PASSWORD", "")



//        ---forgot password
        forgotpass = findViewById(R.id.forgotpass)
        val clickedColor = resources.getColor(R.color.clicked_color)
        val originalColor = resources.getColor(R.color.original_color)


        forgotpass.setOnClickListener {
            forgotpass.setTextColor(clickedColor)
            forgotpass.postDelayed({
                forgotpass.setTextColor(originalColor)
            }, 500)

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.popup)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))


            ok = dialog.findViewById(R.id.ok)
            cancel = dialog.findViewById(R.id.cancel)
            popmailid = dialog.findViewById(R.id.popemail)
            newpassword = dialog.findViewById(R.id.password_toggle)

            ok.setOnClickListener {
                if (popmailid.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@LogInActivity, "enter valid mail id", Toast.LENGTH_LONG)
                        .show()
                    popmailid.error = "enter mail id"
                    return@setOnClickListener
                }
                if (popmailid.text.toString().trim() != regMailid) {
                    Toast.makeText(this@LogInActivity, "enter valid mail id", Toast.LENGTH_LONG)
                        .show()
                    popmailid.error = "enter valid mail id"
                    return@setOnClickListener

                }
                if (newpassword.text.toString().isEmpty()) {
                    Toast.makeText(this@LogInActivity, "enter new password", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener

                }
                if (newpassword.text.toString().equals(regPassword)) {
                    Toast.makeText(
                        this@LogInActivity,
                        "enter new stronger password",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                } else {
                    newPassword = newpassword.text.toString().trim()
                    regPassword = newPassword
                    db.updatePassword(popmailid.toString(),newPassword)
                    Toast.makeText(
                        this@LogInActivity,
                        "New password set successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    dialog.dismiss()
                }
            }

            cancel.setOnClickListener {
                dialog.dismiss()

            }
            dialog.show()
        }

        //--------app link-----------
        facebook = findViewById(R.id.FaceBook)
        facebook.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/”+Dharmik_Malaviya"))
            startActivity(intent)
        }
        twitter = findViewById(R.id.Twitter)
        twitter.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.Twitter.com/”+@DharmikMalavi10"))
            startActivity(intent)
        }
        google = findViewById(R.id.Google)
        google.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.Google.com/”"))
            startActivity(intent)
        }


        //----------validation--------
        emailValidator = findViewById(R.id.email)
        Password = findViewById(R.id.password_toggle)
        loginBtn = findViewById(R.id.loginbtn)

        loginBtn.setOnClickListener {
            val email = emailValidator.text.toString().trim()
            val password = Password.text.toString().trim()
            val emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.[a-z]+"

            if (email.isEmpty()) {
                emailValidator.error = "Email required"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Password.error = "password required"
                return@setOnClickListener
            }
            if (email != regMailid) {
                emailValidator.error = "enter valid mail"
            }
            if (password != regPassword || password != newPassword) {
                Password.error = "enter valid password"
            }
            if (email == regMailid && password == regPassword || password == newPassword) {
                val intent2 = Intent(this@LogInActivity, Home::class.java)
                startActivity(intent2)
            } else {
                return@setOnClickListener
            }
        }
    }
}

