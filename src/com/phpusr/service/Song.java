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
 * TODO Исполнитель, Альбом, Имя файла (Выводить его, если все ост-е пусто)
 */
public class Song {
    private MP3File mp3File;

    public Song(File file) throws IOException, TagException {
        mp3File = new MP3File(file);
    }

    public String getTitle() {
        if (mp3File != null && mp3File.getID3v1Tag() != null) {
            return mp3File.getID3v1Tag().getSongTitle() != null ? mp3File.getID3v1Tag().getSongTitle() : mp3File.getID3v2Tag().getSongTitle();
        } else {
            return null;
        }
    }
}
