package utils;

import java.util.Random;

public class PerlinNoise {

    private int[] permutation; // Stock l'ordre de permutation des valeurs

    // Constructeur qui initialise le Perlin Noise en fonction d'une seed
    public PerlinNoise(long seed) {
        permutation = new int[512]; // Permet d'éviter d'accéder aux valeurs en dehors des limites
        Random random = new Random(seed);

        // Remplissage initial avec des valeurs de 0 à 255
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        // Mélange aléatoire des valeurs pour obtenir des résultats variés
        for (int i = 0; i < 256; i++) {
            int j = random.nextInt(256);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        // Duplication des valeurs pour simplifier les calculs périodique
        for (int i = 0; i < 256; i++) {
            permutation[256 + i] = permutation[i];
        }
    }

    // Fonction d'interpolation pour lisser les transitions de valeurs
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Fonction d'interpolation linéaire entre deux points
    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Fonction qui génère un gradient en fonction d'un hash et des coordonnées
    private static double grad(int hash, double x, double y) {
        int h = hash & 3;
        double u = h < 2 ? x : y;
        double v = h < 2 ? y : x;

        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    // Génère une valeur de noise pour les coordonnées spécifiés
    public double noise(double x, double y) {

        // Trouve la position de la grille
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;

        // Coordonnées dans la cellule de la grille
        x -= Math.floor(x);
        y -= Math.floor(y);

        // Applique la courbe de lissage
        double u = fade(x);
        double v = fade(y);

        //Récupère les indices pour le calcule de noise
        int aa = permutation[X] + Y;
        int ab = permutation[X] + Y + 1;
        int ba = permutation[X + 1] + Y;
        int bb = permutation[X + 1] + Y + 1;

        return lerp(v, lerp(u, grad(permutation[aa], x, y), grad(permutation[ba], x -1, y)),
                lerp(u, grad(permutation[ab], x, y - 1), grad(permutation[bb], x -1, y -1)));
    }
}
