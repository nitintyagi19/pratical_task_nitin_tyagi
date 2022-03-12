package com.app.tasknitintyagi

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.app.tasknitintyagi.util.RemoteDataSource
import com.app.tasknitintyagi.viewModel.HomeViewModel
import com.app.tasknitintyagi.viewModel.SignUpViewModel


/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val baseApplication: Application,
    private val remoteDataSource: RemoteDataSource,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(SignUpViewModel::class.java) ->
                SignUpViewModel(baseApplication, remoteDataSource)
            isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(baseApplication, remoteDataSource)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}