package org.example.model;

import org.example.exceptions.WoluminException;

import java.util.List;

public class Ksiazka extends Wolumin{

    private List<String> autor;

    public Ksiazka(String wydawnictwo, String jezyk, String tytul, List<String> autor) {
        super(wydawnictwo, jezyk, tytul);
        if(autor==null||autor.isEmpty()){
            throw new WoluminException("Brak autora książki");
        }
        this.autor = autor;

    }
    public Ksiazka() {}

    public List<String> getAutor() {
        return autor;
    }

    public void setAutor(List<String> autor) {
        if(autor==null||autor.isEmpty()){
            throw new WoluminException("Brak autora książki");
        }
        this.autor = autor;
    }
}
