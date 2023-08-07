package com.example.project.presentation.Auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.project.databinding.FragmentLoginBinding
import com.example.project.domain.AuthState
import com.example.project.presentation.Auth.ViewModels.LoginViewModel

import com.example.project.presentation.Main.MainActivity
import com.example.project.presentation.MainApplication
import com.example.project.presentation.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginFragment : Fragment() {

    private val args by navArgs<LoginFragmentArgs>()

    private lateinit var viewModel: LoginViewModel
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var progressBarThread: Thread? = null
    private var curProgress = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as MainApplication).component
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        binding.linearLayoutProgress.visibility = View.GONE
    }

    private fun setOnClickListeners(){
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment(args.type))
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }

        binding.btnSignIn.setOnClickListener{ view ->
            val email = binding.edtEmailID.text.toString()
            val pass = binding.edtPassword.text.toString()
            viewModel.login(email, pass)

            lifecycleScope.launch {
                viewModel.stateFlow.collect{
                    when(it) {
                        is AuthState.Loading ->{
                            withContext(Dispatchers.Main){
                                binding.linearLayoutProgress.visibility = View.VISIBLE
//                                fakeProgressBar()
                                view.hideKeyboard()

                            }
                        }
                        is AuthState.Error ->{
                            withContext(Dispatchers.Main){
                                Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show()
                                binding.linearLayoutProgress.visibility = View.GONE
//                                progressBarThread?.interrupt()
//                                curProgress=0
                            }
                        }
                        is AuthState.Success ->{
                            if (it.data!!.type == args.type || it.data.type == "Teacher"){
                                withContext(Dispatchers.Main){
                                    MainActivity.newIntent(requireActivity(), args.type)
                                }
                            }
                            else{
                                withContext(Dispatchers.Main){
                                    Toast.makeText(requireActivity(), "Неподходящий уровень ответственности", Toast.LENGTH_SHORT).show()
                                    binding.linearLayoutProgress.visibility = View.GONE
                                }
                            }

                        }

                    }
                }
            }

        }
    }

//    fun fakeProgressBar(){
//
//        progressBarThread = Thread {
//            while(!Thread.interrupted() && curProgress != 90){
//                curProgress+=2
//                Thread.sleep(25)
//                Handler(Looper.getMainLooper()).post {
//                    Log.d("checkshit", curProgress.toString())
//                    binding.progressBar.progress = curProgress
//                }
//            }
//        }
//        progressBarThread?.start()
//    }


    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}