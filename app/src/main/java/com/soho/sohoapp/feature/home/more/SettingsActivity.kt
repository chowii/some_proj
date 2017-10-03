package com.soho.sohoapp.feature.home.more

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.adapter.SettingsAdapter
import com.soho.sohoapp.feature.home.more.contract.SettingsContract
import com.soho.sohoapp.feature.home.more.presenter.SettingsPresenter

/**
 * Created by chowii on 10/09/17.
 */
class SettingsActivity: AppCompatActivity(), SettingsContract.ViewInteractable, SettingsAdapter.OnSettingsItemClickListener {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    lateinit var presenter: SettingsPresenter

    private lateinit var adapter: SettingsAdapter

    companion object {
        val CAMERA_INTENT_REQUEST_CODE: Int = 12312

        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)


        configureAdapter()
        presenter = SettingsPresenter(this, this)
        presenter.startPresenting()
        presenter.retrieveAccount()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopPresenting()
    }

    override fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
    }

    fun configureAdapter() {
        adapter = SettingsAdapter(listOf(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun updateAdapterDataset(dataset: List<BaseModel>) {
        adapter.updateDataset(dataset)
        adapter.notifyDataSetChanged()
    }

    override fun onSettingsItemClicked(item: String) = presenter.onSettingsItemClicked(item)

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == CAMERA_INTENT_REQUEST_CODE && intent != null &&  intent.extras != null) {
            val photoIdBitmap: Bitmap = retrievePhotoFromIntent(intent.extras)
            photoIdBitmap.byteCount
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun retrievePhotoFromIntent(extras: Bundle?): Bitmap = extras?.get("data") as Bitmap

}