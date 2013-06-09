package com.phpusr.service;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 09.06.13
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */

/**
 * Записывает инф-ю об MP3-файлах в папке в html-файл
 */
public class MP3Tag2Html extends Thread {

    private String fileName;
    private String songsDir;

    public static void main(String[] args) throws InterruptedException, IOException, TagException {
        MP3Tag2Html mp3Tag2Html = new MP3Tag2Html("html/report.html", "f:\\Music\\DL\\Superbus - Sunset (2012)");
        mp3Tag2Html.start();

        Thread.sleep(15000);
        mp3Tag2Html.interrupt();
    }

    public MP3Tag2Html(String fileName, String songsDir) {
        this.fileName = fileName;
        this.songsDir = songsDir;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                generateHtmlFormSongs(getSongsForDir(songsDir));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static List<Song> getSongsForDir(String dir) {
        File folder = new File(dir);
        List<Song> songList = new ArrayList<Song>();
        try {
            for (final File file : folder.listFiles()) {
                if (file.isFile()) {
                    System.out.println("\t" + file.getName());
                    songList.add(new Song(file));
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return songList;
    }

    //TODO сделать красивый вывод
    private void generateHtmlFormSongs(List<Song> songList) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(fileName, false));
            out.println("<html><body>");

            for (Song song : songList) {
                if (song.getTitle() != null) {
                    out.println("<p>" + song.getTitle() + "</p>");
                }
            }

            out.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();
        }

    }

}
