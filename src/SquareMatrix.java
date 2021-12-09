import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SquareMatrix {

    int dim;
    double[][] m;
    String input;

    // konstruktor podanej macierzy
    public SquareMatrix(String input) {
        this.input = input;
        this.dim = getDim();
        this.m = new double[dim][dim];

        String liczby = input.replaceAll
                ("\\[", "").replaceAll
                ("\\]", "").replaceAll
                (";", " ");

        String[] tab1 = liczby.split(" ");
        double[] tab2 = Arrays.stream(tab1).mapToDouble
                (Double::parseDouble).toArray();

        int temp = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.m[i][j] = tab2[temp];
                temp++;
                System.out.print(this.m[i][j] + " \t");
            }
            System.out.print("\n");
        }
    }

    // konstruktor losowej macierzy
    public SquareMatrix(int dim) {
        this.dim = dim;
        this.m = new double[dim][dim];
        Random r = new Random();

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.m[i][j] = r.nextInt(10);
                System.out.print(this.m[i][j] + " \t");
            }
            System.out.print("\n");
        }
    }

    public int getDim() {
        String temp = this.input.replaceAll
                ("\\[", "").replaceAll
                ("\\]", "");
        char znak = ';';
        int licznik = 0;
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == znak) {
                licznik++;
            }
        }
        licznik++;
        return licznik;
    }

    public String toString() {
        StringBuilder odpowiedz = new StringBuilder();

        odpowiedz.append("[");
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                odpowiedz.append(this.m[i][j]).append(" ");
            }
            if (i + 1 < this.dim) odpowiedz.append(";");
        }
        odpowiedz.append("]");
        return odpowiedz.toString();
    }

    public double getDet() {
        double wyznacznik;
        double x, y, z = 0;

        if (dim == 2) {
            wyznacznik = ((this.m[0][0] * this.m[1][1]) - (this.m[0][1] * this.m[1][0]));
            return wyznacznik;
        } else if (dim == 1) {
            wyznacznik = this.m[0][0];
            return wyznacznik;
        } else {
            System.out.println("Podany zakres jest inny niż 2x2 lub 3x3");
        }
        return 0;
    }

    public void trojkatna() {
        double[][] upper = new double[dim][dim];
        double[][] lower = new double[dim][dim];

        int suma1 = 0;
        double suma2 = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = i; j < dim; j++) {
                for (int k = 0; k < i; k++) {
                    suma1 += (lower[i][k] * upper[k][j]);
                }
                upper[i][j] = m[i][j] - suma1;
            }
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(upper[i][j] + "  ");
                if (i == j) {
                    suma2 *= upper[i][j];
                }
            }
            System.out.println();
        }
        System.out.println("Wynik mnozenia po przekatnej: " + suma2);
    }

    public double getSarrus() {
        double wyznacznik;
        double x, y, z = 0;
        if (dim == 3) {
            x = (this.m[1][1] * this.m[2][2]) - (this.m[2][1] * this.m[1][2]);
            y = (this.m[1][0] * this.m[2][2]) - (this.m[2][0] * this.m[1][2]);
            z = (this.m[1][0] * this.m[2][1]) - (this.m[2][0] * this.m[1][1]);
            wyznacznik = (this.m[0][0] * x) - (this.m[0][1] * y) + (this.m[0][2] * z);
            return wyznacznik;
        } else {
            System.out.println("Macierz jest innego stopnia niz 3");
        }
        System.out.print("sarrus ");
        return 0;
    }

    public static double getDetL(double[][] macierz) {
        double size = macierz.length;
        double wyznacznik = 0;

        for (int i = 0; i < size; i++) {
            if (size > 2) {
                double[][] nextMatrix = getMinor(i, 0, macierz);
                wyznacznik += macierz[i][0] * Math.pow(-1, i) * getDetL(nextMatrix);
            } else if (size == 2) {
                wyznacznik = macierz[0][0] * macierz[1][1] - macierz[0][1] * macierz[1][0];
            }
        }
        return wyznacznik;
    }

    public static double[][] getMinor(int wiersz, int kolumna, double[][] macierz) {
        int oldSize = macierz.length;
        int newSize = macierz.length - 1;

        double[][] temp = new double[newSize][oldSize];

        for (int i = 0; i < wiersz; i++) {
            for (int j = 0; j < oldSize; j++) {
                temp[i][j] = macierz[i][j];
            }
        }

        for (int i = wiersz; i < newSize; i++) {
            for (int j = 0; j < oldSize; j++) {
                temp[i][j] = macierz[i + 1][j];
            }
        }

        double[][] newTemp = new double[newSize][newSize];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < kolumna; j++) {
                newTemp[i][j] = temp[i][j];
            }
        }
        for (int i = 0; i < newSize; i++) {
            for (int j = kolumna; j < newSize; j++) {
                newTemp[i][j] = temp[i][j + 1];
            }
        }
        return newTemp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = "[1 2 3;4 5 6;7 8 9.5]";

        System.out.println("Twoja macierz to:");
        SquareMatrix m = new SquareMatrix(input);
        System.out.println("dim = " + m.getDim());
        System.out.println("det = " + m.getSarrus());
        double wyznacznik1 = getDetL(m.m);
        System.out.println("det(Laplace) = " + wyznacznik1);

        System.out.println("\nMacierz trojkatna podanej macierzy:");
        m.trojkatna();

        System.out.print("\nPodaj dim dla losowej macierzy: ");
        int x = scanner.nextInt();

        System.out.println("\nTwoja losowa macierz to:");
        SquareMatrix m2 = new SquareMatrix(x);
        System.out.println("dim = " + m2.dim);

        double wyznacznik2 = getDetL(m2.m);
        if (x == 3) {
            System.out.println("det(Sarrus) = " + m2.getSarrus());
            System.out.println("det(Laplace) = " + wyznacznik2);
        } else if (x < 3) {
            System.out.println("det = " + m2.getDet());
        } else {
            System.out.println("det(Laplace) = " + wyznacznik2);
        }

        System.out.println("Postac matlabowa: " + m2.toString());
    }
}
