package com.lin.alllib.test;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * Created by linhui on 2017/8/9.
 */
public class NioTest {

    public static final void main(String[] args) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\photo\\111.jpg");

        FileChannel channel = fileOutputStream.getChannel();

        int capacity = 1024;
        RandomAccessFile aFile = new RandomAccessFile("D:\\photo\\u=2054292854,2886163927&fm=200&gp=0.jpg", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
                channel.write(buf);
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
        channel.close();
    }
}
