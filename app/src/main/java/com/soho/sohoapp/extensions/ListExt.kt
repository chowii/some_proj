package com.soho.sohoapp.extensions

import com.soho.sohoapp.data.models.PickerItem
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType

fun List<PropertyType>.toPickerItems(): List<PickerItem> {
    val pickerItems = mutableListOf<PickerItem>()
    this.forEach(
            {
                val pickerItem = PickerItem()
                pickerItem.id = it.key
                pickerItem.displayableString = it.label

                if (PropertyType.getIcon(it.key) > 0) {
                    pickerItem.icon = PropertyType.getIcon(it.key)
                }

                pickerItems.add(pickerItem)
            })
    return pickerItems
}