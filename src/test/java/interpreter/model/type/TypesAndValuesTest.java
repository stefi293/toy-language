package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypesAndValuesTest {

    @Test
    void intTypeReturnsCorrectDefaultValue() {
        IntType type = new IntType();
        assertEquals(new IntValue(0), type.defaultValue());
    }

    @Test
    void boolTypeReturnsCorrectDefaultValue() {
        BoolType type = new BoolType();
        assertEquals(new BoolValue(false), type.defaultValue());
    }

    @Test
    void intValueReturnsIntType() {
        IntValue val = new IntValue(5);
        assertEquals(new IntType(), val.getType());
    }

    @Test
    void boolValueReturnsBoolType() {
        BoolValue val = new BoolValue(true);
        assertEquals(new BoolType(), val.getType());
    }

    @Test
    void recordEqualsWorksAutomatically() {
        assertEquals(new IntType(), new IntType());
        assertEquals(new IntValue(10), new IntValue(10));
        assertNotEquals(new IntValue(10), new IntValue(5));
    }

    @Test
    void stringValueReturnsStringType() {
        StringValue val = new StringValue("test");
        assertEquals(new StringType(), val.getType());
    }
    @Test
    void recordEqualsAndHashCodeWorksAutomatically() {
        assertEquals(new IntType(), new IntType());
        assertEquals(new IntValue(10), new IntValue(10));
        assertNotEquals(new IntValue(10), new IntValue(5));

        // String type & value equality + hashCode check for FileTable key lookup
        assertEquals(new StringType(), new StringType());
        StringValue str1 = new StringValue("test.in");
        StringValue str2 = new StringValue("test.in");
        assertEquals(str1, str2);
        assertEquals(str1.hashCode(), str2.hashCode());
        assertNotEquals(str1, new StringValue("other.in"));
    }
}