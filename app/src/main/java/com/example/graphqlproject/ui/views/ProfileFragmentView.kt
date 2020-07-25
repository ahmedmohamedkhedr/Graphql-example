package com.example.graphqlproject.ui.views

import com.example.graphqlproject.GetUserPostsQuery

interface ProfileFragmentView : BaseView {
    fun onReceiveUserPosts(posts: MutableList<GetUserPostsQuery.Todo>? , err:String?)
}