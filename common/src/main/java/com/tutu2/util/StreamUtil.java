package com.tutu2.util;

import java.io.*;

class StreamUtil {


   public StreamUtil() {
   }


   static void io(Reader in, Writer out) throws IOException {
       io(in, out, -1);
   }

    private static void io(Reader in, Writer out, int bufferSize) throws IOException {
       if(bufferSize == -1) {
           bufferSize = 4096;
       }

       char[] buffer = new char[bufferSize];

       int amount;
       while((amount = in.read(buffer)) >= 0) {
           out.write(buffer, 0, amount);
       }

   }
    static String readText(InputStream in) throws IOException {
       return readText(in, -1);
   }

    private static String readText(InputStream in, int bufferSize) throws IOException {
       InputStreamReader reader =new InputStreamReader(in);
       return readText(reader, bufferSize);
   }


    private static String readText(Reader reader, int bufferSize) throws IOException {
       StringWriter writer = new StringWriter();
       io(reader, writer, bufferSize);
       return writer.toString();
   }
}
