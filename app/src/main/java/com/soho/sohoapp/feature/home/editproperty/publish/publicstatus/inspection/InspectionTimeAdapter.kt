package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.extensions.toStringWithDisplayFormat
import com.soho.sohoapp.utils.DateUtils

class InspectionTimeAdapter(val context: Context) : RecyclerView.Adapter<InspectionTimeAdapter.ViewHolder>() {
    private var inspectionTimes = mutableListOf<InspectionTime>()
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_inspection_time, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inspectionTime = inspectionTimes[position]
        holder.itemView.setOnClickListener { listener.onTimeClicked(inspectionTime) }
        holder.date.text = inspectionTime.startTime.toStringWithDisplayFormat()
        holder.time.text = DateUtils.toInspectionDurationString(inspectionTime.startTime, inspectionTime.endTime)
    }

    override fun getItemCount() = inspectionTimes.size

    fun setData(newInspectionTimes: List<InspectionTime>) {
        inspectionTimes.clear()
        inspectionTimes.addAll(newInspectionTimes)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var date: TextView = itemView.findViewById<TextView>(R.id.date)
        internal var time: TextView = itemView.findViewById<TextView>(R.id.time)
    }

    interface OnItemClickListener {
        fun onTimeClicked(inspectionTime: InspectionTime)
    }
}