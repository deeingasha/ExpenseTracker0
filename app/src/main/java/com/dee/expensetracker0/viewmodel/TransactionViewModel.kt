package com.dee.expensetracker0.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dee.expensetracker0.datastore.UIModeDataStore
import com.dee.expensetracker0.model.Transaction
import com.dee.expensetracker0.repo.TransactionRepo
import com.dee.expensetracker0.utils.ViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application, private val transactionRepo: TransactionRepo) :AndroidViewModel(application){

    val transactionFilter = MutableStateFlow<String>("Overall")
    val filterState = transactionFilter.asStateFlow()

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)

    //UI collect from this state flow to get state updates
    val uiState = _uiState.asStateFlow()
    //init datastore
    private val uiModeDataStore = UIModeDataStore(application)

    //get ui mode
    val getUIMode = uiModeDataStore.uiMode

    //save ui mode
    fun saveToDatatstore(isNightMode:Boolean){
        viewModelScope.launch(IO) {
            uiModeDataStore.saveToDatastore(isNightMode)
        }
    }

  //insert transaction
    fun  insertTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.insert(transaction)
  }
    //update transaction
    fun  updateTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.update(transaction)
    }
    //insert transaction
    fun  deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        transactionRepo.delete(transaction)
    }
    init {
        viewModelScope.launch {
            transactionRepo.getAllSingleTransaction(filterState.value).collect(){ result ->

                if (result.isNullOrEmpty()){
                    _uiState.value = ViewState.Empty
                }else{
                    _uiState.value = ViewState.Success(result)
                }
            }
        }
    }

}