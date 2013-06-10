package com.phpusr.service.thread;

/**
 * @author phpusr
 * Date: 08.06.13
 * Time: 17:21
 */
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Хранитель буфера обмена
 */
public class ClipboardKeeperThread extends Thread {

    private String fileName;

    public static void main(String[] args) throws InterruptedException {
        ClipboardKeeperThread clipboardKeeperThread = new ClipboardKeeperThread("bin/PhpusrService.txt", true);
        clipboardKeeperThread.start();

        Thread.sleep(15000);
        clipboardKeeperThread.interrupt();
    }

    public ClipboardKeeperThread(String fileName, boolean append) {
        this.fileName = fileName;
        try {
            new FileWriter(fileName, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String lastData = null;

            while (!isInterrupted()) {
                String clipboardData = getClipboardData();
                if (clipboardData != null && !clipboardData.equals(lastData)) {
                    PrintWriter out = new PrintWriter(new FileWriter(fileName, true));
                    out.println(new Date() + ": " + clipboardData);
                    out.close();
                    System.out.println(">>Change clipboard: " + clipboardData);
                }
                Thread.sleep(100);
                lastData = clipboardData;
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
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
        } catch (UnsupportedFlavorException e) {
            System.out.println("Er1");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Er2");
            e.printStackTrace();
        }

        return null;
    }

    /** Копирует текст в буфер-обмена */
    private static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    /** Тест методов буфера-обмена */
    private static void testClipboardMethods() {
        System.out.println(getClipboardData());
        setClipboardData("It is work!");
        System.out.println(getClipboardData());
    }


}
