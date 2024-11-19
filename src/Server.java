import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            GaussImpl service = new GaussImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("GaussService", service);
            System.out.println("RMI server started");

            Runtime.getRuntime().addShutdownHook(new Thread(service::shutdown));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
