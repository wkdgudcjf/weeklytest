package org.example;

import java.util.ArrayList;
import java.util.List;

class RouteTable {
    private int id;
    private GateWay gateWay;
    private final List<Subnet> subnetList = new ArrayList<>();

    public void setGateWay(GateWay gateWay) {
        this.gateWay = gateWay;
    }

    public GateWay getGateWay() {
        return gateWay;
    }

    public void addSubnet(Subnet subnet) {
        subnetList.add(subnet);
        subnet.setRouteTable(this);
    }

    public boolean route(String msg) {
        return gateWay.send(msg);
    }
}
