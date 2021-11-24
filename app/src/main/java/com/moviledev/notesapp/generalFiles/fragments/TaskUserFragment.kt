package com.moviledev.notesapp.generalFiles.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moviledev.notesapp.MainActivity
import com.moviledev.notesapp.databinding.FragmentTaskUserBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class TaskUserFragment : Fragment() {

    private lateinit var binding: FragmentTaskUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentTaskUserBinding.inflate(layoutInflater)

        binding.btnNewTask.setOnClickListener {
            val intent = Intent(this@TaskUserFragment.requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}