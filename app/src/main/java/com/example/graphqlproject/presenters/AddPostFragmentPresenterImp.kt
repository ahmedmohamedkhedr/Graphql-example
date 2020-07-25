package com.example.graphqlproject.presenters

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxMutate
import com.example.graphqlproject.AddTodoMutation
import com.example.graphqlproject.ui.views.AddPostFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddPostFragmentPresenterImp(
    private var view: AddPostFragmentView?,
    private val client: ApolloClient
) : IAddPostFragmentPresenter {
    override fun addNewPost(mutation: AddTodoMutation, disposable: CompositeDisposable) {
        disposable.add(
            client.rxMutate(mutation).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { i -> view?.onAddNewPost(i.data?.insert_todos()?.returning()) }
        )
    }

    override fun setProgressBarVisibility(visibility: Int) {
        view?.onSetProgressBarVisibility(visibility)
    }

    override fun onDestroy() {
        view = null
    }
}