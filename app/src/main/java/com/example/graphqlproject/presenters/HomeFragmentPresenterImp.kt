package com.example.graphqlproject.presenters

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.rx2.rxQuery
import com.example.graphqlproject.DataQuery
import com.example.graphqlproject.ui.views.HomeFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeFragmentPresenterImp(
    private var view: HomeFragmentView?,
    private val client: ApolloClient
) :
    IHomeFragmentPresenter {

    override fun getAllPosts(query: DataQuery, disposable: CompositeDisposable) {

        disposable.add(
            client.rxQuery(query) {
                responseFetcher(ApolloResponseFetchers.CACHE_FIRST)
            }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onReceiveAllPosts(it.data?.todos(), null)
                }, {
                    view?.onReceiveAllPosts(null, it.message)
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