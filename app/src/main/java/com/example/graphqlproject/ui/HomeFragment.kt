package com.example.graphqlproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqlproject.DataQuery
import com.example.graphqlproject.adapters.HomeFragmentAdapter
import com.example.graphqlproject.presenters.HomeFragmentPresenterImp
import com.example.graphqlproject.ui.views.HomeFragmentView
import com.example.graphqlproject.utilities.Id
import com.example.graphqlproject.utilities.Layout
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(), HomeFragmentView {
    private lateinit var postsList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val presenter: HomeFragmentPresenterImp by inject { parametersOf(this) }
    private val query: DataQuery by inject()
    private val disposable: CompositeDisposable by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(Layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsList = view.findViewById(Id.homePostsList)
        progressBar = view.findViewById(Id.homeProgressBar)
        postsList.layoutManager = LinearLayoutManager(context)
        presenter.getAllPosts(query, disposable)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    override fun onReceiveAllPosts(posts: MutableList<DataQuery.Todo>?, err: String?) {

        posts?.let {
            val adapter: HomeFragmentAdapter by inject { parametersOf(it) }
            postsList.adapter = adapter
            presenter.setProgressBarVisibility(View.GONE)
            return
        }
        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
        presenter.setProgressBarVisibility(View.GONE)
    }


    override fun onSetProgressBarVisibility(visibility: Int) {
        progressBar.visibility = visibility
    }


}