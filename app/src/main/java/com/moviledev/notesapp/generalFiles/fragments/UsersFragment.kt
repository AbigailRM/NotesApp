package com.moviledev.notesapp.generalFiles.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.moviledev.notesapp.databinding.FragmentUsersBinding
import com.moviledev.notesapp.generalFiles.manage.PrincipalActivity

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var dbRef: DatabaseReference

    //Progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUsersBinding.inflate(layoutInflater)

        //init FirebaseAuth
        auth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        loadUser()

        binding.ibLogOut.setOnClickListener {
            auth.signOut()

            val intent=Intent(this@UsersFragment.requireContext(), PrincipalActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun loadUser() {
        progressDialog.setMessage("Checking User...")

        val uid = auth.currentUser?.uid.toString()

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        //if (uid.isEmpty()){
        dbRef.child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){

                    binding.etName.text = snapshot.child("name").value.toString()

                    binding.etLastName.text = snapshot.child("lastName").value.toString()

                    binding.etSex.text = snapshot.child("sex").value.toString()

                    binding.etDateOfBirth.text = snapshot.child("dateOfBirth").value.toString()

                    binding.etPhone.text = snapshot.child("phone").value.toString()

                    binding.etEmail.text = snapshot.child("email").value.toString()

                    binding.etAddress.text=snapshot.child("address").value.toString()

                    binding.etState.text=snapshot.child("state").value.toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    progressDialog.dismiss()
                    Toast.makeText(requireActivity(), "Failed getting profile data", Toast.LENGTH_SHORT).show()
                }
            })
    }

}