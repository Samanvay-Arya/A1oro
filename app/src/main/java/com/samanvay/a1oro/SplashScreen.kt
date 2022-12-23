package com.samanvay.a1oro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.google.firebase.auth.FirebaseAuth
import com.samanvay.a1oro.login.Login

class SplashScreen : AppCompatActivity() {
    private var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
       tim()

    }
    private fun tim(){
        val timer=object :CountDownTimer(1000,2000){
            override fun onTick(millisUntilFinished:Long){

            }
            override fun onFinish(){

                    val currentUser = auth.currentUser
                    if(currentUser != null) {
                        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this@SplashScreen, Login::class.java))
                        finish()
                    }

            }
        }
        timer.start()

    }

    override fun onResume() {
        super.onResume()
        tim()
    }



}