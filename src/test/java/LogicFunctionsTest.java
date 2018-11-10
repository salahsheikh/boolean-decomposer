import com.booleandecomposer.BoolLexer;
import com.booleandecomposer.BoolParser;
import com.booleandecomposer.Sanitizer;
import com.booleandecomposer.TruthTable;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogicFunctionsTest {

    @Test
    public void testNOT() {
        String exp = "NOT A";
        TruthTable t = new TruthTable(exp);
        t.generateTruthTable();
        assertTrue(t.getResults()[0]);
        assertTrue(!t.getResults()[1]);
    }

    @Test
    public void testOR() throws IOException {
        String exp = "A + B";
        TruthTable t = new TruthTable(exp);
        t.generateTruthTable();
        assertTrue(!t.getResults()[0]);
        assertTrue(t.getResults()[1]);
        assertTrue(t.getResults()[2]);
        assertTrue(t.getResults()[3]);
    }

}
