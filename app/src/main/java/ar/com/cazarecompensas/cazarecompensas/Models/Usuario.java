package ar.com.cazarecompensas.cazarecompensas.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jbermolen on 9/10/2017.
 */

public class Usuario implements Serializable,Parcelable{
    @SerializedName("idUsuario")
    private int idUsuario;
    @SerializedName("nombre")
    private String Nombre;
    @SerializedName("apellido")
    private String Apellido;
    @SerializedName("urlFoto")
    private String UrlFoto;
    @SerializedName("email")
    private String Email;
    @SerializedName("idFacebook")
    private long IdFacebook;

    protected Usuario(Parcel in) {
        idUsuario = in.readInt();
        Nombre = in.readString();
        Apellido = in.readString();
        UrlFoto = in.readString();
        Email = in.readString();
        IdFacebook = in.readLong();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + Nombre + '\'' +
                ", apellido='" + Apellido + '\'' +
                ", UrlFoto='" + UrlFoto + '\'' +
                ", Email='" + Email + '\'' +
                ", IdFacebook='" + IdFacebook + '\'' +
                '}';
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getUrlFoto() {
        return UrlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        UrlFoto = urlFoto;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getIdFacebook() {
        return IdFacebook;
    }

    public void setIdFacebook(long idFacebook) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeString(Nombre);
        dest.writeString(Apellido);
        dest.writeString(UrlFoto);
        dest.writeString(Email);
        dest.writeLong(IdFacebook);
    }
}
