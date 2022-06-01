package com.epam.esm.service.criteria;

/**
 * Class that used to hold find criteria.
 */
public class GiftCertificateCriteria {
    /**
     * The constant ASC.
     */
    public static final String ASC = "ASC";
    /**
     * The constant DESC.
     */
    public static final String DESC = "DESC";
    /**
     * The constant NAME.
     */
    public static final String NAME = "name";
    /**
     * The constant DATE.
     */
    public static final String DATE = "date";

    private String tagName;
    private String partName;
    private String partDesc;
    private String sortBy;
    private String sortOrder = ASC;

    /**
     * Instantiates a new Gift certificate criteria.
     */
    public GiftCertificateCriteria() {
    }

    /**
     * Instantiates a new Gift certificate criteria.
     *
     * @param tagName   the tag name
     * @param partName  the part name
     * @param partDesc  the part desc
     * @param sortBy    the sort by
     * @param sortOrder the sort order
     */
    public GiftCertificateCriteria(String tagName, String partName,
                                   String partDesc, String sortBy, String sortOrder) {
        this.tagName = tagName;
        this.partName = partName;
        this.partDesc = partDesc;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    /**
     * Gets tag name.
     *
     * @return the tag name
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets tag name.
     *
     * @param tagName the tag name
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Gets part name.
     *
     * @return the part name
     */
    public String getPartName() {
        return partName;
    }

    /**
     * Sets part name.
     *
     * @param partName the part name
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * Gets part desc.
     *
     * @return the part desc
     */
    public String getPartDesc() {
        return partDesc;
    }

    /**
     * Sets part desc.
     *
     * @param partDesc the part desc
     */
    public void setPartDesc(String partDesc) {
        this.partDesc = partDesc;
    }

    /**
     * Gets sort by.
     *
     * @return the sort by
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * Sets sort by.
     *
     * @param sortBy the sort by
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
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
