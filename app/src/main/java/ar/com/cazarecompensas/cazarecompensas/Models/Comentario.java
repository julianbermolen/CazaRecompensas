package ar.com.cazarecompensas.cazarecompensas.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by julia on 29/10/2017.
 */

public class Comentario implements Parcelable{
    @SerializedName("idComentario")
    private int IdComentario;
    @SerializedName("idPublicacion")
    private int IdPublicacion;
    @SerializedName("idUsuario")
    private int IdUsuario;
    @SerializedName("detalle")
    private String Detalle;
    @SerializedName("idRespuestaComentario")
    private int IdRespuestaComentario;
    @SerializedName("imagen")
    private String Imagen;
    @SerializedName("mensajeLeido")
    private Boolean MensajeLeido;
    @SerializedName("fechaCarga")
    private String FechaCarga;
    @SerializedName("publicacion")
    private Publicacion Publicacion;
    @SerializedName("usuario")
    private Usuario usuario;


    public boolean getMensajeLeido(){
        return MensajeLeido;
    }
    public void setMensajeLeido(boolean mensajeLeido) {
        MensajeLeido = mensajeLeido;
    }

    public Comentario(){

    }
    protected Comentario(Parcel in) {
        IdComentario = in.readInt();
        IdPublicacion = in.readInt();
        IdUsuario = in.readInt();
        Detalle = in.readString();
        IdRespuestaComentario = in.readInt();
        Imagen = in.readString();
        MensajeLeido = in.readByte() != 0;
        usuario = in.readParcelable(Usuario.class.getClassLoader());
        FechaCarga = in.readString();
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
    public String getFechaCarga() {
        return FechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        FechaCarga = fechaCarga;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public static final Creator<Comentario> CREATOR = new Creator<Comentario>() {
        @Override
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }

        @Override
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };

    public int getIdComentario() {
        return IdComentario;
    }

    public void setIdComentario(int idComentario) {
        IdComentario = idComentario;
    }

    public int getIdPublicacion() {
        return IdPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        IdPublicacion = idPublicacion;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getComentario() {
        return Detalle;
    }

    public void setComentario(String comentario) {
        Detalle = comentario;
    }

    public int getIdRespuesta() {
        return IdRespuestaComentario;
    }

    public void setIdRespuesta(int idRespuesta) {
        IdRespuestaComentario = idRespuesta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdComentario);
        dest.writeInt(IdPublicacion);
        dest.writeInt(IdUsuario);
        dest.writeString(Detalle);
        dest.writeInt(IdRespuestaComentario);
        dest.writeString(Imagen);
        dest.writeByte((byte) (MensajeLeido ? 1 : 0));
        dest.writeParcelable(usuario, flags);
        dest.writeString(FechaCarga);
    }
}
