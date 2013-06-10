package com.phpusr.service.entity;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3;

import java.io.File;
import java.io.IOException;

/**
 * @author phpusr
 * Date: 09.06.13
 * Time: 19:53
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
        return getID3Tag() != null ? getID3Tag().getSongTitle() : null;
    }

    /** Исполнитель */
    public String getArtist() {
        return getID3Tag() != null ? getID3Tag().getLeadArtist() : null;
    }

    /** Альбом */
    public String getAlbum() {
        return getID3Tag() != null ? getID3Tag().getAlbumTitle() : null;
    }

    /** Битрейт */
    public int getBitRate() {
        return isNotNull() ? mp3File.getBitRate() : 0;
    }

    /** Проверка на null */
    private boolean isNotNull() {
        return mp3File != null;
    }

    private AbstractID3 getID3Tag() {
        if (isNotNull()) {
            return mp3File.getID3v1Tag() != null ? mp3File.getID3v1Tag() : mp3File.getID3v2Tag();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        if (isNotNull()) {
            return getArtist() + " - " + getTitle() + " (" + getAlbum() + ")";
        } else {
            return getFileName();
        }
    }
}
