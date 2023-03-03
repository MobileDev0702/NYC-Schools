package com.codechallenge.a20230303_joshuahand_nycschools.view.school_list_activity

import androidx.lifecycle.LiveData
import com.codechallenge.a20230303_joshuahand_nycschools.adapters.school_list_adapter.SchoolListItemUiModel

interface SchoolListViewModel {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel)
}
