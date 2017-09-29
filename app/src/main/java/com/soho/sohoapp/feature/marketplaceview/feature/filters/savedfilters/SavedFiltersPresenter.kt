package com.soho.sohoapp.feature.marketplaceview.feature.filters.savedfilters

import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.database.deleteReactive
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

internal class SavedFiltersPresenter
(private val interactable: SavedFiltersContract.ViewInteractable) : AbsPresenter, SavedFiltersContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var filters: List<MarketplaceFilterWithSuburbs>? = null

    // MARK: - ================== General methods ==================

    private fun retrieveFiltersFromDb() {
        interactable.toggleLoadingIndicator(true)
        compositeDisposable.add(Dependencies.DEPENDENCIES.database.marketplaceFilterDao().getAllNonCurrentFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            interactable.toggleLoadingIndicator(false)
                            this.filters = it
                            interactable.updateDataSet(this.filters ?: ArrayList())
                        },
                        { throwable ->
                            interactable.toggleLoadingIndicator(false)
                            interactable.showError(throwable)
                        },
                        {
                            interactable.toggleLoadingIndicator(false)
                            if (this.filters == null) {
                                this.filters = ArrayList()
                                interactable.updateDataSet(this.filters!!)
                            }
                        })
        )
    }

    // MARK: - ================== SavedFiltersContract.ViewPresentable  methods ==================

    override fun startPresenting(fromConfigChanges: Boolean) {
        interactable.setPresentable(this)
        retrieveFiltersFromDb()
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onDeleteFilter(filter: MarketplaceFilterWithSuburbs) {
        compositeDisposable.add(Dependencies.DEPENDENCIES.database.deleteReactive(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            filters = filters?.filter { it.marketplaceFilter.id != filter.marketplaceFilter.id }
                            interactable.updateDataSet(filters ?: ArrayList())
                            interactable.showMessage(R.string.filters_delete_saved_successful_message)
                        },
                        { throwable ->
                            interactable.showError(throwable)
                        })
        )
    }
}