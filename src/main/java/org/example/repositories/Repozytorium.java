package org.example.repositories;

import org.example.exceptions.RepozytoriumException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Repozytorium<T> {
    protected List<T> registry = new ArrayList<>();

    public void dodaj(T model) {
        if (model == null) {
            throw new RepozytoriumException("Nie można dodać do repozytorium");
        }
        registry.add(model);
    }

    public void usun(T model) {
        if (model == null) {
            throw new RepozytoriumException("Nie można usunąć z repozytorium");
        }
        registry.removeIf(m -> m.equals(model));
    }

    public int size() {
        return registry.size();
    }

    public List<T> znajdz(Predicate<T> matchingMethod) {
        return registry.stream()
                .filter(matchingMethod)
                .collect(Collectors.toList());
    }

    public List<T> znajdzWszystkie() {
        return znajdz(elem -> true);
    }

    public T znajdzPoId(UUID id) {
        return znajdz(m -> {
            try {
                return m.getClass().getMethod("getUuid").invoke(m).equals(id);
            } catch (Exception e) {
                return false;
            }
        }).stream().findFirst().orElse(null);
    }
}
