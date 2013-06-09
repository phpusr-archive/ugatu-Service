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

    private ClipboardKeeper clipboardKeeper;

    private PhpusrWrapperListener() {}

    @Override
    public Integer start(String[] args) {
        System.out.println(">>Program start...");
        clipboardKeeper = new ClipboardKeeper("PhpusrService.txt");
        clipboardKeeper.start();

        return null;
    }

    @Override
    public int stop(int exitCode) {
        System.out.println(">>Program end...");
        clipboardKeeper.stop();

        return exitCode;
    }

    @Override
    public void controlEvent(int event) {
        System.out.println(">>controlEvent: " + event);
    }

    public static void main(String[] args) {
        // Start the application.  If the JVM was launched from the native
        //  Wrapper then the application will wait for the native Wrapper to
        //  call the application's start method.  Otherwise the start method
        //  will be called immediately.
        WrapperManager.start(new PhpusrWrapperListener(), args);
    }
}
