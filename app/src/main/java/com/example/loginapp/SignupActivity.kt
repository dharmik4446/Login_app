package com.example.loginapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText

@Suppress("LABEL_NAME_CLASH")
class SignupActivity : AppCompatActivity() {
    lateinit var userName: TextInputEditText
    lateinit var enterdpassword: TextInputEditText
    lateinit var signupbtn: AppCompatButton
    lateinit var facebook: ImageView
    lateinit var twitter: ImageView
    lateinit var google: ImageView
    lateinit var textView: TextView
    lateinit var emailValidator: TextInputEditText
    lateinit var sharePreference: SharedPreferences
    lateinit var spinner: Spinner
    lateinit var playing: CheckBox
    lateinit var writting: CheckBox
    lateinit var reading: CheckBox
    lateinit var gardening: CheckBox
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var genderstore: String
    lateinit var hobbyList:  ArrayList<String>
    private lateinit var db: DbHelper
    lateinit var showdata:AppCompatButton
    lateinit var clear:AppCompatButton
    lateinit var cancel:AppCompatButton
    lateinit var data:AppCompatTextView
    lateinit var gender: String
    private lateinit var stringBuffer: StringBuffer



    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        hobbyList = ArrayList()
        stringBuffer=StringBuffer()
//--------log in sppanable
        textView = findViewById(R.id.Log_in)
        val text = "already have an account? Log in"
        val spannableString = SpannableString(text)
        val clickablespan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent1 = Intent(this@SignupActivity, LogInActivity::class.java)
                startActivity(intent1)
//                Toast.makeText(this@MainActivity,"clicked successfully",Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickablespan, 25, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(spannableString, TextView.BufferType.SPANNABLE)
        textView.movementMethod = LinkMovementMethod.getInstance()


//-------img click
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

//SPINNER
        val citySpinner: Spinner = findViewById(R.id.city)
        val cities = listOf("Ahmedabad", "Amreli", "Surat", "Junagadh","Bhavanagr","Rajakot","Jamanagar","Porbandar","Vadodara")
        val adapter =object:  ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.setTextColor(Color.parseColor("#F35B5B"))
                return view
            }
        }
        Log.d(citySpinner.toString(),"city:")

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        citySpinner.adapter = adapter
        // Apply the adapter to the Spinner

//        val dbHelper = DbHelper(this)
//        val success = dbHelper.saveCity(selectedCity)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        /* ----------sign in button- */
        signupbtn = findViewById(R.id.signupbtn)
        showdata=findViewById(R.id.showdata)
        userName = findViewById(R.id.f_name)
        emailValidator = findViewById(R.id.email)
        enterdpassword = findViewById(R.id.password_toggle)
        male = findViewById(R.id.rd_male)
        female = findViewById(R.id.rd_female)
        playing = findViewById(R.id.check_playing)
        writting = findViewById(R.id.check_writting)
        reading = findViewById(R.id.check_reading)
        gardening = findViewById(R.id.chech_gardening)
        genderstore = toString()
//--------database helper
        db= DbHelper(this)


        signupbtn.setOnClickListener {
            val username = userName.text.toString().trim()
            val mail = emailValidator.text.toString().trim()
            val password = enterdpassword.text.toString().trim()
            val emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.[a-z]+"
            val hobby=hobbyList.joinToString (",")
            val selectedCity = citySpinner.selectedItem.toString()

//         ------name ,mail,password validation

            if (userName.text.toString().trim().isEmpty()) {
                userName.error = "username required"
                return@setOnClickListener
            }
            if (emailValidator.text.toString().trim().isEmpty()) {
                emailValidator.error = "Email required"
                return@setOnClickListener
            }
            if (enterdpassword.text.toString().trim().isEmpty()) {
                enterdpassword.error = "password required"
                return@setOnClickListener
            }
            if (mail != "") {
                if (mail.matches(emailPattern.toRegex())) {
//                    Toast.makeText(this@SignupActivity, "valid email address", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignupActivity, "Invalid email address", Toast.LENGTH_SHORT)
                        .show()
                }
            }
//check box validation




            if (playing.isChecked) {
                hobbyList.add(playing.text.toString())
            }
            if (reading.isChecked) {
                hobbyList.add(reading.text.toString())
            }
            if (writting.isChecked) {
                hobbyList.add(writting.text.toString())
            }
            if (gardening.isChecked) {
                hobbyList.add(gardening.text.toString())
            }
            if (hobbyList.isEmpty()) {
                Toast.makeText(this@SignupActivity, "Select at least one hobby", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


             gender = if (male.isChecked) {
                genderstore + male.text.toString()
            } else if (female.isChecked) {
                genderstore + female.text.toString()
            } else {
                Toast.makeText(this@SignupActivity, "Please select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("gender",genderstore)

            if (genderstore.isEmpty()) {
                Toast.makeText(this@SignupActivity, " select gender ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else {
                Log.d(hobby, "hobby:")
                val savedata=db.saveuserdata(username,mail,password)
                if(savedata){
                    Toast.makeText(this@SignupActivity,"detail saved",Toast.LENGTH_SHORT).show()
                }
                val cursor=db.getText()
                if(cursor?.count==-1){
                    Toast.makeText(this@SignupActivity, "no data ", Toast.LENGTH_SHORT).show()
                }

                while(cursor!!.moveToNext()){
                    stringBuffer.append("name : "+cursor.getString(0)+"\n")
                    stringBuffer.append("mail id :  "+cursor.getString(1)+"\n")
                    stringBuffer.append("password :  "+cursor.getString(2)+"\n")
//                    stringBuffer.append("city: " + cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_CITY)) + "\n")
//                    stringBuffer.append("gender :  "+cursor.getString(4)+"\n")
                }
                Log.d("stringbuffer: ",stringBuffer.toString())
                val builder= AlertDialog.Builder(this)
                builder.setCancelable(true)
                builder.setTitle("user data")
                builder.setMessage(stringBuffer.toString())
                builder.show()

//                val intent2 = Intent(this@SignupActivity, LogInActivity::class.java)
//                startActivity(intent2)
            }

//            ---------shared preference----

            sharePreference = getSharedPreferences("MY_PRE", MODE_PRIVATE)

            if (username.length > 1) {
                val editor = sharePreference.edit()
                editor.putString("USERNAME", username)
                editor.putString("EMAIL", mail)
                editor.putString("PASSWORD", password)
                editor.putString("HOBBY",hobby)
                editor.apply()
            }
        }

        showdata.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Saved User Data")
            builder.setMessage(stringBuffer.toString())
            builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            builder.setPositiveButton("OK") { dialog, which ->
                // Action to perform when OK button is clicked
                dialog.dismiss()
            }
            builder.setNegativeButton("clear data") { dialog, which ->
                // Action to perform when Cancel button is clicked
                stringBuffer.setLength(0)
                db.delete()

            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}




