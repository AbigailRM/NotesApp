package com.moviledev.notesapp.generalFiles.manage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.moviledev.notesapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    
    lateinit var binding: ActivitySignUpBinding

    //Progress dialog
    private lateinit var progressDialog:ProgressDialog

    //Firebase
    lateinit var auth:FirebaseAuth
    lateinit var dbRef:DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        auth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnSubmit.setOnClickListener {
            validateData()
        }

        binding.tvAccount.setOnClickListener{
            val intent= Intent(this@SignUpActivity, PrincipalActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }
    private var name = ""
    private var lastName = ""
    private var sex = ""
    private var dateOfBirth = ""
    private var email = ""
    private var phone = ""
    private var password = ""
    private var cPassword = ""
    private var state = ""
    private var address = ""

    private fun validateData(){
        name = binding.etName.text.toString()
        lastName = binding.etLastName.text.toString()

        if (binding.rbMen.isChecked){
            sex = "Male"
        }else if (binding.rbWomen.isChecked){
            sex = "Female"
        }

        dateOfBirth = binding.etBirth.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        phone = binding.etPhone.text.toString().trim()
        password = binding.etPass.text.toString().trim()
        cPassword = binding.etConfirmPass.text.toString().trim()
        state = binding.etState.text.toString().trim()
        address = binding.etAddress.text.toString().trim()

        //validating
        if(name == ""){
            Toast.makeText(this, "Name is required...", Toast.LENGTH_SHORT).show()
        }
        else if(lastName == ""){
            Toast.makeText(this, "Last name is required...", Toast.LENGTH_SHORT).show()
        }
        else if(sex == ""){
            Toast.makeText(this, "Sex is required...", Toast.LENGTH_SHORT).show()
        }
        else if(dateOfBirth == ""){
            Toast.makeText(this, "Date of birth is required...", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        }
        else if(phone == ""){
            Toast.makeText(this, "Phone number is required...", Toast.LENGTH_SHORT).show()
        }
        else if(password == ""){
            Toast.makeText(this, "Password is required...", Toast.LENGTH_SHORT).show()
        }
        else if(cPassword == ""){
            Toast.makeText(this, "Confirm your password...", Toast.LENGTH_SHORT).show()
        }
        else if(password != cPassword){
            Toast.makeText(this, "Password doesn't match...", Toast.LENGTH_SHORT).show()
        }
        else if(state == ""){
            Toast.makeText(this, "State is required...", Toast.LENGTH_SHORT).show()
        }
        else if(address == ""){
            Toast.makeText(this, "Address is required...", Toast.LENGTH_SHORT).show()
        }
        else{
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating account...")
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed creating account due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun updateUserInfo() {

        progressDialog.setMessage("Saving user info...")


        //get current user uid
        val uid = auth.currentUser?.uid

        //add in db
        val hashMap=HashMap<String,Any?>()
        hashMap.put("uid",uid)
        hashMap.put("name",name)
        hashMap.put("lastName",lastName)
        hashMap.put("sex",sex)
        hashMap.put("dateOfBirth",dateOfBirth)
        hashMap.put("state",state)
        hashMap.put("address",address)
        hashMap.put("phone",phone)
        hashMap.put("email",email)

        //set data to db

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info saved
                progressDialog.dismiss()
                Toast.makeText(this, "Account created...", Toast.LENGTH_SHORT).show()

                val intent=Intent(this@SignUpActivity, PrincipalActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

}