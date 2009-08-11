package uk.co.martinmcnulty.brainfuck.instruction;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class CondBackwardJump implements Instruction {

    public void execute(Context c) throws IOException {
	if (c.getData() != 0) {
	    int closeCount = 1;
	    int instructionPointer = c.getInstructionPointer();
	    while (closeCount > 0) {
		Instruction instruction = c.getInstruction(instructionPointer - 2);
		closeCount -= instruction.getJumpCount();
		instructionPointer--;
	    }
	    c.setInstructionPointer(instructionPointer);
	}
    }

    public int getJumpCount() {
	return -1;
    }
}