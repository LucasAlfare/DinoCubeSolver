package com.dino.solver;

import com.dino.main.Dino;

public class Main {

    public static void main(String[] args) {
        Dino d = new Dino();
        d.applySequence("2 -3");
        System.out.println(d.isSolved());
        System.out.println(d);
        System.out.println(d.getSolution());
        System.out.println(d.isSolved());
    }
}
