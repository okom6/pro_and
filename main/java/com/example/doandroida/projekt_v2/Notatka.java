package com.example.doandroida.projekt_v2;

import java.util.List;

public class Notatka {
public List<String> notatka;
public String user;

        Notatka() {

        }

        Notatka(List<String> notatki, String user) {
        this.notatka = notatki;
        this.user=user;
        }
}