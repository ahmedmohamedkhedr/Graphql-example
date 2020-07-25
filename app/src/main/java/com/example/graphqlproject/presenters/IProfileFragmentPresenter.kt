package com.example.graphqlproject.presenters

import com.example.graphqlproject.GetUserPostsQuery
import com.example.graphqlproject.presenters.base.IBasePresenter
import io.reactivex.disposables.CompositeDisposable

interface IProfileFragmentPresenter : IBasePresenter {
    fun getUserPosts(query: GetUserPostsQuery, disposable: CompositeDisposable)
}