package com.moviledev.notesapp.generalFiles.manage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.moviledev.notesapp.databinding.ActivityPrincipalBinding
import com.moviledev.notesapp.generalFiles.HomeActivity
import com.moviledev.notesapp.generalFiles.fragments.ProviderType

class PrincipalActivity : AppCompatActivity() {

    lateinit var binding:ActivityPrincipalBinding

    //Progress dialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        auth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.tvNoAccount.setOnClickListener{
            val intent= Intent(this@PrincipalActivity,SignUpActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.tvForgotPass.setOnClickListener {
            val intent= Intent(this@PrincipalActivity,ForgotPassActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.btnSubmit.setOnClickListener {
            validateData()
        }
    }

    private var email = ""
    private var password = ""

    private fun validateData() {
        //input data
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim {it <= ' '}

        //validated data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
            Toast.makeText(this, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
        }
        else{
            loginUser()
        }
    }

    private fun loginUser() {

        var provider:ProviderType

        progressDialog.setMessage("Logging In...")
        progressDialog.show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                provider = ProviderType.BASIC

                val intent= Intent(this@PrincipalActivity, HomeActivity::class.java)
                intent.putExtra("userId",it.user?.uid)
                intent.putExtra("email",email)
                intent.putExtra("provider",provider.name)
                startActivity(intent)
                this.finish()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()

            }
    }
}