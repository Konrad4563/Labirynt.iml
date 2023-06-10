package com.grupa.projektowa.labirynt;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandler {

    public int  CreateDescriptionFile(String desc,String filename){
        String path ="Labirynty\\";
        try {
            File f = new File(path+filename+".txt");
            if(!f.isFile()){
                FileWriter myWriter = new FileWriter(path+filename+".txt");
                myWriter.write(desc);
                myWriter.close();
                return 0;
            }
            else{
                //Plik o tej nazwie już istnieje
                return 1;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            //Błąd podczas tworzenia pliku
            return 2;
        }
    }

    public UploadLabyrinth ChooseLab(Stage stage)throws IOException{
        UploadLabyrinth uploadLabyrinth = null;

        FileChooser fileChooser = new FileChooser();
        labyrinthFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String firstline = reader.readLine();
            if(firstline!=null){
                if(firstline.equals(".-.. .- -... .. .-. -.-- -. - ")){
                    uploadLabyrinth=getLabyrinthFromFile(file);
                }
                else{
                    new AlertBox().wyswietl("Błąd wczytania pliku","Brak autoryzacji pliku",false);
                }
            }
            else{
                new AlertBox().wyswietl("Błąd wczytania pliku","Plik jest pusty",false);
            }
        }
        return uploadLabyrinth;
    }

    public UploadLabyrinth getLabyrinthFromFile(File file){
        int lineCounter=0,cols=0,rows=0;
        int[][] arr = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null) {
                lineCounter++;
                if(lineCounter==2){
                    cols = Integer.parseInt(line.substring(0,2));
                    rows = Integer.parseInt(line.substring(3,5));
                    //40x30
                    arr = new int[rows][cols];
                }
                if(lineCounter>2){
                    String[] tokens = line.split(" ");
                    for (int j = 0; j < tokens.length; j++) {
                        int l =Integer.parseInt(tokens[j]);
                        arr[i][j] =l;
                    }
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new UploadLabyrinth(rows,cols,arr);
    }

    private  void labyrinthFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Wybierz plik z labiryntem");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.dir"), "Labirynty")
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }


}
