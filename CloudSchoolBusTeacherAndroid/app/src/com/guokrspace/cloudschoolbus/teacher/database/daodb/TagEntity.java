package com.guokrspace.cloudschoolbus.teacher.database.daodb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TAG_ENTITY.
 */
public class TagEntity {

    /** Not-null value. */
    private String tagid;
    private String tagName;
    private String tagnamedesc;
    private String tagname_en;
    private String tagnamedesc_en;
    /** Not-null value. */
    private String messageid;

    public TagEntity() {
    }

    public TagEntity(String tagid) {
        this.tagid = tagid;
    }

    public TagEntity(String tagid, String tagName, String tagnamedesc, String tagname_en, String tagnamedesc_en, String messageid) {
        this.tagid = tagid;
        this.tagName = tagName;
        this.tagnamedesc = tagnamedesc;
        this.tagname_en = tagname_en;
        this.tagnamedesc_en = tagnamedesc_en;
        this.messageid = messageid;
    }

    /** Not-null value. */
    public String getTagid() {
        return tagid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagnamedesc() {
        return tagnamedesc;
    }

    public void setTagnamedesc(String tagnamedesc) {
        this.tagnamedesc = tagnamedesc;
    }

    public String getTagname_en() {
        return tagname_en;
    }

    public void setTagname_en(String tagname_en) {
        this.tagname_en = tagname_en;
    }

    public String getTagnamedesc_en() {
        return tagnamedesc_en;
    }

    public void setTagnamedesc_en(String tagnamedesc_en) {
        this.tagnamedesc_en = tagnamedesc_en;
    }

    /** Not-null value. */
    public String getMessageid() {
        return messageid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

}
