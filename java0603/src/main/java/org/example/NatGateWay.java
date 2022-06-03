package org.example;

class NatGateWay implements GateWay {
    private Subnet subnet;

    public NatGateWay(Subnet subnet) {
        this.subnet = subnet;
    }
    @Override
    public boolean send(String msg) {
        return subnet.transfer(msg);
    }
}
