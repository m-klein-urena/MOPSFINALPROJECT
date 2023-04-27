package com.example.mopsfinalproject.custom;


import android.content.Intent;
import android.os.Bundle;

import java.util.EnumMap;
import java.util.Map;

//The purpose of this class is to store all of the relevant attributes of a person in a single JAva object that can be passed in between activities.
//


public class Person {

    public enum Attribute {first, last, preferred, pronouns, address1, address2, city, state, zip, phone, email, school, skill1, skill2, skill3}
    private Map<Attribute, String> genes;

    public Attribute[] attributesEnumArray = Person.buildAttributeArray();

    public String[] attributesStringArray = Person.AttributesToString();

    public static Map<Attribute, String> attributesMap = Person.buildLabelMap();


//    public static Map<Attribute, String> attributesEnumToString() {
//        Map<Attribute, String> map = new EnumMap<Attribute, String>(Attribute.class);
//
//
//
//
//
//    }

    public Person() {
        genes = initializeGenes();

    }

    public void setAttributes(Map<Attribute, String> map) {

        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
            Attribute attr = entry.getKey();
            String val = entry.getValue();

            if ((val != "") && val != null) {
                this.genes.put(attr, val);
            } else {
                this.genes.put(attr, "ukn");
            }
//        end for
        }

//    End setAttribute
    }


    public String getAttribute (Attribute attr) {
        return this.genes.get(attr);

    }

    public Map<Attribute, String> getAttributeMap (Attribute[] attr) {
        Map<Attribute, String> map = new EnumMap<Attribute, String>(Attribute.class);

        for (Attribute key : attr) {
            map.put(key, this.getAttribute(key));
        }

        return map;
    }

    public Map<Attribute, String> getAllAttributes() {
        return getAttributeMap(Person.buildAttributeArray());

    }

    public void sequenceGenes(Intent intent) {
        Bundle bundle = new Bundle();

        bundle.putString("first", this.genes.get(Attribute.first));
        bundle.putString("last", this.genes.get(Attribute.last));
        bundle.putString("preferred", this.genes.get(Attribute.preferred));
        bundle.putString("pronouns", this.genes.get(Attribute.pronouns));
        bundle.putString("address1", this.genes.get(Attribute.address1));
        bundle.putString("address2", this.genes.get(Attribute.address2));
        bundle.putString("city", this.genes.get(Attribute.city));
        bundle.putString("state", this.genes.get(Attribute.state));
        bundle.putString("zip", this.genes.get(Attribute.zip));
        bundle.putString("phone", this.genes.get(Attribute.phone));
        bundle.putString("email", this.genes.get(Attribute.email));
        bundle.putString("school", this.genes.get(Attribute.school));
        bundle.putString("skill1", this.genes.get(Attribute.skill1));
        bundle.putString("skill2", this.genes.get(Attribute.skill2));
        bundle.putString("skill3", this.genes.get(Attribute.skill3));

        intent.putExtras(bundle);

        System.out.println(this.genes.get(Attribute.state));

    }


    public static Person buildPerson(Intent intent, Attribute[] attributes) {
        Person person = new Person();
        Bundle extras = intent.getExtras();
        Map<Attribute, String> map = new EnumMap<Attribute, String>(Attribute.class);

        for (Attribute attr : attributes) {
            String key = Person.buildLabelMap().get(attr);
            String val = extras.getString(key);
            System.out.println(key);
            System.out.println(val);
            map.put(attr, val);
        }

        person.setAttributes(map);

        return person;
    }

    private EnumMap<Attribute, String> initializeGenes() {
        EnumMap<Attribute, String> map = new EnumMap<Attribute, String>(Attribute.class);

        Attribute[] keys = Person.buildAttributeArray();
        String default_str = "ukn";

        for(Attribute key : keys) {
            map.put(key, default_str);
        }

        return map;

    }

    public static Attribute[] buildAttributeArray() {

        Attribute[] arr = new Attribute[]{Attribute.first,
                                Attribute.last,
                                Attribute.preferred,
                                Attribute.pronouns,
                                Attribute.address1,
                                Attribute.address2,
                                Attribute.city,
                                Attribute.state,
                                Attribute.zip,
                                Attribute.phone,
                                Attribute.email,
                                Attribute.school,
                                Attribute.skill1,
                                Attribute.skill2,
                                Attribute.skill3};

        return arr;
    }

    public static Map<Attribute, String> buildLabelMap () {
        Map<Attribute, String> map = new EnumMap<Attribute, String>(Attribute.class);

        map.put(Attribute.first, "First Name:");
        map.put(Attribute.last, "Last Name:");
        map.put(Attribute.preferred, "Preferred Name:");
        map.put(Attribute.pronouns, "Pronouns:");
        map.put(Attribute.address1, "Address:");
        map.put(Attribute.address2, "");
        map.put(Attribute.city, "City:");
        map.put(Attribute.state, "State:");
        map.put(Attribute.zip, "ZIP:");
        map.put(Attribute.phone, "Phone:");
        map.put(Attribute.email, "Email:");
        map.put(Attribute.school, "School:");
        map.put(Attribute.skill1, "Skill 1:");
        map.put(Attribute.skill2, "Skill 2:");
        map.put(Attribute.skill3, "Skill 3:");

        return map;
    }

    public static String[] AttributesToString() {
        Map <Attribute, String> labels = Person.buildLabelMap();
        Attribute[] attr = Person.buildAttributeArray();

        String[] array = new String[attr.length];

        for (int i = 0; i < attr.length; i++) {
            array[i] = labels.get(attr[i]);
        }
        return array;

    }

    public String[] genesToString() {
        Attribute[] attr = Person.buildAttributeArray();

        String[] array = new String[attr.length];

        for (int i = 0; i < attr.length; i++) {
            array[i] = this.getAttribute(attr[i]);
        }

        return array;
    }

//End class
}
