package com.example.graphqlproject.ui.views

import com.example.graphqlproject.AddTodoMutation

interface AddPostFragmentView : BaseView {
    fun onAddNewPost(post: MutableList<AddTodoMutation.Returning>?)
}