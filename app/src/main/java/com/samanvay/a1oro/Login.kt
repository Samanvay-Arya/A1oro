package com.samanvay.a1oro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.samanvay.a1oro.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var emailS:String
    private lateinit var passwordS:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signup.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }
        binding.loginLoginViaEmail.setOnClickListener{
            if(extractData()){
                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show()
            }
        }
        binding.forgotPassword.setOnClickListener {
            forgotPassword()
        }
        binding.loginViaCall.setOnClickListener{
            startActivity(Intent(this@Login,PhoneNumberLogin::class.java))
            finish()
        }

    }

    private fun forgotPassword() {
        TODO("Not yet implemented")
    }

    private fun extractData():Boolean{
        emailS=binding.email.text.toString()
        passwordS=binding.password.text.toString()
        if(emailS.isEmpty()) binding.email.setError("required!!")
        else if(!emailS.contains("@")) binding.email.setError("Invalid Email!!")
        else if(passwordS.isEmpty()) binding.password.setError("required!!")
        else return true
        return false
    }
}