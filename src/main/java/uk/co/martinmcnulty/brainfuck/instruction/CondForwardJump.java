package uk.co.martinmcnulty.brainfuck.instruction;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class CondForwardJump implements Instruction {

    private int jumpDestination = -1;

    public void execute(Context c) throws IOException {
	if (c.getData() == 0) {
	    if (jumpDestination == -1) {
		int instructionPointer = c.getInstructionPointer();
		jumpDestination = getJumpDestination(instructionPointer, c);
	    }
	    c.setInstructionPointer(jumpDestination);
	}
    }

    public int getJumpCount() {
	return 1;
    }

    private int getJumpDestination(int instructionPointer, Context c) {
	int openCount = getJumpCount();
	while (openCount != 0) {
	    Instruction instruction = c.getInstruction(instructionPointer);
	    openCount += instruction.getJumpCount();
	    instructionPointer++;
	}
	return instructionPointer;
    }

}