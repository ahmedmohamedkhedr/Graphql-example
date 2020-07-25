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
import com.example.graphqlproject.GetUserPostsQuery
import com.example.graphqlproject.adapters.ProfileFragmentAdapter
import com.example.graphqlproject.presenters.ProfileFragmentPresenterImp
import com.example.graphqlproject.ui.views.ProfileFragmentView
import com.example.graphqlproject.utilities.Id
import com.example.graphqlproject.utilities.Layout
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProfileFragment : Fragment(), ProfileFragmentView {
    private lateinit var progressBar: ProgressBar
    private lateinit var list: RecyclerView
    private val presenter: ProfileFragmentPresenterImp by inject { parametersOf(this) }
    private val disposable: CompositeDisposable by inject()
    private val query: GetUserPostsQuery by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(Layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(Id.profileProgressBar)
        list = view.findViewById(Id.profilePostsList)
        list.layoutManager = LinearLayoutManager(context)
        presenter.getUserPosts(query, disposable)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onReceiveUserPosts(posts: MutableList<GetUserPostsQuery.Todo>?, err: String?) {
        posts?.let {
            val adapter: ProfileFragmentAdapter by inject { parametersOf(posts) }
            list.adapter = adapter
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