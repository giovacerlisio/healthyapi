package com.healthy.healthy.controller;

import com.healthy.healthy.model.Alimenti;
import com.healthy.healthy.model.Data;
import com.healthy.healthy.model.dataCal;
import com.healthy.healthy.repository.AlimentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private AlimentiRepository repo;

    @PostMapping("/alimenti")
    public Object alimenti(@RequestBody Alimenti alimenti) {


        String nome = alimenti.getNome();
        String calorie = alimenti.getCalorie();
        String peso = alimenti.getPeso();

        repo.save(alimenti);

        return null;
    }

    @GetMapping("/listalimenti")
    public List<Alimenti> alimenti(@RequestParam , ) {
        repo.findAll();
        System.out.println(repo);
        return null;
    }

    @PostMapping("/bmi")
    public float bmicalc(@RequestBody Data bmicalc) {


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