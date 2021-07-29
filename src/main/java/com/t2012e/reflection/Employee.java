package com.t2012e.reflection;

import com.t2012e.reflection.myannotation.Id;
import com.t2012e.reflection.myannotation.Table;

@Table(name = "sinhvien")
public class Employee {
    @Id(autoIncrement = true)
    private int id;
    private String name;
    private String address;
    private String phone;

    public Employee() {
    }

    public Employee(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
