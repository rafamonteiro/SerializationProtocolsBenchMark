package br.com.tdc.serializationprotocols.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rafael on 23/07/2015.
 */
public class AddressBookJson implements Serializable{

    private Integer id;
    private String name;
    private String email;
    private List<Long> longList;

    public AddressBookJson() {
    }

    public AddressBookJson(Integer id, String name, String email, List<Long> longList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.longList = longList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }
}
