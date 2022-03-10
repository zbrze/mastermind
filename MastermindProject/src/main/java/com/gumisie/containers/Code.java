package com.gumisie.containers;

import javafx.scene.paint.Color;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Code {

    @NonNull
    private final List<Color> colors;

    public Code(List<Color> colors) {
        this.colors = colors;
    }

    public Code(Color... colors){
        this.colors = List.of(colors);
    }

    public Code(int size){
        this.colors = new ArrayList<>(size);
    }

    public void putColor(int i, Color color){
        this.colors.add(i, color);
    }

    public Color getColor(int i){
        return this.colors.get(i);
    }

    public List<Color> getColors(){
        return this.colors;
    }

    public String toString(){
        return Arrays.toString(colors.toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return colors.equals(code.colors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colors);
    }

    public int containsColor(Color color) {
        if(color != null) return this.colors.indexOf(color);
        return -1;
    }
}
