package com.epam.esm.service.criteria;

public class GiftCertificateCriteria {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String NAME = "name";
    public static final String DATE = "date";

    private String tagName;
    private String partName;
    private String partDesc;
    private String sortBy;
    private String sortOrder = ASC;

    public GiftCertificateCriteria() {
    }

    public GiftCertificateCriteria(String tagName, String partName,
                                   String partDesc, String sortBy, String sortOrder) {
        this.tagName = tagName;
        this.partName = partName;
        this.partDesc = partDesc;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartDesc() {
        return partDesc;
    }

    public void setPartDesc(String partDesc) {
        this.partDesc = partDesc;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isEmpty() {
        return tagName == null && partName == null && partDesc == null && sortBy == null && sortOrder == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificateCriteria that = (GiftCertificateCriteria) o;

        if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;
        if (partName != null ? !partName.equals(that.partName) : that.partName != null) return false;
        if (partDesc != null ? !partDesc.equals(that.partDesc) : that.partDesc != null) return false;
        if (sortBy != null ? !sortBy.equals(that.sortBy) : that.sortBy != null) return false;
        return sortOrder != null ? sortOrder.equals(that.sortOrder) : that.sortOrder == null;
    }

    @Override
    public int hashCode() {
        int result = tagName != null ? tagName.hashCode() : 0;
        result = 31 * result + (partName != null ? partName.hashCode() : 0);
        result = 31 * result + (partDesc != null ? partDesc.hashCode() : 0);
        result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
        result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GiftCertificateCriteria{");
        sb.append("tagName='").append(tagName).append('\'');
        sb.append(", partName='").append(partName).append('\'');
        sb.append(", partDesc='").append(partDesc).append('\'');
        sb.append(", sortBy='").append(sortBy).append('\'');
        sb.append(", sortOrder='").append(sortOrder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
