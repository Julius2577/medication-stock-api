package com.example.stockmedication.api;

import com.example.stockmedication.application.BatchCommand;
import com.example.stockmedication.application.MedicationCommand;
import com.example.stockmedication.application.MedicationService;
import com.example.stockmedication.domain.Batch;
import com.example.stockmedication.domain.Medication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicationAPI {
    private final MedicationService medicationService;

    public MedicationAPI(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping("/medications")
    public List<Medication> getMedications(){
        return medicationService.getMedications();
    }

    @PostMapping("/medications")
    public Medication addMedication(@RequestBody MedicationCommand medication){
        return medicationService.createMedication(medication);
    }

    @PostMapping("/medications/{id}/batches")
    public Batch addBatchMedications(@PathVariable String id, @RequestBody BatchCommand batchCommand){
        return medicationService.addNewBatch(id, batchCommand);
    }

    //put mapping to sell a medication id, quantity /medications/{id}/quantity/{quantity}
    @PutMapping("/medications/{id}/quantity/{quantity}")
    public boolean updateMedicationQuantity(@PathVariable String id, @PathVariable int quantity) // We want to know sell or no sell(true or flase (boolean))
    {
        return medicationService.sellMedication(id, quantity);
    }
    // get the total quantity of a medication /medication/{id}/quantity
    @GetMapping("/medications/{id}/quantity")
    public int getMedicationQuantity(@PathVariable String id) // We want Quantity so integer
    {
        return medicationService.medicationQuantity(id);
    }
}
