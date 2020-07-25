package com.example.graphqlproject.ui.views

import com.example.graphqlproject.DataQuery

interface HomeFragmentView : BaseView {
    fun onReceiveAllPosts(posts: MutableList<DataQuery.Todo>? , err:String?)


}