package org.example;

import org.bson.types.ObjectId;
import org.example.databasemanagers.ZarzadcaWoluminu;
import org.example.model.Wolumin;

import java.util.Scanner;

public class TestConsistency {
    private static ZarzadcaWoluminu zarzadca;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        zarzadca = new ZarzadcaWoluminu();
        Wolumin wolumin = new Wolumin("Wydawnictwo", "Pol", "Tytuł");
        while(true) {
            System.out.println("Wpisz coś na konsoli:");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("1")) {
                wolumin.setId(new ObjectId());
                zarzadca.dodajWolumin(wolumin);

            }
            else if (userInput.equalsIgnoreCase("2")) {
                Wolumin retrieved = zarzadca.znajdzWolumin(wolumin.getId());
                if (retrieved != null) {
                    System.out.println("Znalazło");
                }else {
                    System.out.println("nie znalazło");
                }
            }
            else if (userInput.equalsIgnoreCase("3")) {
                zarzadca.zamknijPolaczenie();
                break;
            }
        }

    }
}