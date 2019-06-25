package com.sandalen.sandalen.dao;

import java.io.File;

public class TstFileExists {
    public static void main(String[] args) {
        File file = new File("../json/emotion-mapping");
        System.out.println(file.exists());
    }
}
