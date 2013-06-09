package com.phpusr.service;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 08.06.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Хранитель буфера обмена
 */
public class ClipboardKeeper {

    private String fileName;
    private PrintWriter out;
    private boolean stopped;

    public static void main(String[] args) {
//        System.out.println(getClipboardData());
//        setClipboardData("It is work!");
//        System.out.println(getClipboardData());

        ClipboardKeeper clipboardKeeper = new ClipboardKeeper("PhpusrService.txt");
        clipboardKeeper.start();
    }

    public ClipboardKeeper(String fileName) {
        this.fileName = fileName;
    }

    public void start() {
        try {
            FileWriter f = new FileWriter(fileName, false);
            out = new PrintWriter(f);
            stopped = false;
            String lastData = null;

            while (!stopped) {
                String clipboardData = getClipboardData();
                if (lastData == null || !clipboardData.equals(lastData)) {
                    out.print(new Date() + ": " + clipboardData);
                    out.println();
                    System.out.println(">>Change");
                }
                Thread.sleep(100);
                //System.out.println(">>It is work!; clip: " + clipboardData + "; last: " + lastData);
                lastData = clipboardData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stopped = true;
        out.close();
    }

    /** Возвращает содержимое буфера-обмена */
    private static String getClipboardData() {
        String clipboardText;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);

        try {
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                clipboardText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                return clipboardText;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    /** Копирует текст в буфер-обмена */
    private static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }


}
