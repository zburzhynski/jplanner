package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Application setting.
 * <p/>
 * Date: 09.06.15
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(schema = "jplanner", name = "setting")
public class Setting extends Domain {

    public static final String P_NAME = "name";
    public static final String P_CATEGORY = "category";
    public static final String P_VALUE = "value";
    public static final String P_TYPE = "type";
    public static final String P_SORT_ORDER = "sortOrder";

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private SettingCategory category;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private SettingType type;

    @Column(name = "sort_order")
    private Integer sortOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SettingCategory getCategory() {
        return category;
    }

    public void setCategory(SettingCategory category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SettingType getType() {
        return type;
    }

    public void setType(SettingType type) {
        this.type = type;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Cabinet)) {
            return false;
        }

        Setting that = (Setting) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(category, that.category)
            .append(value, that.value)
            .append(type, that.type)
            .append(sortOrder, that.sortOrder)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(category)
            .append(value)
            .append(type)
            .append(sortOrder)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("category", category)
            .append("value", value)
            .append("type", type)
            .append("sortOrder", sortOrder)
            .toString();
    }

}
