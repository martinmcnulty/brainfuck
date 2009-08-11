package uk.co.martinmcnulty.brainfuck.instruction;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class DecDataPointer implements Instruction {

    public void execute(Context c) {
	c.decrementDataPointer();
    }

    public int getJumpCount() {
	return 0;
    }

}