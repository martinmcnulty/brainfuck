package uk.co.martinmcnulty.brainfuck;

import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Context {

    private final byte[] data;
    private int dataPointer;
    private final Instruction[] program;
    private int instructionPointer;
    private final InputStream in;
    private final OutputStream out;

    public Context(Instruction[] program, InputStream in, OutputStream out) {
	this.data = new byte[32768];
	this.dataPointer = 0;
	this.program = program;
	this.instructionPointer = 0;
	this.in = in;
	this.out = out;
    }

    public void fetchAndExecute() throws IOException {
	//System.out.println("Executing: " + program[instructionPointer]);
	Instruction instruction = program[instructionPointer];
	instructionPointer++;
	instruction.execute(this);
	/*StringBuilder sb = new StringBuilder('[');
	for (int i = 0; i < 20; i++) {
	    sb.append(String.valueOf(((int) data[i]) & 0xff));
	    sb.append(',');
	}
	sb.deleteCharAt(sb.length() -1);
	sb.append(']');
	System.out.println(sb);
	System.out.println("Instruction pointer: " + instructionPointer);*/
    }

    public boolean isTerminated() {
	//System.out.println("instructionPointer: " + instructionPointer + ", program length: " + program.length);
	return instructionPointer < 0 || instructionPointer >= program.length;
    }

    public int getInstructionPointer() {
	return instructionPointer;
    }

    public void setInstructionPointer(int instructionPointer) {
	this.instructionPointer = instructionPointer;
    }

    public void incrementDataPointer() {
	dataPointer++;
    }

    public void decrementDataPointer() {
	dataPointer--;
    }

    public void incrementData() {
	data[dataPointer]++;
    }

    public void decrementData() {
	data[dataPointer]--;
    }

    public byte getData() {
	return data[dataPointer];
    }

    public Instruction getInstruction(int index) {
	return program[index];
    }

    public void write() throws IOException {
	out.write(((int) data[dataPointer]) & 0xff);
    }

    public void read() throws IOException {
	int read = in.read();
	if (read == -1) {
	    throw new EOFException();
	}
	//System.out.println("Read: " + read);
	data[dataPointer] = (byte) (read & 0xff);
    }
}
