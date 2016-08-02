package com.max.tse.io.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-3-16
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
public class BufferTest {
    public RandomAccessFile  getFile () throws Exception {
        System.out.println(this.getClass().getResource("/readme.txt").getPath().toString());
        RandomAccessFile aFile = new RandomAccessFile(this.getClass().getResource("/readme.txt").getPath().toString(), "rw");
        return aFile;

    }

    public static void main(String[] args) throws Exception{
        //RandomAccessFile aFile = new RandomAccessFile(this.getClass().getResource("/readme.txt"), "rw");
        //System.out.print(aFile.read());
        BufferTest bufferTest = new BufferTest();
        RandomAccessFile accessFile = bufferTest.getFile();
        FileChannel inChannel = accessFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(480);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while(buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        accessFile.close();


    }
}
