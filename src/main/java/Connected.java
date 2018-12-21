import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Connected {

    private static class Connection {
        final String from;
        final String to;

        Connection( String from, String to ) {
            this.from = from;
            this.to = to;
        }

        static Connection fromString( String sconn ) {
            List<String> conn = Arrays.asList( sconn.split( ", " ) );
            return new Connection( conn.get(0), conn.get(1) );
        }
    }

    private static Map<String, Set<String>> updateWithConnection( Map<String, Set<String>> routes,
                                                                  Connection conn ) {
        if ( ! conn.from.equals( conn.to ) ) {
            routes.computeIfAbsent(
                    conn.from,
                    k -> new HashSet<String>() {{
                        add(conn.to);
                    }})
                    .add(conn.to);

            routes.computeIfAbsent(
                    conn.to,
                    k -> new HashSet<String>() {{
                        add(conn.from);
                    }})
                    .add(conn.from);
        }
        
        return routes;

    }

    private static void addConnection( Map<String, Set<String>> connections,
                                       Connection conn ) {

        if ( ! connections.getOrDefault( conn.from, new HashSet<String>() )
                          .contains( conn.to ) ) {

            updateWithConnection(connections, conn);

            connections.forEach(
                    (city, connected) -> {

                        if (connected.contains(conn.to) || connected.contains(conn.from)) {

                            addConnection(connections, new Connection(conn.to, city));
                            addConnection(connections, new Connection(conn.from, city));
                        }
                    });
        }
    }

    private static Map<String, Set<String>> parseConnections( String path ) throws IOException, URISyntaxException {

        Map<String, Set<String>> connections = new HashMap<>();

        Files.lines( Paths.get( path ) )
                .map( Connection::fromString )
                .forEach( c -> addConnection( connections, c ) );

        return connections;
    }

    public static void main( String[] args ) throws IOException, URISyntaxException {

        final String usage = "\nthis program reads a file with pairs of connected cities,\n" +
                             "creates all the possible interconnections between them,\n" +
                             "and helps humans to determine whether " +
                             "any two given cities are interconnected.\n\n" +
                             "usage:       java Connected <path to a file with connections> <from> <to>\n" +
                             "for example: java Connected ./cities.txt \"New York\" \"Tampa\"";

        if ( args.length != 3 ) {
            System.err.println( "[PROBLEM]: I need exactly 3 arguments, but was given " +
                                args.length + " arguments"  );
            System.err.println( usage );
            System.exit(1);
        }

        String pathToRoutes = args[0];
        String from = args[1];
        String to = args[2];

        if ( pathToRoutes.isEmpty() || from.isEmpty() || to.isEmpty() ) {
            System.err.println( "[PROBLEM]: I am missing some data. Here is what I was given: " +
                                "path-to-connections: " + pathToRoutes +
                                " from: " + from +
                                " to: " + to );
            System.err.println( usage );
            System.exit(1);
        }

        Map<String, Set<String>> connections = parseConnections( pathToRoutes );

        System.out.print( "checking whether " + from + " is connected to " + to + "...  " );

        System.out.println(
                connections
                .getOrDefault( from, new HashSet<>() )
                .contains( to ) );
    }
}
