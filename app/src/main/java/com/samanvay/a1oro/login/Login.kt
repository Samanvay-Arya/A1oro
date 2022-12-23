package com.samanvay.a1oro.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.samanvay.a1oro.*
import com.samanvay.a1oro.databinding.ActivityLoginBinding
import com.samanvay.a1oro.LoadingDialog
import com.samanvay.a1oro.signUP.SignUp
import com.samanvay.a1oro.SplashScreen

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var emailS:String
    private lateinit var passwordS:String
    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val reqCode: Int = 123
    private var loadingDialog= LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.signup.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
        binding.loginLoginViaEmail.setOnClickListener{
            if(extractData()){
                loadingDialog.startLoadingDialog()
                singInWithEmail()
            }
        }
        binding.forgotPassword.setOnClickListener {
            forgotPassword()
        }
        binding.loginViaCall.setOnClickListener{
            startActivity(Intent(this@Login, PhoneNumberLogin::class.java))
            finish()
        }
        binding.loginViaGoogle.setOnClickListener{
            createGoogleSignInRequest()
        }

    }

    private fun singInWithEmail() {
        auth.signInWithEmailAndPassword(emailS, passwordS)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUIEmailLogin(user)
                } else {
                    Log.w("TAG", "signInWithEmail:failure")
                    Toast.makeText(baseContext, ""+ (task.exception?.message
                        ?:String ),
                        Toast.LENGTH_SHORT).show()
                    updateUIEmailLogin(null)
                }
            }
    }

    private fun updateUIEmailLogin(user: FirebaseUser?) {
       loadingDialog.dismissDialog()
        if (user != null) {
            Toast.makeText(baseContext, "Welcome! "+user.displayName+" to A1oro Family :)", Toast.LENGTH_SHORT).show()
            saveSharedPreferences(user.displayName.toString(),user.email.toString())
            startActivity(Intent(this@Login, MainActivity::class.java))
            finish()
        }

    }


    private fun createGoogleSignInRequest(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signUpGoogle()
    }
    private fun signUpGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, reqCode)

    }
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
            if (account != null) {
                updateUIGoogleLogin(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUIGoogleLogin(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Welcome "+account.displayName.toString()+" A1oro",Toast.LENGTH_SHORT).show()
                saveSharedPreferences(account.displayName.toString(),account.email.toString())
                startActivity(Intent(this@Login, MainActivity::class.java))
                finish()
            }
        }
    }


    private fun forgotPassword() {
        TODO("Not yet implemented")
    }

    private fun extractData():Boolean{
        emailS=binding.email.text.toString()
        passwordS=binding.password.text.toString()
        if(emailS.isEmpty()) binding.email.error = "required!!"
        else if(!emailS.contains("@")) binding.email.error = "Invalid Email!!"
        else if(passwordS.isEmpty()) binding.password.error = "required!!"
        else return true
        return false
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }
    private fun reload(){
        startActivity(Intent(this@Login, SplashScreen::class.java))
    }
    private fun saveSharedPreferences(name:String,email:String) {
        val sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE)
        @SuppressLint("CommitPrefEdits") val editor = sharedPreferences.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.apply()
    }
    private fun loadSharedPreferences() {
        val sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE)
    }


}