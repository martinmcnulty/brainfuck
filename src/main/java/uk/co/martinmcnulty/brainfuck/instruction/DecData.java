package uk.co.martinmcnulty.brainfuck.instruction;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class DecData implements Instruction {

    public void execute(Context c) throws IOException {
	c.decrementData();
    }

    public int getJumpCount() {
	return 0;
    }

}