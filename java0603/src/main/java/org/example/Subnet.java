package org.example;

class Subnet {
    private final int id;
    private String region;
    private String ip;
    private RouteTable routeTable;

    public Subnet(int id, String ip, String region) {
        this.id = id;
        this.ip = ip;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    protected void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }

    public boolean transfer(String msg) {
        if (routeTable == null) return false;
        return routeTable.route(msg);
    }
}
