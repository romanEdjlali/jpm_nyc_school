package com.roman.jpmnycsch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.jpmnycsch.R
import com.roman.jpmnycsch.databinding.FragmentListBinding
import com.roman.jpmnycsch.viewmodel.ListViewModel


/**
 * Class: ListFragment
 * Owner: Roman Edjlali
 * Date Created: 09/01/2023 8:23 AM
 */
class ListFragment : Fragment() {

    private val tag = javaClass.simpleName
    private lateinit var listBinding: FragmentListBinding

    lateinit var viewModel: ListViewModel
    private val schoolAdapter = SchoolAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {

        }
        viewModel = ViewModelProviders.of(this).get(SchoolViewModel::class.java)
        viewModel.refresh()

        listBinding.schoolList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = schoolAdapter
        }

        observeViewModel()*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        listBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val btnDetail = view.findViewById<Button>(R.id.btnDetail)
        btnDetail.setOnClickListener{
            val action = ListFragmentDirections.actionDetailFragment()
            action.schUuid = 5
            Navigation.findNavController(it).navigate(action)
        }*/

        arguments?.let {

        }
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        listBinding.schoolList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = schoolAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.schools.observe(viewLifecycleOwner, Observer {schools ->
            schools?.let {
                listBinding.schoolList.visibility = View.VISIBLE
                schoolAdapter.updateSchoolList(it) }
        })

        viewModel.schoolLoadError.observe(viewLifecycleOwner, Observer {
            it?.let {
                listBinding.listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                listBinding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listBinding.listError.visibility = View.GONE
                    listBinding.schoolList.visibility = View.GONE
                }
            }
        })
    }

}