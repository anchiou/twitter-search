package com.search.twitter.searcher;

public class SearchObject {
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------
    // SearchObject query
    private String query;

    // SearchObject language
    private String lang;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------
    public SearchObject() { }

    public SearchObject(String query, String lang) {
        this.query = query;
        this.lang = lang;
    }

    // Getter and setter methods

    public String getQuery() {
        return query;
    }

    public void setQuery(String value) {
        this.query = value;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String value) {
        this.lang = value;
    }

    public boolean isHashTag() {
        if (this.query.startsWith("#")) {
            return true;
        }
        return false;
    }
}
