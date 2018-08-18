package com.vtt.musiconline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("id_album")
    @Expose
    private String idAlbum;
    @SerializedName("name_album")
    @Expose
    private String nameAlbum;
    @SerializedName("singer_name_album")
    @Expose
    private String singerNameAlbum;
    @SerializedName("image_album")
    @Expose
    private String imageAlbum;

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public String getSingerNameAlbum() {
        return singerNameAlbum;
    }

    public void setSingerNameAlbum(String singerNameAlbum) {
        this.singerNameAlbum = singerNameAlbum;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }

}