package com.example.stockmedication.persistence;

import com.example.stockmedication.domain.Batch;
import com.example.stockmedication.domain.Medication;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Repository
public class DBMedicationRepository
{
    private JdbcClient jdbcClient;

    public DBMedicationRepository(JdbcClient jdbcClient)
    {
        this.jdbcClient = jdbcClient;
    }

    private Medication mapMedicationRow(ResultSet rs) throws SQLException
    {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String form = rs.getString("form");
        String strength = rs.getString("strength");
        return new Medication(id, name, form, strength);
    }

    private Batch mapBatchRow(ResultSet rs) throws SQLException
    {
        String batchNo = rs.getString("batch_no");
        int quantity = rs.getInt("quantity");
        LocalDate expiry = rs.getDate("expiry").toLocalDate();
        return new Batch(batchNo, quantity, expiry);
    }

    public Medication getMedicationWithBatches(String id)
    {
        String sql = "SELECT * FROM MEDICATION m LEFT JOIN BATCH b ON b.med_id = m.id WHERE m.id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(rs -> {
                    Medication medication = null;
                    while (rs.next())
                    {
                        if (medication == null)
                        {
                            medication = mapMedicationRow(rs);
                        }
                        String batchNo = rs.getString("batch_no");
                        if (batchNo != null)
                            {
                            Batch batch = mapBatchRow(rs);
                            medication.addBatch(batch);
                            }
                    }
                    if (medication == null)
                    {
                        throw new IllegalArgumentException("Medication not found" +id );
                    }
                    return medication;
                } );

    }
    public List<Medication> getAllMedicationsWithBatches()
    {
        String sql = "SELECT * FROM MEDICATION m LEFT JOIN BATCH b ON b.med_id = m.id ORDER BY m.id";
        return jdbcClient.sql(sql)
                .query(rs -> {
                    Map<String, Medication> meds = new LinkedHashMap<>();
                    while (rs.next())
                    {
                        String medId = rs.getString("id");
                        Medication med = meds.get(medId);
                        if (med == null)
                        {
                            med= mapMedicationRow(rs);
                            meds.put(medId, med);
                        }
                        String batchNo = rs.getString("batch_no");
                        if (batchNo != null) {
                            Batch batch = mapBatchRow(rs);
                            med.addBatch(batch);
                        }
                    }
                    return new ArrayList<>(meds.values());
                });

    }
    public void addMedication(Medication med)
    {
        var params = Map.<String, Object>of(
                "id", med.getId(),
                "name", med.getName(),
                "form", med.getForm(),
                "strength", med.getStrength()
        );

        jdbcClient.sql("INSERT INTO MEDICATION (id, name, form, strength) VALUES (:id, :name, :form, :strength)")
                .params(params)
                .update();
    }
    public void addBatchToMedication(String medId, Batch batch)
    {
        jdbcClient.sql("INSERT INTO BATCH (batch_no, med_id, quantity, expiry)VALUES (:batchNo, :medId, :quantity, :expiry)")
            .param("batchNo",batch.getBatchNo())
            .param("medId", medId)
            .param("quantity", batch.getQuantity())
            .param("expiry", batch.getExpiry())
            .update();
    }
    public void updateBatchQuantity(String batchNo, int newQuantity)
    {
        jdbcClient.sql("UPDATE BATCH SET quantity = :quantity WHERE batch_no = :batchNo")
        .param("quantity", newQuantity)
        .param ("batchNo",batchNo)
        .update();
    }
}
