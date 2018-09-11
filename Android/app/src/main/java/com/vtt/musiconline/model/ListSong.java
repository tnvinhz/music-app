package com.vtt.musiconline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListSong {

    @SerializedName("id_song")
    @Expose
    private String idSong;
    @SerializedName("id_album")
    @Expose
    private String idAlbum;
    @SerializedName("id_category")
    @Expose
    private String idCategory;
    @SerializedName("id_playlist")
    @Expose
    private String idPlaylist;
    @SerializedName("name_song")
    @Expose
    private String nameSong;
    @SerializedName("image_song")
    @Expose
    private String imageSong;
    @SerializedName("singer_song")
    @Expose
    private String singerSong;
    @SerializedName("link_song")
    @Expose
    private String linkSong;

    private Boolean isPlay = false;

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getImageSong() {
        return imageSong;
    }

    public void setImageSong(String imageSong) {
        this.imageSong = imageSong;
    }

    public String getSingerSong() {
        return singerSong;
    }

    public void setSingerSong(String singerSong) {
        this.singerSong = singerSong;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    public Boolean getPlay() {
        return isPlay;
    }

    public void setPlay(Boolean play) {
        isPlay = play;
    }
}
