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
class ListFragment : Fragment(R.layout.fragment_list) {

    private val tag = javaClass.simpleName
    private var listBinding: FragmentListBinding? = null

    lateinit var viewModel: ListViewModel
    private val schoolAdapter = SchoolAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listBinding = FragmentListBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        listBinding?.schoolList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = schoolAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.schools.observe(viewLifecycleOwner, Observer {schools ->
            schools?.let {
                listBinding?.schoolList?.visibility = View.VISIBLE
                schoolAdapter.updateSchoolList(it) }
        })

        viewModel.schoolLoadError.observe(viewLifecycleOwner, Observer {
            it?.let {
                listBinding?.listError?.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                listBinding?.loadingView?.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listBinding?.listError?.visibility = View.GONE
                    listBinding?.schoolList?.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        listBinding = null
        super.onDestroy()
    }

}