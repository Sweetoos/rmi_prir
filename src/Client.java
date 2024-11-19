import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Gauss service = (Gauss) registry.lookup("GaussService");

            int[] sizes = {10, 100, 1000,10000};
            for (int size : sizes) {
                System.out.println("Size: " + size);

                double[][] matrix = generateRandomMatrix(size);

                // Sekwencyjne rozwiązanie
                double[][] sequentialMatrix = copyMatrix(matrix);
                long startSeq = System.nanoTime();
                sequentialGaussElimination(sequentialMatrix);
                long endSeq = System.nanoTime();
                System.out.println("Sequential: " + (endSeq - startSeq) / 1e6 + " ms");

                // Równoległe rozwiązanie
                double[][] parallelMatrix = copyMatrix(matrix);
                long startPar = System.nanoTime();
                double[][] result = service.eliminate(parallelMatrix);
                long endPar = System.nanoTime();
                System.out.println("Parallel: " + (endPar - startPar) / 1e6 + " ms");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double[][] generateRandomMatrix(int size) {
        double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] = Math.random() * 100;
            }
        }
        return matrix;
    }

    private static double[][] copyMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] copy = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, n + 1);
        }
        return copy;
    }

    private static void sequentialGaussElimination(double[][] matrix) {
        int n = matrix.length;
        for (int k = 0; k < n; k++) {
            for (int i = k + 1; i < n; i++) {
                double factor = matrix[i][k] / matrix[k][k];
                for (int j = k; j < n + 1; j++) {
                    matrix[i][j] -= factor * matrix[k][j];
                }
            }
        }
    }
}
