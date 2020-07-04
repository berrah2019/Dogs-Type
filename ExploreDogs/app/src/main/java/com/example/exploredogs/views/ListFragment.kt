package com.example.exploredogs.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exploredogs.R
import com.example.exploredogs.viewmodels.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    private lateinit var viewmodel: ListViewModel
    private val dogslistadapter = DogsListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewmodel.refresh()
        dog_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogslistadapter
        }
        swipe_layout.setOnRefreshListener {
            dog_list.visibility = View.GONE
            list_error.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewmodel.refreshByPass()
            swipe_layout.isRefreshing = false
        }
        observeviewmodel()
    }

    private fun observeviewmodel() {
        viewmodel.dogs.observe(this, Observer { dogs ->
            dogs?.let {
                dog_list.visibility = View.VISIBLE
                dogslistadapter.UpdateDogList(dogs)
            }

        })
        viewmodel.dogsloadError.observe(this, Observer { isError ->
            isError?.let {
                list_error.visibility = if (it) View.VISIBLE else View.GONE
            }

        })

        viewmodel.loading.observe(this, Observer {
            it?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    dog_list.visibility = View.GONE
                    list_error.visibility = View.GONE
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSettings -> {
               view?.let {
               //    Navigation.findNavController(it)
                //        .navigate( .actionSetting())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
