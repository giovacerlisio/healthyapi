package com.healthy.healthy.controller;

import com.healthy.healthy.model.Alimenti;
import com.healthy.healthy.model.dataBmi;
import com.healthy.healthy.model.dataCal;
import com.healthy.healthy.repository.AlimentiRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private AlimentiRepository repo;

    @PostMapping("/alimenti")
    public Object alimenti(@RequestBody Alimenti alimenti) throws IOException {


        String nome = alimenti.getNome();
        String calorie = alimenti.getCalorie();
        String peso = alimenti.getPeso();

        repo.save(alimenti);


        return null;
    }


    @GetMapping("/file")
    public Resource getFile() throws IOException {

        //Scrittura su un file di testo di una Stringa
        String str = "Healthy Docs v1.0";
        BufferedWriter writer = new BufferedWriter(new FileWriter("testo.txt"));
        writer.write(str);
        writer.close();

        // Convertire il file di testo in un documento PDF utilizzando Apache PDFBox
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (BufferedReader reader = new BufferedReader(new FileReader("testo.txt"))) {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 12); // Imposta il font del testo
            String line;
            float y = page.getMediaBox().getHeight() - 50; // Posizione y del primo testo
            while ((line = reader.readLine()) != null) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y); // Posizione x e y del testo
                contentStream.showText(line);
                contentStream.endText();
                y -= 20; // Spaziatura tra le righe
            }
            contentStream.close();
        }

        document.save("testo.pdf");
        document.close();

        Resource fileResource = new FileSystemResource("testo.pdf");
        return fileResource;
    }


    @GetMapping("/listalimenti")
    public List<Alimenti> alimenti() {
        return repo.findAll();
    }

    @GetMapping("/getalimentoby/{id}")
    public Optional<Alimenti> alimenti(@PathVariable Integer id) {
        return repo.findById(id);
    }

    @PutMapping("/modifyalimenti/{id}")
    public ResponseEntity<Alimenti> modifyalimenti(@PathVariable Integer id, @RequestBody Alimenti alimenti) {

        Alimenti alimento = repo.findById(id).get();
        alimento.setNome(alimenti.getNome());
        alimento.setCalorie(alimenti.getCalorie());
        alimento.setPeso(alimenti.getPeso());

        Alimenti alimentomodificato = repo.save(alimento);

        return ResponseEntity.ok().body(alimentomodificato);


    }

    @DeleteMapping("/deletealimento/{id}")
    public void deleteAlimento(@PathVariable("id") int id){
        repo.deleteById(id);
    }


    @PostMapping("/bmi")
    public float bmicalc(@RequestBody dataBmi bmicalc) {


        int peso = bmicalc.getPeso();
        float altezza = bmicalc.getAltezza();
        int eta = bmicalc.getEta();
        String sesso = bmicalc.getSesso();
        float result = 0;

        if (sesso.equals("donna")){
            result = peso / (altezza * altezza);
            System.out.println("Donna");
        } else if (sesso.equals("uomo"))
            result = peso / (altezza * altezza);
        else {
            System.out.println("Errore");
        }


        System.out.println("Il BMI e'" + result);
        return (int) result;
    }

    @PostMapping("/calorie")
    public float calorie(@RequestBody dataCal bmicalc) {


        int peso = bmicalc.getPeso();
        int altezza = bmicalc.getAltezza();
        int eta = bmicalc.getEta();
        String sesso = bmicalc.getSesso();

        double result = 0;

        if (sesso.equals("donna")){
            result = ((9.6 * (float) peso) + (1.9 * (float) altezza) - (4.7 * (float) eta) + 655.1);
            System.out.println("Donna");
        } else if (sesso.equals("uomo"))
            result = ((13.8 * (float) peso) + (5 * (float) altezza) - (6.8 * (float) eta) + 66.5);
        else {
            System.out.println("Errore");
        }


        System.out.println("Il tuo metabolismo basale e'" + result);
        return (int) result;
    }
}
