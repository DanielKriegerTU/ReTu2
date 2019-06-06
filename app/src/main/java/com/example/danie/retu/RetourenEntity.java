package com.example.danie.retu;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "retouren")
public class RetourenEntity {



    @PrimaryKey()
   @NonNull
    private String retoureID;

    private String paketgroesse;
    private String datum;
    private String abgabeort;

    @NonNull
    public String getRetoureID() {
        return retoureID;
    }

    public void setRetoureID(@NonNull String retoureID) {
        this.retoureID = retoureID;
    }



    public String getPaketgroesse() {
        return paketgroesse;
    }

    public void setPaketgroesse(String paketgroesse) {
        this.paketgroesse = paketgroesse;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getAbgabeort() {
        return abgabeort;
    }

    public void setAbgabeort(String abgabeort) {
        this.abgabeort = abgabeort;
    }
}
