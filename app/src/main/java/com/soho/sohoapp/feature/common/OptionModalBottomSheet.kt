package com.soho.sohoapp.feature.common

import android.app.Dialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R

class OptionModalBottomSheet(private var options: List<String>,
                             private val title: String,
                             private var onOptionSelected: ((String) -> Unit)?) : BottomSheetDialogFragment() {

    val TAG = "OptionModalBottomSheet"

    @BindView(R.id.txt_title)
    lateinit var textViewTitle: TextView

    @BindView(R.id.rv_options)
    lateinit var recyclerViewOptions: RecyclerView

    lateinit var adapter: OptionModalAdapter

    constructor() : this(arrayListOf(), "", null)

    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_option_modal_bottom_sheet, null)
        ButterKnife.bind(this, contentView)
        dialog?.setContentView(contentView)
        adapter = OptionModalAdapter(options, {
            onOptionSelected?.invoke(it)
            dialog?.dismiss()
        })
        recyclerViewOptions.adapter = adapter
        recyclerViewOptions.layoutManager = LinearLayoutManager(context)
        textViewTitle.text = title
    }
}