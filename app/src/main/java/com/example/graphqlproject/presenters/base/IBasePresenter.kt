package com.example.graphqlproject.presenters.base

interface IBasePresenter {
    fun setProgressBarVisibility(visibility: Int)
    fun onDestroy()
}