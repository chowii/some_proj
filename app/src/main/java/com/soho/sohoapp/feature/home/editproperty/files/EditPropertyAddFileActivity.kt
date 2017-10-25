package com.soho.sohoapp.feature.home.editproperty.files

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.marchinram.rxgallery.RxGallery
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.customviews.LabeledValueField
import com.soho.sohoapp.data.enums.FileCostType
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.data.models.SohoOption
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.extensions.afterTextChanged
import com.soho.sohoapp.extensions.getParcelableOrNull
import com.soho.sohoapp.feature.common.OptionModalBottomSheet
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.FileHelper

class EditPropertyAddFileActivity : AbsActivity(),
        EditPropertyAddFileContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.scrollview_form)
    lateinit var scrollViewForm: ScrollView

    @BindView(R.id.btn_add_file)
    lateinit var buttonAddFile: Button

    @BindView(R.id.btn_file_type)
    lateinit var buttonFileType: Button

    @BindView(R.id.et_other)
    lateinit var editTextOther: LabeledValueField

    @BindView(R.id.rg_income_or_expense)
    lateinit var radioGroupIncomeOrExpense: RadioGroup

    @BindView(R.id.et_amount)
    lateinit var editTextAmount: LabeledValueField

    @BindView(R.id.iv_file)
    lateinit var imageViewFile: ImageView

    lateinit var presenter: EditPropertyAddFilePresenter
    lateinit var presentable: EditPropertyAddFileContract.ViewPresentable
    private lateinit var loadingDialog: LoadingDialog

    companion object {
        const val EXTRA_FILE: String = "EXTRA_FILE"
        const val EXTRA_PROPERTY: String = "EXTRA_PROPERTY"
        const val BOTTOM_MODAL_SHEET_TAG: String = "BOTTOM_MODAL_SHEET_TAG"

        @JvmStatic
        fun createIntent(context: Context, property: Property, propertyFile: PropertyFile?): Intent =
                Intent(context, EditPropertyAddFileActivity::class.java)
                        .putExtra(EXTRA_FILE, propertyFile)
                        .putExtra(EXTRA_PROPERTY, property)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_property_add_file)
        ButterKnife.bind(this)
        initView()
        intent.extras?.getParcelableOrNull<Property>(EXTRA_PROPERTY)?.let { property ->
            presenter = EditPropertyAddFilePresenter(this,
                    intent.extras?.getParcelableOrNull(EXTRA_FILE) ?: PropertyFile(),
                    property,
                    NavigatorImpl.newInstance(this),
                    RxGallery.gallery(this, false, RxGallery.MimeType.IMAGE),
                    RxGallery.photoCapture(this),
                    PermissionManagerImpl.newInstance(this),
                    AddPhotoDialog(this),
                    FileHelper.newInstance(this))
            presenter.startPresenting()
        }
    }

    private fun initView() {
        loadingDialog = LoadingDialog(this)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }
        editTextAmount.etValue.afterTextChanged { text ->
            presentable.getCurrentPropertyFile().amount = text.toString().toFloatOrNull()
        }
        editTextOther.etValue.afterTextChanged { text ->
            presentable.getCurrentPropertyFile().documentType = text.toString()
        }
        scrollViewForm.visibility = View.GONE
    }

    private fun fileTypeSelected(selectedType: String) {
        buttonFileType.text = selectedType
        presentable.getFileTypesList().firstOrNull { fileType -> fileType.label.equals(selectedType) }
                ?.let { fileType ->
                    radioGroupIncomeOrExpense.check(if (fileType.category?.equals(FileCostType.EXPENSE, true) != false) R.id.rb_expense else R.id.rb_income)
                    if (fileType.key.equals(getString(R.string.file_type_other), true)) {
                        styleForOtherFileTypeSelected()
                    } else {
                        editTextOther.visibility = View.GONE
                        presentable.getCurrentPropertyFile().documentType = fileType.key
                    }
                } ?: styleForOtherFileTypeSelected()
    }

    private fun styleForOtherFileTypeSelected() {
        editTextOther.visibility = View.VISIBLE
        radioGroupIncomeOrExpense.clearCheck()
        presentable.getCurrentPropertyFile().apply {
            isCost = null
            documentType = editTextOther.etValue.text.toString()
        }
    }

    // MARK: - ================== EditPropertyAddFileContract.ViewInteractable methods ==================

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setViewPresentable(presentable: EditPropertyAddFileContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun setupForm(currentFile: PropertyFile, fileTypes: List<SohoOption>) {
        if (!currentFile.filePhoto.isNullOrEmpty()) {
            DEPENDENCIES.imageLoader.load(ImageLoader.Params.create()
                    .url(currentFile.filePhoto ?: "")
                    .view(imageViewFile))
        }
        scrollViewForm.visibility = View.VISIBLE
        toolbar.title = if (currentFile.id == null) getString(R.string.edit_property_file_add_file_title) else getString(R.string.edit_property_edit_file_title)
        editTextAmount.etValue.setText(currentFile.amount?.toString() ?: "")
        currentFile.isCost?.let {
            radioGroupIncomeOrExpense.check(if (it) R.id.rb_expense else R.id.rb_income)
        }
        fileTypes.firstOrNull { fileType -> fileType.key.equals(currentFile.documentType, true) }
                ?.let { fileType ->
                    populateFormWithFileType(fileType)
                } ?: populateFormForOtherFileType(currentFile)
    }

    private fun populateFormWithFileType(option: SohoOption) {
        editTextOther.visibility = View.GONE
        buttonFileType.text = option.label
    }

    private fun populateFormForOtherFileType(currentFile: PropertyFile) {
        buttonFileType.text = getString(R.string.file_type_other)
        editTextOther.visibility = View.VISIBLE
        editTextOther.etValue.setText(currentFile.documentType ?: "")
    }

    override fun toggleRetrievingFileTypes(retrieving: Boolean) {
        progressBar.visibility = if (retrieving) View.VISIBLE else View.GONE
    }

    override fun toggleSendingFileIndicator(sending: Boolean) {
        if (sending) loadingDialog.show(getString(R.string.common_loading)) else loadingDialog.dismiss()
    }

    override fun fileSelected(uri: Uri) {
        DEPENDENCIES.imageLoader.load(ImageLoader.Params.create()
                .file(uri)
                .view(imageViewFile)
                .height(imageViewFile.height)
                .width(imageViewFile.width)
                .centerCrop(true))
    }

    override fun showToastMessage(message: Int) {
        showToast(message)
    }
    // MARK: - ================== Listeners ==================

    @OnCheckedChanged(R.id.rb_expense, R.id.rb_income)
    fun onIncomeOrExpenseSelected(button: CompoundButton, checked: Boolean) {
        if (checked) {
            presentable.getCurrentPropertyFile().isCost = button.id == R.id.rb_expense
        }
    }

    @OnClick(R.id.btn_add_file)
    fun onAddFileClicked(view: View) {
        presentable.onAddFileClicked()
    }

    @OnClick(R.id.btn_file_type)
    fun onFileTypeClicked(view: View) {
        OptionModalBottomSheet(presentable.getFileTypesList().map { it.label ?: "" }, getString(R.string.add_file_options),
                { selectedOption ->
                    fileTypeSelected(selectedOption)
                }).show(supportFragmentManager, BOTTOM_MODAL_SHEET_TAG)
    }

    @OnClick(R.id.btn_save)
    fun onSaveClicked(view: View) {
        presentable.onSaveClicked()
    }
}

