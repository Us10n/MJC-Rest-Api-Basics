package com.epam.esm.service.dto;

/**
 * Data transfer object for Tag
 */
public class TagDto {
    private long tagId;
    private String name;

    /**
     * Instantiates a new Tag dto.
     */
    public TagDto() {
    }

    /**
     * Instantiates a new Tag dto.
     *
     * @param tagId the tag id
     * @param name  the name
     */
    public TagDto(long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    /**
     * Instantiates a new Tag dto.
     *
     * @param name the name
     */
    public TagDto(String name) {
        this.name = name;
    }

    /**
     * Gets tag id.
     *
     * @return the tag id
     */
    public long getTagId() {
        return tagId;
    }

    /**
     * Sets tag id.
     *
     * @param tagId the tag id
     */
    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagDto tagDto = (TagDto) o;

        if (tagId != tagDto.tagId) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (tagId ^ (tagId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagDto{");
        sb.append("tagId=").append(tagId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
