package com.phpusr.service;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 08.06.13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class PhpusrWrapperListener implements WrapperListener {

    private Thread mp3Tag2Html;

    private PhpusrWrapperListener() {}

    @Override
    public Integer start(String[] args) {
        System.out.println(">>Program start...");
        mp3Tag2Html = new MP3Tag2Html("../html/report.html", "f:\\Music\\DL\\Superbus - Sunset (2012)");
        mp3Tag2Html.start();

        return null;
    }

    @Override
    public int stop(int exitCode) {
        System.out.println(">>Program end...");
        mp3Tag2Html.interrupt();

        return exitCode;
    }

    @Override
    public void controlEvent(int event) {
        System.out.println(">>Control event: " + event);
    }

    public static void main(String[] args) {
        // Start the application.  If the JVM was launched from the native
        //  Wrapper then the application will wait for the native Wrapper to
        //  call the application's start method.  Otherwise the start method
        //  will be called immediately.
        WrapperManager.start(new PhpusrWrapperListener(), args);
    }
}
