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
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.imageloader.ImageLoader
import java.io.File

class OwnershipFilesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val imageLoader = DEPENDENCIES.imageLoader
    private lateinit var listener: OnItemClickListener
    private val TYPE_ATTACHMENT = 0
    private val TYPE_FILE = 1
    private val TYPE_ADD_FILE = 2
    private val TYPE_UNDEFINED = 100
    private var displayableList = mutableListOf<Displayable>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val context = parent?.context
        return when (viewType) {
            TYPE_ATTACHMENT, TYPE_FILE -> FileHolder(LayoutInflater.from(context).inflate(R.layout.item_file, parent, false))
            TYPE_ADD_FILE -> AddFileHolder(LayoutInflater.from(context).inflate(R.layout.item_add_file, parent, false))
            else -> null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ATTACHMENT -> initAttachment(position, holder)
            TYPE_ADD_FILE -> holder?.itemView?.setOnClickListener({ listener.onAddFileClicked() })
            TYPE_FILE -> initFile(position, holder)
        }
    }

    private fun initFile(position: Int, holder: RecyclerView.ViewHolder?) {
        val file = displayableList[position] as PropertyFile
        val fileHolder = holder as FileHolder

        fileHolder.itemView.setOnClickListener({ listener.onFileClicked(file) })
        fileHolder.deleteBtn.visibility = View.GONE

        imageLoader.load(ImageLoader.Params.create()
                .height(calculateImageSize(holder.itemView.context))
                .width(calculateImageSize(holder.itemView.context))
                .centerCrop(true)
                .view(holder.photo)
                .placeHolder(R.drawable.bc_add_new_file)
                .url(file.filePhoto!!))
    }

    override fun getItemCount() = displayableList.size

    override fun getItemViewType(position: Int) = when (displayableList[position]) {
        is PropertyFile -> TYPE_FILE
        is Attachment -> TYPE_ATTACHMENT
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

    private fun initAttachment(position: Int, holder: RecyclerView.ViewHolder?) {
        val file = displayableList[position] as Attachment
        val fileHolder = holder as FileHolder
        fileHolder.deleteBtn.setOnClickListener { listener.onDeleteIconClicked(file) }

        val params = ImageLoader.Params.create()
                .view(fileHolder.photo)
                .placeHolder(file.holder)

        val imageSize = calculateImageSize(holder.itemView.context)
        params.height(imageSize)
                .width(imageSize)
                .centerCrop(true)

        //we have this check because attachment can be from gallery, camera or server
        if (file.uri != null) {
            imageLoader.load(params.file(file.uri!!))
        } else if (file.filePath != null) {
            imageLoader.load(params.file(Uri.fromFile(File(file.filePath!!))))
        } else {
            imageLoader.load(params.url(file.fileUrl!!))
        }
    }

    private fun calculateImageSize(context: Context): Int {
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
        fun onFileClicked(file: PropertyFile)
    }
}