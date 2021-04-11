import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        int A = 127;
        int B = 128;
        int C = 128;
        assertTrue(Flik.isSameNumber(127, A));
        assertTrue(!Flik.isSameNumber(C, B));
    }
}
