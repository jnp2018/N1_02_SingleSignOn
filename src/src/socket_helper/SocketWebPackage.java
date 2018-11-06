/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_helper;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ledin
 */
public class SocketWebPackage implements Serializable {
    private String id;
    private Object body;
    private String name;
    private Timestamp ctime;

    public SocketWebPackage(String id, String name, Object body) {
        this.id = id;
        this.body = body;
        this.name = name;
        this.ctime = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public Object getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCtime() {
        return ctime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setCtime(Timestamp ctime) {
        this.ctime = ctime;
    }
}
