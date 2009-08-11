package uk.co.martinmcnulty.brainfuck;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import junit.framework.TestCase;

import org.junit.Assert;

public class HelloWorldTest extends TestCase {
    public void testHelloWorld() throws IOException {
	testProgram("src/test/resources/hello-world.bf", "Hello World!\n".getBytes());
    }

    public void testCommentedHelloWorld() throws IOException {
	testProgram("src/test/resources/commented-hello-world.bf", "Hello World!\n".getBytes());
    }

    private void testProgram(String path, byte[] expectedOutput) throws IOException {
	Reader program = new FileReader(new File(path));
	ByteArrayOutputStream out = new ByteArrayOutputStream(12);
	Interpreter interpreter = new Interpreter(program, System.in, out);
	interpreter.interpret();
	Assert.assertArrayEquals(expectedOutput, out.toByteArray());
    }
}