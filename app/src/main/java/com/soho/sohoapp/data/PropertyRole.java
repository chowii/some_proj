package com.soho.sohoapp.data;

import android.support.annotation.NonNull;

public class PropertyRole {
    private String key;
    private String label;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @NonNull
    public static PropertyRole createOwnerRole() {
        PropertyRole propertyRole = new PropertyRole();
        propertyRole.setKey("owner");
        propertyRole.setLabel("Owner");
        return propertyRole;
    }

    @NonNull
    public static PropertyRole createAgentRole() {
        PropertyRole propertyRole = new PropertyRole();
        propertyRole.setKey("agent");
        propertyRole.setLabel("Agent");
        return propertyRole;
    }
}
