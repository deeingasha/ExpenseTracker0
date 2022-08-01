package com.dee.expensetracker0.repo

import com.dee.expensetracker0.db.AppDatabase
import com.dee.expensetracker0.model.Transaction

class TransactionRepo (private val db:AppDatabase){

    //insert transaction
    suspend fun insert(transaction: Transaction) = db.getTransactionDao().insertTransaction(transaction)

    //update transaction
    suspend fun update(transaction:Transaction) = db.getTransactionDao().updateTransaction(transaction)

    //delete transaction
    suspend fun delete(transaction:Transaction) = db.getTransactionDao().deleteTransaction(transaction)

    //get all transaction
    fun getAllTransactions() = db.getTransactionDao().getAllTransactions()

    //get single transaction type
    fun getAllSingleTransaction(transactionType:String)=if (transactionType == "Overall") {
        getAllTransactions()
    }else{
        db.getTransactionDao().getAllSingleTransaction(transactionType)
    }
}