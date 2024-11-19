import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Gauss extends Remote {
    double[][] eliminate(double[][] matrix) throws RemoteException;
}
