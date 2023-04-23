package one;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 给定两个字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
 * @author molax
 * @date 2022/1/25 10:19
 */
public class Two {

    public boolean checkLetter(String s1, String s2){
        if(s1.length() == s2.length()) {
            for (int i = 0, size = s1.length(); i < size; i++) {
                s2.replaceFirst(String.valueOf(s1.charAt(i)), "");
            }
            return s2.isEmpty();
        }
        return false;
    }


    public static void main(String[] args) {
//        var a = 0 >>> 1;
//        print(a);
            fileChannelWrite("/Users/molax/valley/tmp/nio.txt");
            fileChannelRead("/Users/molax/valley/tmp/nio.txt");
    }

    public static void print(int num){
        System.out.print(num + ": ");
        for(int i=31;i>=0;i--){
            System.out.print((num & 1 << i) == 0 ? "0":"1");
        }
        System.out.println("");
    }

    public static void fileChannelWrite(String filePath) {
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.clear();
            buf.put("test".getBytes(StandardCharsets.UTF_8));
            buf.flip();
            while(buf.hasRemaining()) {
                channel.write(buf);
            }
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileChannelRead(String filePath) {
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.clear();
            int read;
            do{
                read = channel.read(buf);
                System.out.println(buf);
            } while (read>0);
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
