import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Gauss extends Remote {
    void eliminate(double[][] matrix, int row) throws RemoteException;
}
