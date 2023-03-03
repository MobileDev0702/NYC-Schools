package com.codechallenge.a20230303_joshuahand_nycschools.view.school_list_activity

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.codechallenge.a20230303_joshuahand_nycschools.R
import com.codechallenge.a20230303_joshuahand_nycschools.adapters.school_list_adapter.OnSchoolListItemSelectedListener
import com.codechallenge.a20230303_joshuahand_nycschools.adapters.school_list_adapter.SchoolListAdapter
import com.codechallenge.a20230303_joshuahand_nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnSchoolListItemSelectedListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var schoolListViewModel : SchoolListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val schoolListAdapter = SchoolListAdapter(this, this, linearLayoutManager)
        recyclerView.adapter = schoolListAdapter

        schoolListViewModel = ViewModelProvider(this, factory)[SchoolListViewModelImpl::class.java]

        schoolListViewModel?.getSchoolList()?.observe(this, Observer { schoolListUiModel ->
            schoolListUiModel?.let {
                schoolListAdapter.updateList(schoolListUiModel.schoolListItemUiModels)
                schoolListUiModel.errorMessage?.let {
                    toast(it)
                }
            }
        })
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        schoolListViewModel?.onSchoolListItemSelected(schoolListItemUiModel)
    }
}