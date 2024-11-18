import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GaussImpl extends UnicastRemoteObject implements Gauss{
    protected GaussImpl() throws RemoteException {
        super();
    }
    @Override
    public void eliminate(double[][] matrix, int row) throws RemoteException {
        int n = matrix.length;
        for (int i = row + 1; i < n; i++) {
            double factor = matrix[i][row] / matrix[row][row];
            for (int j = row; j < n + 1; j++) {
                matrix[i][j] -= factor * matrix[row][j];
            }
        }
    }
}

