import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GaussImpl extends UnicastRemoteObject implements Gauss {
    private final ExecutorService executor;

    protected GaussImpl() throws RemoteException {
        super();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public double[][] eliminate(double[][] matrix) throws RemoteException {
        int n = matrix.length;

        for (int k = 0; k < n; k++) {
            final int pivot = k;
            for (int i = pivot + 1; i < n; i++) {
                double factor = matrix[i][pivot] / matrix[pivot][pivot];
                for (int j = pivot; j < n + 1; j++) {
                    matrix[i][j] -= factor * matrix[pivot][j];
                }
            }
        }

        return matrix;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
