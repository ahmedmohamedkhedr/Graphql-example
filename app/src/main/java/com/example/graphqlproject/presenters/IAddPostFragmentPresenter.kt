package com.example.graphqlproject.presenters

import com.example.graphqlproject.AddTodoMutation
import com.example.graphqlproject.presenters.base.IBasePresenter
import io.reactivex.disposables.CompositeDisposable

interface IAddPostFragmentPresenter : IBasePresenter {
    fun addNewPost(mutation: AddTodoMutation, disposable: CompositeDisposable)
}