package com.example.graphqlproject.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.graphqlproject.AddTodoMutation
import com.example.graphqlproject.R
import com.example.graphqlproject.presenters.AddPostFragmentPresenterImp
import com.example.graphqlproject.ui.views.AddPostFragmentView
import com.example.graphqlproject.utilities.Id
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddPostFragment : Fragment(), AddPostFragmentView, View.OnClickListener {
    private lateinit var postEditText: EditText
    private lateinit var postBtn: Button
    private lateinit var postProgressBar: ProgressBar
    private val presenter: AddPostFragmentPresenterImp by inject { parametersOf(this) }
    private val disposable: CompositeDisposable by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postEditText = view.findViewById(Id.editPost)
        postBtn = view.findViewById(Id.buttonPost)
        postProgressBar = view.findViewById(Id.addPostProgress)
        postBtn.setOnClickListener(this)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonPost) {
            if (TextUtils.isEmpty(postEditText.text.toString())) {
                Toast.makeText(context, getString(R.string.error_post_message), Toast.LENGTH_SHORT)
                    .show()
                return
            }
            presenter.setProgressBarVisibility(View.VISIBLE)

        }
    }

    override fun onAddNewPost(post: MutableList<AddTodoMutation.Returning>?) {
        post?.let {
            Toast.makeText(context, "You post ${post[it.lastIndex].title()}", Toast.LENGTH_SHORT)
                .show()
            presenter.setProgressBarVisibility(View.GONE)
            return
        }
        Toast.makeText(context, getString(R.string.add_post_error_message), Toast.LENGTH_SHORT)
            .show()
        presenter.setProgressBarVisibility(View.GONE)
    }

    override fun onSetProgressBarVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) {
            val mutation: AddTodoMutation by inject { parametersOf(postEditText.text.toString()) }
            presenter.addNewPost(mutation, disposable)
        }
        postProgressBar.visibility = visibility
    }
}