package com.medicomp.medicompbackend.repository;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.medicomp.medicompbackend.model.Medication;
import com.medicomp.medicompbackend.model.MedicationPairing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MediRepository {
    private List<Medication> meds;
    private List<MedicationPairing> pairings;
    private static final String path = "src/main/resources/MediRepository.json";
    private static int highestId;

    private MediRepository() {
        this.meds = new ArrayList<>();
        this.pairings = new ArrayList<>();
    }

    public static MediRepository loadRepository() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = gson.newJsonReader(new FileReader(path));
        return gson.fromJson(reader, MediRepository.class);
    }

    public void saveRepository() throws IOException {
        Writer writer = new FileWriter(path);
        new Gson().toJson(this, writer);
        writer.close();
    }

    public void addMed(Medication newMed) {
        if (medicationNotYetInMeds(newMed)) {
            Medication med = new Medication(++highestId, newMed.getName());
            addPairings(med);
            meds.add(med);
        }
    }

    private void addPairings(Medication newMed) {
        for (Medication med : meds) {
            pairings.add(new MedicationPairing(med, newMed));
        }
    }

    private boolean medicationNotYetInMeds(Medication newMed) {
        for (Medication med : meds) {
            if (med.getName().equals(newMed.getName())) {
                return false;
            }
        }
        return true;
    }

    public List<Medication> getMeds() {
        return meds;
    }

}
