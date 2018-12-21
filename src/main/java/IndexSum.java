import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexSum {

    public static void main(String[] args) {

        final List<Integer> digits = Arrays.asList( 2,4,5,4,6 );
        final Map<Integer, Integer> m = new HashMap<>();
        final int sum = 10;

        int idx = 0;
        for ( Integer digit : digits ) {

            Integer matchIdx = m.get( sum - digit );

            if ( matchIdx != null ) {
                System.out.println( sum - digit + " [idx: " + matchIdx +
                        "] + " + digit + " [idx: " + idx + "] = " + sum );
                break;
            }
            else {
                m.putIfAbsent(digit, idx);
            }

            idx++;
        }
    }
}
