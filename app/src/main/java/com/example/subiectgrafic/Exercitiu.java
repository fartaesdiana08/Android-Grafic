package com.example.subiectgrafic;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercitiu implements Parcelable {
    private String denumire;
    private Grupa grupa;  //spinner
    private int nrRepetari;  //seekbar
    private int numarCaloriiArse;
    private String indicatii;

    public Exercitiu(String denumire, Grupa grupa, int nrRepetari, int numarCaloriiArse, String indicatii) {
        this.denumire = denumire;
        this.grupa = grupa;
        this.nrRepetari = nrRepetari;
        this.numarCaloriiArse = numarCaloriiArse;
        this.indicatii = indicatii;
    }

    protected Exercitiu(Parcel in) {
        denumire = in.readString();
        grupa=Grupa.valueOf(in.readString());
        nrRepetari = in.readInt();
        numarCaloriiArse = in.readInt();
        indicatii = in.readString();
    }

    public static final Creator<Exercitiu> CREATOR = new Creator<Exercitiu>() {
        @Override
        public Exercitiu createFromParcel(Parcel in) {
            return new Exercitiu(in);
        }

        @Override
        public Exercitiu[] newArray(int size) {
            return new Exercitiu[size];
        }
    };

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public int getNrRepetari() {
        return nrRepetari;
    }

    public void setNrRepetari(int nrRepetari) {
        this.nrRepetari = nrRepetari;
    }

    public int getNumarCaloriiArse() {
        return numarCaloriiArse;
    }

    public void setNumarCaloriiArse(int numarCaloriiArse) {
        this.numarCaloriiArse = numarCaloriiArse;
    }

    public String getIndicatii() {
        return indicatii;
    }

    public void setIndicatii(String indicatii) {
        this.indicatii = indicatii;
    }

    @Override
    public String toString() {
        return "Exercitiu{" +
                "denumire='" + denumire + '\'' +
                ", grupa=" + grupa +
                ", nrRepetari=" + nrRepetari +
                ", numarCaloriiArse=" + numarCaloriiArse +
                ", indicatii='" + indicatii + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeString(String.valueOf(grupa));
        dest.writeInt(nrRepetari);
        dest.writeInt(numarCaloriiArse);
        dest.writeString(indicatii);
    }
}
