package com.railwayconcession.http.railwayconcession;

public class Notices {
    public String fileName;
    public String fileUrl;

    public Notices() {
    }

    public Notices(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }


    public String getFileUrl() {
        return fileUrl;
    }
}

