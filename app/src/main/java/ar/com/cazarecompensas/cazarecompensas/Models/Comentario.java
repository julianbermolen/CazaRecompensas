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
    @SerializedName("idUsuarioEmisor")
    private int IdUsuarioEmisor;
    @SerializedName("idUsuarioReceptor")
    private int IdUsuarioReceptor;
    @SerializedName("detalle")
    private String Detalle;
    @SerializedName("imagen")
    private String Imagen;
    @SerializedName("mensajeLeido")
    private Boolean MensajeLeido;
    @SerializedName("fechaCarga")
    private String FechaCarga;
    @SerializedName("numeroConversacion")
    private int NumeroConversacion;
    @SerializedName("publicacion")
    private Publicacion Publicacion;
    @SerializedName("usuarioEmisor")
    private Usuario UsuarioEmisor;
    @SerializedName("usuarioReceptor")
    private Usuario UsuarioReceptor;


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
        IdUsuarioEmisor = in.readInt();
        IdUsuarioReceptor = in.readInt();
        Detalle = in.readString();
        NumeroConversacion = in.readInt();
        Imagen = in.readString();
        MensajeLeido = in.readByte() != 0;
        UsuarioEmisor = in.readParcelable(Usuario.class.getClassLoader());
        UsuarioReceptor = in.readParcelable(Usuario.class.getClassLoader());
        Publicacion = in.readParcelable(ar.com.cazarecompensas.cazarecompensas.Models.Publicacion.class.getClassLoader());
        FechaCarga = in.readString();
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getIdUsuarioEmisor() {
        return IdUsuarioEmisor;
    }

    public void setIdUsuarioEmisor(int idUsuarioEmisor) {
        IdUsuarioEmisor = idUsuarioEmisor;
    }

    public int getIdUsuarioReceptor() {
        return IdUsuarioReceptor;
    }

    public void setIdUsuarioReceptor(int idUsuarioReceptor) {
        IdUsuarioReceptor = idUsuarioReceptor;
    }

    public int getNumeroConversacion() {
        return NumeroConversacion;
    }

    public void setNumeroConversacion(int numeroConversacion) {
        NumeroConversacion = numeroConversacion;
    }

    public Usuario getUsuarioEmisor() {
        return UsuarioEmisor;
    }

    public void setUsuarioEmisor(Usuario usuarioEmisor) {
        UsuarioEmisor = usuarioEmisor;
    }

    public Usuario getUsuarioReceptor() {
        return UsuarioReceptor;
    }

    public void setUsuarioReceptor(Usuario usuarioReceptor) {
        UsuarioReceptor = usuarioReceptor;
    }

    public String getFechaCarga() {
        return FechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        FechaCarga = fechaCarga;
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


    public String getComentario() {
        return Detalle;
    }

    public void setComentario(String comentario) {
        Detalle = comentario;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdComentario);
        dest.writeInt(IdPublicacion);
        dest.writeInt(IdUsuarioReceptor);
        dest.writeInt(IdUsuarioEmisor);
        dest.writeString(Detalle);
        dest.writeInt(NumeroConversacion);
        dest.writeString(Imagen);
        dest.writeByte((byte) (MensajeLeido ? 1 : 0));
        dest.writeParcelable(UsuarioEmisor, flags);
        dest.writeParcelable(UsuarioReceptor,flags);
        dest.writeParcelable(Publicacion,flags);
        dest.writeString(FechaCarga);
    }
}
