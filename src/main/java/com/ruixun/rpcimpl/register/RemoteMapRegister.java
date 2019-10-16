package com.ruixun.rpcimpl.register;

import com.ruixun.rpcimpl.framework.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteMapRegister {

    private static Map<String, List<URL>> REGISTER = new HashMap<>();

    public static void register(String interfaceName,URL url){
        List<URL> urls = REGISTER.get(interfaceName);
        if(urls==null){
            urls = new ArrayList<>();
        }
        urls.add(url);
        REGISTER.put(interfaceName,urls);
        saveFile();
    }

    public static List<URL> get(String interfaceName){
        List<URL> urls = REGISTER.get(interfaceName);
        return urls;
    }

    private static void saveFile() {
        try {
            FileOutputStream fos = new FileOutputStream("remoteMapRegister.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(REGISTER);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Map<String,List<URL>> getFile(){
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream("remoteMapRegister.txt");
            ois = new ObjectInputStream(fis);
            return (Map<String,List<URL>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
