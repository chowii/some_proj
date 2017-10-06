package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.data.listdata.AddFile
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.Attachment
import com.soho.sohoapp.imageloader.ImageLoader
import java.io.File

class OwnershipFilesAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val imageLoader = DEPENDENCIES.imageLoader
    private lateinit var listener: OnItemClickListener
    private val TYPE_FILE = 0
    private val TYPE_ADD_FILE = 1
    private val TYPE_UNDEFINED = 100
    private var displayableList = mutableListOf<Displayable>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = when (viewType) {
        TYPE_FILE -> FileHolder(LayoutInflater.from(context).inflate(R.layout.item_file, parent, false))
        TYPE_ADD_FILE -> AddFileHolder(LayoutInflater.from(context).inflate(R.layout.item_add_file, parent, false))
        else -> null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            TYPE_FILE -> {
                val file = displayableList[position] as Attachment
                val fileHolder = holder as FileHolder
                fileHolder.deleteBtn.setOnClickListener { listener.onDeleteIconClicked(file) }

                val params = ImageLoader.Params.create()
                        .view(fileHolder.photo)
                        .placeHolder(file.holder)

                val imageSize = calculateImageSize()
                params.height(imageSize)
                params.width(imageSize)

                //we have this check because attachment can be from gallery, camera or server
                if (file.uri != null) {
                    imageLoader.load(params.file(file.uri!!))
                } else if (file.filePath != null) {
                    imageLoader.load(params.file(Uri.fromFile(File(file.filePath!!))))
                } else {
                    imageLoader.load(params.url(file.fileUrl!!))
                }
            }
            TYPE_ADD_FILE -> {
                holder?.itemView?.setOnClickListener({ listener.onAddFileClicked() })
            }
        }
    }

    override fun getItemCount() = displayableList.size

    override fun getItemViewType(position: Int) = when (displayableList[position]) {
        is Attachment -> TYPE_FILE
        is AddFile -> TYPE_ADD_FILE
        else -> TYPE_UNDEFINED
    }

    fun setData(newFileList: List<Displayable>) {
        displayableList.clear()
        displayableList.addAll(newFileList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private fun calculateImageSize(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.x / 3
    }

    inner class FileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var photo: ImageView = itemView.findViewById<ImageView>(R.id.photo)
        internal var deleteBtn: ImageView = itemView.findViewById<ImageView>(R.id.delete)
    }

    inner class AddFileHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onAddFileClicked()
        fun onDeleteIconClicked(attachment: Attachment)
    }
}