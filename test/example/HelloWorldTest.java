package example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void testHelloWorldMessage() {
        String message = "Hello, World!";
        assertEquals("Hello, World!", message, "The message should be 'Hello, World!'");
    }
    
}
