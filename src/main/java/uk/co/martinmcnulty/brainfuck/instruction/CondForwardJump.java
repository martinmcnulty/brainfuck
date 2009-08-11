package uk.co.martinmcnulty.brainfuck.instruction;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class CondForwardJump implements Instruction {

    public void execute(Context c) throws IOException {
	if (c.getData() == 0) {
	    int openCount = 1;
	    int instructionPointer = c.getInstructionPointer();
	    while (openCount > 0) {
		Instruction instruction = c.getInstruction(instructionPointer);
		openCount += instruction.getJumpCount();
		instructionPointer++;
	    }
	    c.setInstructionPointer(instructionPointer);
	}
    }

    public int getJumpCount() {
	return 1;
    }
}