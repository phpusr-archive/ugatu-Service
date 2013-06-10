package com.phpusr.service.thread;

import com.phpusr.service.entity.Song;
import org.farng.mp3.TagException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author phpusr
 * Date: 09.06.13
 * Time: 18:37
 */


/**
 * Записывает инф-ю об MP3-файлах в папке, в html-файл
 */
public class MP3Tag2HtmlThread extends Thread {

    /** Папка с песнями */
    private String songsDir;
    /** Имя html-файла */
    private String htmlFileName;

    public static void main(String[] args) throws InterruptedException, IOException, TagException {
        MP3Tag2HtmlThread mp3Tag2HtmlThread = new MP3Tag2HtmlThread("music", "html/report.html");
        mp3Tag2HtmlThread.start();

        Thread.sleep(5000);
        mp3Tag2HtmlThread.interrupt();
    }

    public MP3Tag2HtmlThread(String songsDir, String htmlFileName) {
        this.songsDir = songsDir;
        this.htmlFileName = htmlFileName;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println(">>Songs:");
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
            System.err.println(e.getMessage());
        }

        return songList;
    }

    /** Генерация html-файла из тегов песен */
    private void generateHtmlFormSongs(List<Song> songList) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(htmlFileName), "utf-8"));
            out.println("<html>");
            out.println("<head><meta charset=\"utf-8\"><link href=\"style/style.css\" type=\"text/css\" rel=\"stylesheet\"/></head>");
            out.println("<html><body class=\"branding\"><section id=\"main\">");
            String[] classes = new String[]{"main-holder", "wrap", "white-box home-page", "white-holder", "white-frame", "gray-box", "gray-holder", "column"};

            for (String cssClass : classes) {
                out.print("<div class=\"" + cssClass + "\">");
            }

            out.print("\n<div class=\"topic\"><h2>"+ new Date() +"<br/>Композиции в папке: <br/>");
            out.println(songsDir + "</h2></div>");
            out.println("<ul class=\"songs-list players-list js-top40-list\">");

            int count = 0;
            for (Song song : songList) {
                if (song.getTitle() != null) {
                    out.println("<li class=\"player-in-playlist-holder\">");
                    out.println("\t<div class=\"jp_container\">");
                    out.println("\t\t<span class=\"number\">"+(++count)+"</span>");
                    out.println("\t\t<div class=\"jp-title\">");
                    out.println("\t\t\t<strong class=\"title\"><a href=\"#\" title=\"Карточка исполнителя " + song.getArtist() + "\">" + song.getArtist() + "</a></strong>");
                    out.print("\t\t\t<span>" + song.getTitle());
                    if (song.getAlbum() != null && !song.getAlbum().equals("")) out.print(" <i>(" + song.getAlbum() + ")</i>");
                    out.print("</span>");
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
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }

}
