package com.vtt.musiconline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist {

    @SerializedName("id_playlist")
    @Expose
    private String idPlaylist;
    @SerializedName("name_playlist")
    @Expose
    private String namePlaylist;
    @SerializedName("background_image_playlist")
    @Expose
    private String backgroundImagePlaylist;
    @SerializedName("icon_image_playlist")
    @Expose
    private String iconImagePlaylist;

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

    public String getBackgroundImagePlaylist() {
        return backgroundImagePlaylist;
    }

    public void setBackgroundImagePlaylist(String backgroundImagePlaylist) {
        this.backgroundImagePlaylist = backgroundImagePlaylist;
    }

    public String getIconImagePlaylist() {
        return iconImagePlaylist;
    }

    public void setIconImagePlaylist(String iconImagePlaylist) {
        this.iconImagePlaylist = iconImagePlaylist;
    }

}