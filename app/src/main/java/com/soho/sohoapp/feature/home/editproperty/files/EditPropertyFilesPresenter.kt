package com.soho.sohoapp.feature.home.editproperty.files

import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.listdata.AddFile
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode

class EditPropertyFilesPresenter(private val view: EditPropertyFilesContact.ViewInteractable,
                                 private val navigator: NavigatorInterface) : AbsPresenter, EditPropertyFilesContact.ViewPresentable {

    private lateinit var property: Property
    private val displayableList = mutableListOf<Displayable>()

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getPropertyFromExtras()
        displayableList.add(AddFile())
        displayableList.addAll(property.files)
        view.setFileList(displayableList)
    }

    override fun stopPresenting() {}

    override fun onAddFileClicked() {
        navigator.openEditPropertyAddFileScreen(property, null, RequestCode.EDIT_PROPERTY_NEW_FILE)
    }

    override fun onFileClicked(file: PropertyFile) {
        navigator.openEditPropertyPreviewFileScreen(property, file, RequestCode.EDIT_PROPERTY_PREVIEW_FILE)
    }

    override fun onPropertyFileCreated(propertyFile: PropertyFile) {
        displayableList.add(propertyFile)
        view.setFileList(displayableList)
    }

    override fun onPropertyFileUpdated(propertyFile: PropertyFile, wasDeleted: Boolean) {
        if (wasDeleted) {
            val filePosition = displayableList.indexOfFirst { it is PropertyFile && it.id == propertyFile.id }
            displayableList.removeAt(filePosition)
        } else {
            displayableList.filterIsInstance<PropertyFile>().find { propertyFile.id == it.id }?.clone(propertyFile)
        }
        view.setFileList(displayableList)
    }
}