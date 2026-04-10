import java.sql.Connection;
import java.util.Optional;

public interface ServerStart {
    public void cacheRoute(Optional<Connection> con, String tname);
}
