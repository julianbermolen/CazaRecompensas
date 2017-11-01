package ar.com.cazarecompensas.cazarecompensas.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julia on 30/10/2017.
 */

public class Publicacion implements Parcelable{
    @SerializedName("idPublicacion")
    private int IdPublicacion;
    @SerializedName("idTesoro")
    private int IdTesoro;
    @SerializedName("tesoro")
    private Tesoro tesoro;

    protected Publicacion(Parcel in) {
        IdPublicacion = in.readInt();
        IdTesoro = in.readInt();
        tesoro = in.readParcelable(Tesoro.class.getClassLoader());
    }

    public static final Creator<Publicacion> CREATOR = new Creator<Publicacion>() {
        @Override
        public Publicacion createFromParcel(Parcel in) {
            return new Publicacion(in);
        }

        @Override
        public Publicacion[] newArray(int size) {
            return new Publicacion[size];
        }
    };

    public int getIdPublicacion() {
        return IdPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        IdPublicacion = idPublicacion;
    }

    public int getIdTesoro() {
        return IdTesoro;
    }

    public void setIdTesoro(int idTesoro) {
        IdTesoro = idTesoro;
    }

    public Tesoro getTesoro() {
        return tesoro;
    }

    public void setTesoro(Tesoro tesoro) {
        this.tesoro = tesoro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdPublicacion);
        dest.writeInt(IdTesoro);
        dest.writeParcelable(tesoro, flags);
    }
}
