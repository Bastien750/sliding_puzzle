package com.example.sliding_puzzle.abstraction;

import com.example.sliding_puzzle.controllers.GameController;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Record {

    private String recordFile;

    private Label recordLabel;

    private ShowAlert showAlert;

    public String getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(String recordFile) {
        this.recordFile = recordFile;
    }

    public Label getRecordLabel() {
        return recordLabel;
    }

    public void setRecordLabel(Label recordLabel) {
        this.recordLabel = recordLabel;
    }

    public void  updateRecord() {
        int nbTurns = Integer.parseInt(GameController.getNbTurns().getNbTurns().getText());
        int currentRecord = this.getFileLevelRecord();

        this.recordLabel.setText(Integer.toString(nbTurns));

        if(currentRecord == 0) {
            this.modifiyRecordFile(nbTurns);
            this.showAlert = new ShowAlert("Nouveau Record", "Le nouveau record est : " + nbTurns);
        } else if (nbTurns < currentRecord) {
            this.modifiyRecordFile(nbTurns);
            this.showAlert = new ShowAlert("Nouveau Record", "Le nouveau record est : " + nbTurns);
        }
    }

    public void modifiyRecordFile(int newRecord) {;
        String recordLine = "Record: ";

        try (BufferedReader br = new BufferedReader(new FileReader(GameController.getSlidingGame().getFileName()))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(recordLine)) {
                    // Modification de la ligne du record
                    line = recordLine + newRecord;
                }
                fileContent.append(line).append("\n");
            }

            // Écriture du contenu modifié dans le fichier
            try (FileWriter writer = new FileWriter(GameController.getSlidingGame().getFileName())) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getFileLevelRecord() {
        String recordLine = "Record: ";
        int recordValue = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(GameController.getSlidingGame().getFileName()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(recordLine)) {
                    String recordNumberStr = line.substring(recordLine.length());
                    try {
                        recordValue = Integer.parseInt(recordNumberStr.trim());
                        break; // On quitte la boucle après avoir récupéré le nombre du record
                    } catch (NumberFormatException e) {
                        // La valeur du record n'est pas un nombre valide, faire autre chose si nécessaire
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordValue;
    }
}
