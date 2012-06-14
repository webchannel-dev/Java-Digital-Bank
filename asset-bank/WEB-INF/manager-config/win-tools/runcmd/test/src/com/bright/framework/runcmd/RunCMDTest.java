package com.bright.framework.runcmd;

/**
 * Bright Interactive, Image Bank
 *
 * Copyright 2009 Bright Interactive, All Rights Reserved.
 * RunCMDTest.java
 *
 * Contains the RunCMDTest class.
 */
/*
 Ver  Date			Who					Comments
 -------------------------------------------------------------------------------
 d1	  16-Apr-2009	Francis Devereux	Created
 -------------------------------------------------------------------------------
*/

import java.util.*;
import java.io.*;

/**
 * Program to test running runcmd.exe from Java
 *
 * @author Bright Interactive
 */
public class RunCMDTest
{
    private static class ProcWaiter implements Runnable
    {
        private Process proc;
        private boolean finished = false;

        public ProcWaiter(Process proc)
        {
            this.proc = proc;
        }

        public void run()
        {
            while(!isFinished())
            {
                try
                {
                    proc.waitFor();
                    setFinished(true);
                }
                catch (InterruptedException e)
                {
                    // ignore
                }
            }
        }

        public synchronized boolean isFinished()
        {
            return finished;
        }

        private synchronized void setFinished(boolean finished)
        {
            this.finished = finished;
        }
    }

    private static final int BUF_SIZE = 4096;

    public static void main(String[] args) throws Exception
    {
        String[] subProcArgs = args;

        for (int i = 0; i < subProcArgs.length; ++i)
        {
            System.out.println("subProcArgs[" + i + "] = \"" + subProcArgs[i] + "\"");
        }

        Runtime rt = Runtime.getRuntime();
        Process subProc = rt.exec(subProcArgs);

        byte[] buf = new byte[BUF_SIZE];

        InputStream subProcIn = subProc.getInputStream();
        InputStream subProcErr = subProc.getErrorStream();

        ProcWaiter subProcWaiter = new ProcWaiter(subProc);
        new Thread(subProcWaiter).start();

        // Note that this loop eats all available CPU - don't copy/paste it
        // into a "real" program
        while (!subProcWaiter.isFinished())
        {
            tryToCopy(System.out, subProcIn, buf);
            tryToCopy(System.err, subProcErr, buf);
        }

        System.exit(subProc.exitValue());
    }

    private static void tryToCopy(OutputStream out, InputStream in, byte[] buf) throws IOException
    {
        int available = in.available();
        if (available > 0)
        {
            int maxBytesToRead = available > buf.length ? buf.length : available;

            int bytesRead = in.read(buf, 0, maxBytesToRead);

            out.write(buf, 0, bytesRead);
        }
    }
}
