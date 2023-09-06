package com.roman.jpmnycsch.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.roman.jpmnycsch.R
import com.roman.jpmnycsch.databinding.FragmentDetailBinding
import com.roman.jpmnycsch.databinding.FragmentListBinding
import com.roman.jpmnycsch.model.SATScore
import com.roman.jpmnycsch.viewmodel.DetailViewModel

/**
 * Class: DetailFragment
 * Owner: Roman Edjlali
 * Date Created: 09/01/2023 8:28 AM
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val tag = javaClass.simpleName

    private var detailBinding: FragmentDetailBinding? = null
    private var schUuid = 0
    private var dbnIndex = "dbn"

    lateinit var  viewModel: DetailViewModel
    private var currentSatScore: SATScore? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailBinding = FragmentDetailBinding.bind(view)
        arguments?.let {
            //schUuid = DetailFragmentArgs.fromBundle(it).schUuid
            dbnIndex = DetailFragmentArgs.fromBundle(it).dbnIndex
        }
        val callBack= object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        Log.d(tag, "***>>> schUuid is $schUuid & dbnIndex: $dbnIndex")
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        //viewModel.fetch(schUuid)
        viewModel.fetchByDbn(dbnIndex)
        observeViewModel()


    }

    private fun observeViewModel() {
        viewModel.satScoreLiveData.observe(viewLifecycleOwner, Observer {
            currentSatScore = it
            it?.let {
                detailBinding?.tvDetailDbn?.text = it.dbn
                detailBinding?.tvDetailSchoolName?.text = it.school_name
                detailBinding?.tvMathScore?.text = it.sat_math_avg_score.toString()
                detailBinding?.tvReadingScore?.text = it.sat_critical_reading_avg_score.toString()
                detailBinding?.tvTestTakers?.text = it.num_of_sat_test_takers.toString()
                detailBinding?.tvWritingScore?.text = it.sat_writing_avg_score.toString()
            }
        })
    }

    override fun onDestroy() {
        detailBinding = null
        super.onDestroy()
    }

}