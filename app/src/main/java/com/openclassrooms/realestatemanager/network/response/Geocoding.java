
package com.openclassrooms.realestatemanager.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geocoding {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("attribution")
    @Expose
    private String attribution;
    @SerializedName("licence")
    @Expose
    private String licence;
    @SerializedName("query")
    @Expose
    private String query;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
