@file:Suppress("DEPRECATION")

package com.samanvay.a1oro.signUP

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samanvay.a1oro.login.Login
import com.samanvay.a1oro.login.PhoneNumberLogin
import com.samanvay.a1oro.MainActivity
import com.samanvay.a1oro.R
import com.samanvay.a1oro.SplashScreen
import com.samanvay.a1oro.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivitySignUpBinding
    private lateinit var emailS:String
    private lateinit var passwordS:String
    private lateinit var nameS:String
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val reqCode: Int = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize Firebase Auth
        auth = Firebase.auth
        binding.signUpSignUpViaEmail.setOnClickListener {
            if(extractData()){
                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show()
                createUserWithEmail()
            }
        }
        binding.signupViaPhone.setOnClickListener {
            startActivity(Intent(this, PhoneNumberLogin::class.java))
            finish()
        }
        binding.signupViaGoogle.setOnClickListener {
            createGoogleSignInRequest()
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun createGoogleSignInRequest(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signUpViaGoogle()
    }
    private fun signUpViaGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, reqCode)

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) updateUIGoogleSignUp(account)
        } catch (e: ApiException) {
            Log.d("TAG", "handleResult: $e ${e.status} ${e.statusCode}")
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUIGoogleSignUp(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Welcome "+account.displayName.toString()+"to A1oro",Toast.LENGTH_SHORT).show()
                nameS=account.displayName.toString()
                emailS=account.email.toString()
                saveSharedPreferences()
                Toast.makeText(this, account.id.toString(), Toast.LENGTH_SHORT).show()
                TODO("password dialog if a user logged in by google")
            }
        }
    }

    private fun createUserWithEmail(){
        auth.createUserWithEmailAndPassword(emailS, passwordS)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUIEmailSignUp(user)
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed!" +
                            " please try again!",
                        Toast.LENGTH_SHORT).show()
                    updateUIEmailSignUp(null)
                }
            }
    }
    private fun updateUIEmailSignUp(user: FirebaseUser?) {
        if(user!=null){
            saveSharedPreferences()
            Toast.makeText(baseContext, "Welcome! $nameS to A1oro Family :)", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignUp, MainActivity::class.java))
            finish()
        }

    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }
    private fun extractData():Boolean{
        emailS=binding.email.text.toString()
        passwordS=binding.password.text.toString()
        nameS=binding.name.text.toString()
        if(emailS.isEmpty()) binding.email.error = "required!!"
        else if(!emailS.contains("@")) binding.email.error = "Invalid Email!!"
        else if(passwordS.isEmpty()) binding.password.error = "required!!"
        else if(nameS.isEmpty()) binding.name.error = "required!!"
        else return true
        return false
    }
    private fun reload(){
        startActivity(Intent(this@SignUp, SplashScreen::class.java))
    }
    private fun saveSharedPreferences() {
        val sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE)
        @SuppressLint("CommitPrefEdits") val editor = sharedPreferences.edit()
        editor.putString("name",nameS)
        editor.putString("email",emailS)
        editor.apply()
    }
}