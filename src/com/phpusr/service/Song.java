package com.phpusr.service;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 09.06.13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */

/**
 * Музыкальная композиция (Имя файла, Название, Исполнитель, Альбом, Битрейт)
 */
public class Song {
    private MP3File mp3File;

    public Song(File file) throws IOException, TagException {
        mp3File = new MP3File(file);
    }

    /** Имя файла */
    public String getFileName() {
        return mp3File.getMp3file().getName();
    }

    /** Название композиции */
    public String getTitle() {
        if (isIssetTags()) {
            return mp3File.getID3v1Tag().getSongTitle() != null ? mp3File.getID3v1Tag().getSongTitle() : mp3File.getID3v2Tag().getSongTitle();
        } else {
            return null;
        }
    }

    /** Исполнитель */
    public String getArtist() {
        if (isIssetTags()) {
            return mp3File.getID3v1Tag().getLeadArtist() != null ? mp3File.getID3v1Tag().getLeadArtist() : mp3File.getID3v2Tag().getLeadArtist();
        } else {
            return null;
        }
    }

    /** Альбом */
    public String getAlbum() {
        if (isIssetTags()) {
            return mp3File.getID3v1Tag().getAlbumTitle() != null ? mp3File.getID3v1Tag().getAlbumTitle() : mp3File.getID3v2Tag().getAlbumTitle();
        } else {
            return null;
        }
    }

    /** Битрейт */
    public int getBitRate() {
        if (isIssetTags()) {
            return mp3File.getBitRate();
        } else {
            return 0;
        }
    }

    /** Проверка, есть ли теги в файле */
    private boolean isIssetTags() {
        return mp3File != null && (mp3File.getID3v1Tag() != null || mp3File.getID3v2Tag() != null);
    }

    @Override
    public String toString() {
        if (isIssetTags()) {
            return getArtist() + " - " + getTitle() + " (" + getAlbum() + ")";
        } else {
            return getFileName();
        }
    }
}
