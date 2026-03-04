package com.example.stockmedication.application;

import com.example.stockmedication.domain.Batch;
import com.example.stockmedication.domain.Medication;
import com.example.stockmedication.persistence.DBMedicationRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MedicationService {
    private final DBMedicationRepository repository;

    public MedicationService(DBMedicationRepository repository) {
        this.repository = repository;
    }

    public Medication createMedication(MedicationCommand medicationCommand) {
        String id = medicationCommand.getName();
        Medication newMed = new Medication(id,
                medicationCommand.getName(),
                medicationCommand.getForm(),
                medicationCommand.getStrengths());
        //save to the ddbb
        repository.addMedication(newMed);
        //medications.put(newMed.getName(), newMed);
        return newMed;
    }

    public List<Medication> getMedications() {
        return repository.getAllMedicationsWithBatches();
        //return new ArrayList<>(medications.values());
    }

    public int medicationQuantity(String medicationId) {
        Medication med = repository.getMedicationWithBatches(medicationId);
        return med.getTotalQuantity();
        //return medications.get(medicationId).getTotalQuantity();
    }

    public Batch addNewBatch(String id, BatchCommand batchCommand) {
        repository.getMedicationWithBatches(id);//TODO need to control if medication exists

        //Medication medication = medications.get(id);
        //create the batch
        Batch newBatch = new Batch(batchCommand.getBatchNo(), batchCommand.getQuantity(), batchCommand.getExpiry());
        // add the batch to medication
        repository.addBatchToMedication(id, newBatch);
        //medication.addBatch(newBatch);
        // return the batch
        return newBatch;
    }

    // sell medication
    public boolean sellMedication(String medicationId, int quantity) {
        if (quantity <= 0) {
            return false;
        }
        Medication med = repository.getMedicationWithBatches(medicationId);
        boolean success = med.sellMedication(quantity);
        if (!success) {
            return false;
        }
        List<Batch> batches = med.getBatches();
        for (Batch batch : batches) {
            String batchNo = batch.getBatchNo();
            int newQuantity = batch.getQuantity();
            repository.updateBatchQuantity(batchNo, newQuantity);
        }
        return true;
        //Medication medication = medications.get(medicationId);
        //return medication.sellMedication(quantity);
    }
}
