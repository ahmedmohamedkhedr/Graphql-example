package com.example.graphqlproject.presenters

import com.example.graphqlproject.DataQuery
import com.example.graphqlproject.presenters.base.IBasePresenter
import io.reactivex.disposables.CompositeDisposable

interface IHomeFragmentPresenter : IBasePresenter {
    fun getAllPosts(query: DataQuery, disposable: CompositeDisposable)
}