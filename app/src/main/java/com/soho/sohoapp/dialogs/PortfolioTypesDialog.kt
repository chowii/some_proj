package com.soho.sohoapp.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import com.soho.sohoapp.R
import java.util.*

@Deprecated("Replace this dialog by Bottom Sheet fragment dialog")
class PortfolioTypesDialog(private val context: Context) {
    private val HOME = 0
    private val INVESTMENT = 1
    private val ARCHIVED = 2

    fun show(listener: Listener): AlertDialog {
        val builder = AlertDialog.Builder(context)

        val arrayAdapter = ArrayAdapter<String>(context, R.layout.dialog_item).apply { addAll(createItems()) }
        builder.setAdapter(arrayAdapter) { _, which ->
            when (which) {
                HOME -> listener.onHomeClicked()
                INVESTMENT -> listener.onInvestmentClicked()
                ARCHIVED -> listener.onArchiveClicked()
            }
        }
        return builder.show()
    }

    private fun createItems() = ArrayList<String>().apply {
        add(HOME, context.getString(R.string.edit_property_portfolio_type_home))
        add(INVESTMENT, context.getString(R.string.edit_property_portfolio_type_investment))
        add(ARCHIVED, context.getString(R.string.edit_property_portfolio_type_archived))
    }

    interface Listener {
        fun onHomeClicked()
        fun onInvestmentClicked()
        fun onArchiveClicked()
    }
}