package com.file.study;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOStudy5 {
    public static void main(String[] args) {
        /*
        * 文件拷贝

        将 D:\黑黑.png，拷贝到 E:\根目录下

        1. 创建输入流对象读取文件
        2. 创建输出流对象关联数据目标
        3. 读写操作
        4. 关闭流释放资源
        * */

        byte[] bts = new byte[1024*32];
        int len;
        try(FileInputStream fls = new FileInputStream("F:\\software play in F\\2024\\myAppShow.mp4");FileOutputStream fos = new FileOutputStream("F:\\software play in F\\2024\\play\\myAppShow.mp4")){
            while((len = fls.read(bts)) != -1 ){
                fos.write(bts,0,len);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
