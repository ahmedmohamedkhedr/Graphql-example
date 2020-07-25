package com.example.graphqlproject.presenters

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.rx2.rxQuery
import com.example.graphqlproject.GetUserPostsQuery
import com.example.graphqlproject.ui.views.ProfileFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileFragmentPresenterImp(
    private var view: ProfileFragmentView?,
    private val client: ApolloClient
) : IProfileFragmentPresenter {

    override fun getUserPosts(query: GetUserPostsQuery, disposable: CompositeDisposable) {
        disposable.add(
            client.rxQuery(query) {
                responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
            }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onReceiveUserPosts(it.data?.todos(), null)
                }, {
                    view?.onReceiveUserPosts(null, it.message)
                }, {
                    //on complete
                })
        )
    }


    override fun setProgressBarVisibility(visibility: Int) {
        view?.onSetProgressBarVisibility(visibility)
    }

    override fun onDestroy() {
        view = null
    }
}