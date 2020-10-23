package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by htf on 2020/10/23.
 */
public abstract class Service {

    protected Service next;

    public Service(Service next) {
        this.next = next;
    }

    protected abstract int getCode();

    public void process(int code, Server server, JSONObject command) {
        if (code == getCode()) {
            process0(server, command);
        } else if (next != null) {
            next.process(code, server, command);
        }
    }

    protected abstract void process0(Server server, JSONObject command);
}
