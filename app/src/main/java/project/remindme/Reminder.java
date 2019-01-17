package project.remindme;

import java.io.Serializable;

public class Reminder implements Serializable {

    private String name;
    private String desc;

    public Reminder(String n, String d) {
        this.name = n;
        this.desc = d;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }
}
