package com.example.academy.ui.academy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy.databinding.FragmentAcademyBinding
import com.example.academy.viewmodel.ViewModelFactory
import com.example.academy.vo.Status
import kotlinx.coroutines.launch

class AcademyFragment : Fragment() {

    private lateinit var fragmentAcademyBinding: FragmentAcademyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentAcademyBinding = FragmentAcademyBinding.inflate(layoutInflater, container, false)
        return fragmentAcademyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]

            val academyAdapter = AcademyAdapter()
            lifecycleScope.launch {
                viewModel.getCourses().observe(viewLifecycleOwner) { courses ->
                    if (courses != null) {
                        when (courses.status) {
                            Status.LOADING -> fragmentAcademyBinding.progressBar.visibility =
                                View.VISIBLE
                            Status.SUCCESS -> {
                                fragmentAcademyBinding.progressBar.visibility = View.GONE
                                academyAdapter.submitList(courses.data)
                            }
                            Status.ERROR -> {
                                fragmentAcademyBinding.progressBar.visibility = View.GONE
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            with(fragmentAcademyBinding.rvAcademy) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = academyAdapter
            }
        }
    }
}