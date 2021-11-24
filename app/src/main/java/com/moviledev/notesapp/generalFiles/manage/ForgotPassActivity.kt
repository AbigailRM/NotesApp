package com.moviledev.notesapp.generalFiles.manage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.moviledev.notesapp.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPassBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRestorePass.setOnClickListener {
            validateEmail()
        }

        binding.tvAccount.setOnClickListener{
            val intent= Intent(this@ForgotPassActivity, PrincipalActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private var email=""

    private fun validateEmail() {
        email = binding.etEmail.text.toString().trim()

        //validated email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
            Toast.makeText(this, "Invalid Email Pattern Or None Email Is Empty...", Toast.LENGTH_SHORT).show()
        }
        else(
                restorePass(email)
                )
    }

    private fun restorePass(email: String) {
        val emailAddress = email

        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Email sent successfully to reset your password!", Toast.LENGTH_SHORT).show()

                    val intent= Intent(this@ForgotPassActivity, PrincipalActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }else{
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()

                }
            }
    }
}