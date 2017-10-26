package ar.com.cazarecompensas.cazarecompensas.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by julia on 16/10/2017.
 */

public class Tesoro implements Parcelable{
    @SerializedName("idTesoro")
    private int IdTesoro;
    @SerializedName("nombre")
    private String Nombre;
    @SerializedName("descripcion")
    private String Descripcion;
    @SerializedName("recompensa")
    private float Recompensa;
    @SerializedName("latitud")
    private String Latitud;
    @SerializedName("longitud")
    private String Longitud;
    @SerializedName("imagen1")
    private String Imagen1;
    @SerializedName("imagen2")
    private String Imagen2;
    @SerializedName("imagen3")
    private String Imagen3;
    @SerializedName("idTesoroCategoria")
    private long IdTesoroCategoria;
    @SerializedName("idTesoroEstado")
    private int IdTesoroEstado;
    @SerializedName("usuario")
    private Usuario Usuario;
    @SerializedName("idUsuario")
    private int IdUsuario;
    @SerializedName("idFacebook")
    private long IdFacebook;

    public Tesoro(){

        }

    protected Tesoro(Parcel in) {
        IdTesoro = in.readInt();
        Nombre = in.readString();
        Descripcion = in.readString();
        Recompensa = in.readFloat();
        Latitud = in.readString();
        Longitud = in.readString();
        Imagen1 = in.readString();
        Imagen2 = in.readString();
        Imagen3 = in.readString();
        IdTesoroCategoria = in.readLong();
        IdTesoroEstado = in.readInt();
        IdUsuario = in.readInt();
        IdFacebook = in.readLong();
    }

    public static final Creator<Tesoro> CREATOR = new Creator<Tesoro>() {
        @Override
        public Tesoro createFromParcel(Parcel in) {
            return new Tesoro(in);
        }

        @Override
        public Tesoro[] newArray(int size) {
            return new Tesoro[size];
        }
    };

    @Override
    public String toString() {
        return "Tesoro{" +
                "Nombre='" + Nombre + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Recompensa=" + Recompensa +
                ", Imagen1=" + Imagen1 +
                ", Imagen2=" + Imagen2 +
                ", Imagen3=" + Imagen3 +
                ", IdTesoroCategoria=" + IdTesoroCategoria +
                ", IdUsuario=" + IdUsuario +
                '}';
    }
    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public ar.com.cazarecompensas.cazarecompensas.Models.Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(ar.com.cazarecompensas.cazarecompensas.Models.Usuario usuario) {
        Usuario = usuario;
    }
    public int getIdTesoro() {
        return IdTesoro;
    }

    public void setIdTesoro(int idTesoro) {
        IdTesoro = idTesoro;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public float getRecompensa() {
        return Recompensa;
    }

    public void setRecompensa(float recompensa) {
        Recompensa = recompensa;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getImagen1() {
        return Imagen1;
    }

    public void setImagen1(String imagen1) {
        Imagen1 = imagen1;
    }

    public String getImagen2() {
        return Imagen2;
    }

    public void setImagen2(String imagen2) {
        Imagen2 = imagen2;
    }

    public String getImagen3() {
        return Imagen3;
    }

    public void setImagen3(String imagen3) {
        Imagen3 = imagen3;
    }

    public long getIdTesoroCategoria() {
        return IdTesoroCategoria;
    }

    public void setIdTesoroCategoria(long idTesoroCategoria) {
        IdTesoroCategoria = idTesoroCategoria;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdTesoro);
        dest.writeString(Nombre);
        dest.writeString(Descripcion);
        dest.writeFloat(Recompensa);
        dest.writeString(Latitud);
        dest.writeString(Longitud);
        dest.writeString(Imagen1);
        dest.writeString(Imagen2);
        dest.writeString(Imagen3);
        dest.writeLong(IdTesoroCategoria);
        dest.writeInt(IdTesoroEstado);
        dest.writeInt(IdUsuario);
        dest.writeLong(IdFacebook);
    }
}
