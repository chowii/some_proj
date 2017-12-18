package com.soho.sohoapp.feature.home.editproperty.connections

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.data.enums.PropertyUserRole
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.PropertyUser
import com.soho.sohoapp.feature.home.portfolio.data.Title
import com.soho.sohoapp.imageloader.ImageLoader

class ConnectionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_USER = 1
    private val TYPE_REQUEST = 2
    private val TYPE_UNDEFINED = 100

    private lateinit var currentUserRole: String
    private var displayableList = mutableListOf<Displayable>()
    private var currentUserId = DEPENDENCIES.userPrefs.user?.id

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val context = parent?.context
        return when (viewType) {
            TYPE_HEADER -> HeaderHolder(LayoutInflater.from(context).inflate(R.layout.item_title, parent, false))
            TYPE_USER -> UserHolder(LayoutInflater.from(context).inflate(R.layout.item_user_connection, parent, false))
            TYPE_REQUEST -> RequestHolder(LayoutInflater.from(context).inflate(R.layout.item_request_connection, parent, false))
            else -> null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder?.apply {
            when (getItemViewType(position)) {
                TYPE_USER -> initUserItem(position, holder, itemView.context)
                TYPE_REQUEST -> initRequestItem(position, holder, itemView.context)
                TYPE_HEADER -> initHeaderItem(position, holder, itemView.context)
            }
        }
    }

    private fun initHeaderItem(position: Int, holder: RecyclerView.ViewHolder?, context: Context) {
        val header = displayableList[position] as Title
        val headerHolder = holder as HeaderHolder
        headerHolder.title.text = header.title
    }

    private fun initRequestItem(position: Int, holder: RecyclerView.ViewHolder?, context: Context) {
        val request = displayableList[position] as ConnectionRequest
        val requestHolder = holder as RequestHolder
        val params = ImageLoader.Params.create()
                .view(requestHolder.avatar)
        request.userDetails?.avatar?.smallImageUrl?.let { params.url(it) }
        DEPENDENCIES.imageLoader.load(params)

        request.updatedAt?.let {
            requestHolder.timeAgoTv.text = DateUtils.getRelativeTimeSpanString(it, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
        }
        requestHolder.nameTv.text = request.userDetails?.fullName()
        requestHolder.deletionTv.text = context.getString(R.string.connection_revoke)

        //owner and manager can remove any request
        if (PropertyUserRole.OWNER == currentUserRole || PropertyUserRole.AGENT == currentUserRole) {
            requestHolder.canBeRemoved = true
        }
    }

    private fun initUserItem(position: Int, holder: RecyclerView.ViewHolder?, context: Context) {
        val user = displayableList[position] as PropertyUser
        val userHolder = holder as UserHolder
        val params = ImageLoader.Params.create()
                .view(userHolder.avatar)
        DEPENDENCIES.imageLoader.load(params)
        user.userDetails?.avatar?.smallImageUrl?.let { params.url(it) }
        userHolder.nameTv.text = user.userDetails?.fullName()

        if (user.userDetails?.id == currentUserId) {
            userHolder.deletionTv.text = context.getString(R.string.connection_leave)
        }

        if (PropertyUserRole.OWNER == user.role) {
            userHolder.nameTv.text = context.getString(R.string.connection_owner_name, user.userDetails?.fullName())
        } else if (PropertyUserRole.AGENT == user.role) {
            userHolder.nameTv.text = context.getString(R.string.connection_agent_name, user.userDetails?.fullName())
        } else {
            userHolder.nameTv.text = context.getString(R.string.connection_guest_name, user.userDetails?.fullName())
        }

        if (PropertyUserRole.OWNER == currentUserRole) {
            //owner can remove any user
            userHolder.canBeRemoved = true
        } else if (PropertyUserRole.AGENT == currentUserRole && PropertyUserRole.OWNER != user.role) {
            //agent can delete any user except of owner
            userHolder.canBeRemoved = true
        } else if (user.userDetails?.id == currentUserId) {
            //guest can only remove own connection
            userHolder.canBeRemoved = true
        }
    }

    override fun getItemCount() = displayableList.size

    override fun getItemViewType(position: Int) = when (displayableList[position]) {
        is Title -> TYPE_HEADER
        is PropertyUser -> TYPE_USER
        is ConnectionRequest -> TYPE_REQUEST
        else -> TYPE_UNDEFINED
    }

    fun setData(newConnections: List<Displayable>) {
        displayableList.clear()
        displayableList.addAll(newConnections)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        displayableList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getConnectionAt(position: Int) = if (position < displayableList.size) displayableList[position] else null

    fun setUserCurrentRole(role: String) {
        this.currentUserRole = role
    }

    inner class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title = itemView.findViewById<TextView>(R.id.title)
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var canBeRemoved = false
        internal var avatar = itemView.findViewById<ImageView>(R.id.avatar)
        internal var nameTv = itemView.findViewById<TextView>(R.id.name)
        internal var deletionTv = itemView.findViewById<TextView>(R.id.deletion_text)
        internal var viewForeground = itemView.findViewById<LinearLayout>(R.id.view_foreground)
    }

    inner class RequestHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var canBeRemoved = false
        internal var avatar = itemView.findViewById<ImageView>(R.id.avatar)
        internal var nameTv = itemView.findViewById<TextView>(R.id.name)
        internal var timeAgoTv = itemView.findViewById<TextView>(R.id.timeAgo)
        internal var deletionTv = itemView.findViewById<TextView>(R.id.deletion_text)
        internal var viewForeground = itemView.findViewById<LinearLayout>(R.id.view_foreground)
    }
}