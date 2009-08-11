package uk.co.martinmcnulty.brainfuck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;

import uk.co.martinmcnulty.brainfuck.instruction.*;

public class Interpreter {

    private final Context context;

    public static void main(String[] args) {
	if (args.length != 1) {
	    System.err.println("Usage: java -jar brainfuck-interpreter-XXX.jar <brainfuck-program>");
	    System.exit(1);
	}
	try {
	    FileReader in = new FileReader(new File(args[0]));
	    Interpreter interpreter = new Interpreter(in);
	    interpreter.interpret();
	}
	catch (FileNotFoundException e) {
	    System.err.println("File not found: " + new File(args[0]).getAbsolutePath());
	} catch (IOException e) {
	    System.err.println("Problem reading file: " + new File(args[0]).getAbsolutePath());
	    e.printStackTrace(System.err);
	}
    }

    public Interpreter(Reader in) throws IOException
    {
	this(in, System.in, System.out);
    }

    public Interpreter(Reader programIn, InputStream in, OutputStream out) throws IOException {
	Instruction[] program = parseProgram(programIn);
	this.context = new Context(program, in, out);
    }

    public void interpret()
    {
	try {
	    //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    while (! context.isTerminated()) {
		//in.readLine();
		context.fetchAndExecute();
	    }
	} catch (IOException e) {
	    System.err.println("IO problem executing instruction " + context.getInstructionPointer());
	    e.printStackTrace(System.err);
	}
    }

    private Instruction[] parseProgram(Reader in) throws IOException {
	List<Instruction> program = new ArrayList<Instruction>();
	int token;
	Instruction instruction;
	while ((token = in.read()) != -1) {
	    switch (token) {
	    case '>':
		instruction = new IncDataPointer();
		break;
	    case '<':
		instruction = new DecDataPointer();
		break;
	    case '+':
		instruction = new IncData();
		break;
	    case '-':
		instruction = new DecData();
		break;
	    case '.':
		instruction = new Output();
		break;
	    case ',':
		instruction = new Input();
		break;
	    case '[':
		instruction = new CondForwardJump();
		break;
	    case ']':
		instruction = new CondBackwardJump();
		break;
	    default:
		// Other symbols are ignored
		instruction = null;
	    }
	    if (instruction != null) {
		program.add(instruction);
	    }
	    //System.out.println("Added instruction: " + instruction);
	}
	return program.toArray(new Instruction[program.size()]);
    }
}