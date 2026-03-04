package com.example.stockmedication.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Medication
{
    // Attribute für Medikamente
        private String ID;
        private final String name;
        private final String form;
        private final String strength;
        private final List<Batch> batches;

      // Konstruktor bauen
    public Medication(String ID, String name, String form, String strength)
    {
        //if (ID == null || ID.isBlank()) throw new IllegalArgumentException("id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        this.ID = ID;
        this.name = name;
        this.form = form;
        this.strength = strength;
        batches = new ArrayList<>();
    }

    public Medication(String name, String form, String strengths) {
        this(null, name, form, strengths);
    }

    // Getter für den private String
    public String getId() { return ID; }
    public String getName() { return name; }
    public String getForm() { return form; }
    public String getStrength() { return strength; }

    public List<Batch> getBatches() {
        return batches;
    }

    //TODO
    public int getTotalQuantity()
    {
        int sum = 0;
        LocalDate today = LocalDate.now();
        for (int i=0; i<batches.size(); i++)
        {
            Batch currentBatch = batches.get(i);
            if (!currentBatch.isExpired(today))
            {
                sum += currentBatch.getQuantity();
            }

        }
        return sum;
    }

    public void addBatch(Batch batch)
    {
        batches.add(batch);
    }

    //TODO
    public boolean sellMedication(int quantity)
    {
        if (quantity <= 0)
        {
            return false;
        }

        int available = getTotalQuantity();
        if (available < quantity)
        {
            return false;
        }

        batches.sort(null); // take the ones that expire first at first
        int remaining = quantity;
        for (int i=0; i<batches.size() && remaining >0; i++)
        {
            Batch currentBatch = batches.get(i);
            if (currentBatch.isExpired(LocalDate.now()))
            {
                continue;
            }

            int batchQuantity = currentBatch.getQuantity();
            if (batchQuantity >= remaining)
            {
                currentBatch.take(remaining);
                remaining = 0;
            }
            else
            {
                currentBatch.take(batchQuantity);
                remaining -= batchQuantity;
            }
        }
        return remaining==0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Medication m)) return false;
        return ID.equalsIgnoreCase(m.ID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ID.toLowerCase());
    }

    @Override
    public String toString()
    {
        return name + " (" + strength + ", " + form + ")";
    }
}
