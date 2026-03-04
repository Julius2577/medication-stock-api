package com.example.stockmedication.application;

import java.time.LocalDate;

public class MedicationCommand {
    private String  name;
    private String  form;
    private String     strengths;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }
}
