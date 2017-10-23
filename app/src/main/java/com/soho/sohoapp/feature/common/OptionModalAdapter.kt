package com.soho.sohoapp.feature.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R

class OptionModalAdapter(private val dataSet: List<String> = arrayListOf(),
                         private var onOptionSelected: ((String) -> Unit)?) : RecyclerView.Adapter<OptionModalAdapter.OptionViewHolder>() {

    override fun getItemCount(): Int =
            dataSet.size

    override fun getItemViewType(position: Int): Int =
            R.layout.item_text

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
        holder.itemView.setOnClickListener { onOptionSelected?.invoke(dataSet[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
        return OptionViewHolder(itemView)
    }

    // MARK: - ================== ViewHolder ==================

    class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_view)
        lateinit var textView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }
}