package com.codechallenge.a20230303_joshuahand_nycschools.view.school_list_activity

import com.codechallenge.a20230303_joshuahand_nycschools.adapters.school_list_adapter.SchoolListItemUiModel

data class SchoolListUiModel(val schoolListItemUiModels: List<SchoolListItemUiModel>, val errorMessage: String? = null)