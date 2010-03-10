package uk.co.martinmcnulty.brainfuck.instruction;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class CondJump implements Instruction {

    private int jumpDestination = -1;

    private final boolean forward;

    public CondJump(boolean forward) {
	this.forward = forward;
    }

    public void execute(Context c) throws IOException {
	if ((forward && c.getData() == 0) || (! forward && c.getData() != 0)) {
	    if (jumpDestination == -1) {
		int instructionPointer = c.getInstructionPointer();
		jumpDestination = getJumpDestination(instructionPointer, c);
	    }
	    c.setInstructionPointer(jumpDestination);
	}
    }

    public int getJumpCount() {
	return forward ? 1 : -1;
    }

    private int getJumpDestination(int instructionPointer, Context c) {
	int openCount = getJumpCount();
	while (openCount != 0) {
	    Instruction instruction = c.getInstruction(instructionPointer + (forward ? 0 : -2));
	    openCount += instruction.getJumpCount();
	    instructionPointer += forward ? 1 : -1;
	}
	return instructionPointer;
    }

}