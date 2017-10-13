package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode

class InspectionTimeActivity : AbsActivity(), InspectionTimeContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.inspectionTimes)
    lateinit var inspectionTimeList: RecyclerView

    private lateinit var inspectionTimeAdapter: InspectionTimeAdapter
    private lateinit var presentable: InspectionTimeContract.ViewPresentable
    private lateinit var presenter: InspectionTimePresenter

    companion object {
        private val KEY_PROPERTY = "KEY_PROPERTY"

        fun createIntent(context: Context, property: Property): Intent {
            val intent = Intent(context, InspectionTimeActivity::class.java)
            intent.putExtra(KEY_PROPERTY, property)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection_time)
        ButterKnife.bind(this)
        initToolbar()
        initView()

        presenter = InspectionTimePresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.NEW_INSPECTION_TIME) {
            data?.extras?.let {
                presentable.onInspectionTimesUpdated(it.getParcelable<InspectionTime>(NavigatorImpl.KEY_INSPECTION_TIME),
                        it.getBoolean(NavigatorImpl.KEY_INSPECTION_TIME_IS_CREATED))
            }
        }
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: InspectionTimeContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun getPropertyFromExtras() = intent.extras?.getParcelable<Property>(InspectionTimeActivity.KEY_PROPERTY)

    override fun setInspectionList(inspectionTimes: List<InspectionTime>) {
        inspectionTimeAdapter.setData(inspectionTimes)
    }

    override fun setResult(property: Property?) {
        val intent = Intent()
        intent.putExtra(KEY_PROPERTY, property)
        this.setResult(Activity.RESULT_OK, intent)
    }

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.inspection_time_toolbar)
        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }
        toolbar.setOnMenuItemClickListener { item ->
            if (R.id.action_add == item.itemId) {
                presentable.onAddTimeClicked()
            }
            false
        }
    }

    private fun initView() {
        inspectionTimeAdapter = InspectionTimeAdapter(this)
        inspectionTimeAdapter.setOnItemClickListener(object : InspectionTimeAdapter.OnItemClickListener {
            override fun onTimeClicked(inspectionTime: InspectionTime) {
                presentable.onInspectionTimeClicked(inspectionTime)
            }
        })

        with(inspectionTimeList) {
            setHasFixedSize(true)
            adapter = inspectionTimeAdapter
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        }
    }
}