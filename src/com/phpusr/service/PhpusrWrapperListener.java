package com.phpusr.service;

import com.phpusr.service.thread.MP3Tag2Html;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 * @author phpusr
 * Date: 08.06.13
 * Time: 11:35
 */

/**
 * Оболочка для сервиса
 */
public class PhpusrWrapperListener implements WrapperListener {

    private Thread service;

    private PhpusrWrapperListener() {}

    @Override
    public Integer start(String[] args) {
        System.out.println(">>Service start...");
        service = new MP3Tag2Html(args[0], args[1]);
        service.start();

        return null;
    }

    @Override
    public int stop(int exitCode) {
        System.out.println(">>Service end...");
        service.interrupt();

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
