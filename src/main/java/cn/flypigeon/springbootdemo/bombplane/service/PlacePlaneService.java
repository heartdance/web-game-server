package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by htf on 2020/10/23.
 */
public class PlacePlaneService extends Service {

    public PlacePlaneService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 4;
    }

    @Override
    protected void process0(Server server, JSONObject command) {

    }
}
