package com.soho.sohoapp.feature.marketplaceview.feature.filters.savedfilters

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs
import com.soho.sohoapp.landing.BaseFragment
import com.soho.sohoapp.extensions.addLeftSwipeToDelete

/**
 * Created by chowii on 25/8/17.
 */
class SavedFiltersFragment : BaseFragment(), SavedFiltersContract.ViewInteractable {

    @BindView(R.id.rootView)
    lateinit var rootView : RelativeLayout

    @BindView(R.id.recyclerView)
    lateinit var recyclerView : RecyclerView

    @BindView(R.id.progress_bar)
    lateinit var progressBar : ProgressBar

    @BindView(R.id.textview_empty_dataset_message)
    lateinit var textViewEmptyDataset : TextView

    private lateinit var presenter: SavedFiltersPresenter
    private lateinit var presentable: SavedFiltersContract.ViewPresentable
    private var adapter: PropertyFilterSavedAdapter? = null
    lateinit var onClickListener: PropertyFilterSavedAdapter.PropertyFilterOnClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_filter_saved, container, false)
        ButterKnife.bind(this, view)
        presenter = SavedFiltersPresenter(this)
        presenter.startPresenting(false)
        initView()
        return view
    }

    private fun initView() {
        recyclerView.addLeftSwipeToDelete(R.color.redPrimary, R.drawable.ic_delete_white_24dp) {
            adapter?.getFilterAt(it)?.let { filter ->
                presentable.onDeleteFilter(filter)
            }
        }
    }

    // MARK: - ================== SavedFiltersContract.ViewInteractable methods ==================

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: SavedFiltersContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun updateDataSet(dataSet: List<MarketplaceFilterWithSuburbs>) {
        if (adapter == null) {
            adapter = PropertyFilterSavedAdapter(onClickListener)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        }
        recyclerView.adapter = adapter
        adapter?.updateDataSet(dataSet)
        textViewEmptyDataset.visibility = if(dataSet.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun toggleLoadingIndicator(loading: Boolean) {
        progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    override fun showMessage(stringRes: Int) {
        showSnackBar(getString(stringRes), rootView)
    }

    companion object {
        fun newInstance(onClickListener: PropertyFilterSavedAdapter.PropertyFilterOnClickListener): SavedFiltersFragment {
            val fragment = SavedFiltersFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.onClickListener = onClickListener
            return fragment
        }
    }
}

