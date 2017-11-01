package com.util.test.json;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable{
    private static final long serialVersionUID = 1L;

    private int age;
    
    private Date birthDate;
    private String name;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Person(int age, Date birthDate, String name) {
	super();
	this.age = age;
	this.birthDate = birthDate;
	this.name = name;
    }
    public Person() {
	super();
    }
    
    
    
}
