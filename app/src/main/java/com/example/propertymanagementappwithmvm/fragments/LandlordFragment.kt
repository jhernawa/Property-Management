package com.example.propertymanagementappwithmvm.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.activities.LoginActivity
import com.example.propertymanagementappwithmvm.app.Config
import com.example.propertymanagementappwithmvm.databinding.FragmentLandlordBinding
import com.example.propertymanagementappwithmvm.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_landlord.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LandlordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LandlordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandlordFragment : Fragment(), RegisterViewModel.RegisterViewModelInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    //you need to create the <layout> and <data> first before you can see the FragmentLandlordBinding
    lateinit var landlordFragmentBinding : FragmentLandlordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        landlordFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_landlord, container, false)
        var view : View =  landlordFragmentBinding.root

        //initialize the view model for this fragment
        var registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        //bind registerViewModel with the layout
        landlordFragmentBinding.registerViewModel = registerViewModel

        //set this fragment as a listener to the register view model
        registerViewModel.setupListener(this)
        registerViewModel.account = Config.LANDLORD_ACCOUNT



        return view
    }

    override fun goToLoginButton() {
        startActivity(Intent(activity, LoginActivity::class.java))
    }

    override fun onSuccess(registerResponse : LiveData<String>) {
        registerResponse.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                Log.d("aaa", "register onSuccess")
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                goToLoginButton()

            }

        })

    }

    override fun onError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }



    /*only use onAttach and onDetach when you have an activity that would listen to this fragment
      or else it will throw you an exception since it would set listener = context
     */
   /* override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LandlordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LandlordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
