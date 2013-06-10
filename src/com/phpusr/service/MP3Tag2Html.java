package com.phpusr.service;

import org.farng.mp3.TagException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

        Thread.sleep(5000);
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
                    Song song = new Song(file);
                    songList.add(song);
                    System.out.println("\t" + song);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return songList;
    }

    /** Генерация html-файла из тегов песен */
    private void generateHtmlFormSongs(List<Song> songList) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(fileName, false));
            out.println("<html>");
            out.println("<head><link href=\"style/style.css\" type=\"text/css\" rel=\"stylesheet\"/></head>");
            out.println("<html><body class=\"branding\"><section id=\"main\">");
            String[] classes = new String[]{"main-holder", "wrap", "white-box home-page", "white-holder", "white-frame", "gray-box", "gray-holder", "column"};

            for (String cssClass : classes) {
                out.print("<div class=\"" + cssClass + "\">");
            }

            out.println("\n<div class=\"topic\"><h2>Композиции в папке:<br/>" + songsDir + "</h2></div>");
            out.println("<ul class=\"songs-list players-list js-top40-list\">");

            int count = 0;
            for (Song song : songList) {
                if (song.getTitle() != null) {
                    out.println("<li class=\"player-in-playlist-holder\">");
                    out.println("\t<div class=\"jp_container\">");
                    out.println("\t\t<span class=\"number\">"+(++count)+"</span>");
                    out.println("\t\t<div class=\"jp-title\">");
                    out.println("\t\t\t<strong class=\"title\"><a href=\"#\" title=\"Карточка исполнителя " + song.getArtist() + "\">" + song.getArtist() + "</a></strong>");
                    out.println("\t\t\t<span>" + song.getTitle() + " <i>(" + song.getAlbum() + ")</i></span>");
                    out.println("\t\t</div>");
                    out.println("\t</div>");
                    out.println("</li>");
                }
            }

            out.println("</ul>");

            for (int i=0; i<classes.length; i++) {
                out.print("</div>");
            }

            out.println("\n</section></body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();
        }

    }

}
