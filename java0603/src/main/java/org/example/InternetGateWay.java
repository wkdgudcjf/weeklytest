package org.example;

public class InternetGateWay implements GateWay {

    @Override
    public boolean send(String msg) {
        System.out.println(msg);
        return true;
    }
}
