package com.soho.sohoapp.feature.home.editproperty.connections

import android.app.Activity
import android.content.DialogInterface.*
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyConnections
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode


class EditConnectionFragment : BaseFragment(), EditConnectionContract.ViewInteractable {

    @BindView(R.id.connections)
    lateinit var rvConnections: RecyclerView

    private lateinit var presentable: EditConnectionContract.ViewPresentable
    private lateinit var presenter: EditConnectionPresenter
    private lateinit var connectionsAdapter: ConnectionsAdapter
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private const val KEY_PROPERTY = "KEY_PROPERTY"
        private const val KEY_CONNECTIONS = "KEY_CONNECTIONS"

        fun newInstance(property: Property, connections: PropertyConnections) = EditConnectionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_PROPERTY, property)
                putParcelable(KEY_CONNECTIONS, connections)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_connection, container, false)
        ButterKnife.bind(this, view)

        initView()
        presenter = EditConnectionPresenter(this, NavigatorImpl.newInstance(this), resources)
        presenter.startPresenting(savedInstanceState != null)
        return view
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.EDIT_PROPERTY_NEW_CONNECTION) {
            data?.getParcelableExtra<ConnectionRequest>(NavigatorImpl.KEY_CONNECTION_REQUEST)?.let {
                presentable.onNewRequestCreated(it)
            }
        }
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: EditConnectionContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun getPropertyFromExtras(): Property = arguments.getParcelable(KEY_PROPERTY)

    override fun getConnectionsFromExtras(): PropertyConnections = arguments.getParcelable(KEY_CONNECTIONS)

    override fun setData(data: List<Displayable>) {
        connectionsAdapter.setData(data)
    }

    private val dialogClickListener = OnClickListener { dialog, which ->
        when (which) {
            BUTTON_POSITIVE -> presentable.onConfirmDeletionClicked()
            BUTTON_NEGATIVE -> {
                dialog.dismiss()
                notifyDataSetChanged()
            }
        }
    }

    override fun showConfirmationDialog(name: String?) {
        val message = if (name.isNullOrEmpty()) {
            getString(R.string.connection_confirm_unknown_deletion)
        } else {
            getString(R.string.connection_confirm_deletion, name)
        }
        AlertDialog.Builder(context).setMessage(message)
                .setTitle(R.string.connection_confirmation)
                .setPositiveButton(R.string.connection_confirm_deletion_yes, dialogClickListener)
                .setNegativeButton(R.string.connection_confirm_deletion_no, dialogClickListener)
                .setCancelable(false)
                .show()
    }

    override fun removeItemFromList(position: Int) {
        connectionsAdapter.removeItem(position)
    }

    override fun notifyDataSetChanged() {
        connectionsAdapter.notifyDataSetChanged()
    }

    override fun showLoadingDialog() {
        loadingDialog = LoadingDialog(context)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun setUserCurrentRole(role: String) {
        connectionsAdapter.setUserCurrentRole(role)
    }

    @OnClick(R.id.add_connection)
    fun onAddConnectionClicked() {
        presentable.onAddConnectionClicked()
    }

    private fun initView() {
        connectionsAdapter = ConnectionsAdapter(this.context)

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
                connectionsAdapter.getConnectionAt(viewHolder.adapterPosition)?.let {
                    presentable.onConnectionSwipe(viewHolder.adapterPosition, it)
                }
            }

        })
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvConnections)

        with(rvConnections) {
            setHasFixedSize(true)
            adapter = connectionsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        }
    }
}