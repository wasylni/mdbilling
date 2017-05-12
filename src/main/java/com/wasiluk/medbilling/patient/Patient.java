package com.wasiluk.medbilling.patient;

/**
 * Created by marcin on 13-May-17.
 */
public class Patient {

    private int age;

    private boolean isMediHealth;


    public Patient(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMediHealth() {
        return isMediHealth;
    }

    public void setMediHealth(boolean mediHealth) {
        isMediHealth = mediHealth;
    }

}
