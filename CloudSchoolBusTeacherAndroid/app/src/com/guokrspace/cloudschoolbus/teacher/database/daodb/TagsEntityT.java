package com.guokrspace.cloudschoolbus.teacher.database.daodb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TAGS_ENTITY_T.
 */
public class TagsEntityT {

    private String tagid;
    private String tagname;
    private String tagname_en;
    private String tagnamedesc;
    private String tagnamedesc_en;
    /** Not-null value. */
    private String schoolid;
    private String pickey;

    public TagsEntityT() {
    }

    public TagsEntityT(String tagid) {
        this.tagid = tagid;
    }

    public TagsEntityT(String tagid, String tagname, String tagname_en, String tagnamedesc, String tagnamedesc_en, String schoolid, String pickey) {
        this.tagid = tagid;
        this.tagname = tagname;
        this.tagname_en = tagname_en;
        this.tagnamedesc = tagnamedesc;
        this.tagnamedesc_en = tagnamedesc_en;
        this.schoolid = schoolid;
        this.pickey = pickey;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTagname_en() {
        return tagname_en;
    }

    public void setTagname_en(String tagname_en) {
        this.tagname_en = tagname_en;
    }

    public String getTagnamedesc() {
        return tagnamedesc;
    }

    public void setTagnamedesc(String tagnamedesc) {
        this.tagnamedesc = tagnamedesc;
    }

    public String getTagnamedesc_en() {
        return tagnamedesc_en;
    }

    public void setTagnamedesc_en(String tagnamedesc_en) {
        this.tagnamedesc_en = tagnamedesc_en;
    }

    /** Not-null value. */
    public String getSchoolid() {
        return schoolid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getPickey() {
        return pickey;
    }

    public void setPickey(String pickey) {
        this.pickey = pickey;
    }

}
