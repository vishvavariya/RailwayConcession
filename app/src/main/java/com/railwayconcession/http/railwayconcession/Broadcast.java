package com.railwayconcession.http.railwayconcession;

public class Broadcast {
    public String reminder, broadcastmessage;

    public Broadcast(){

    }

    public Broadcast(String reminder,String broadcastmessage) {
        this.reminder = reminder;
        this.broadcastmessage = broadcastmessage;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getBroadcastmessage() {
        return broadcastmessage;
    }

    public void setBroadcastmessage(String broadcastmessage) {
        this.broadcastmessage = broadcastmessage;
    }
}

