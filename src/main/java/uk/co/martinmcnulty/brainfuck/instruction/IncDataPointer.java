package uk.co.martinmcnulty.brainfuck.instruction;

import uk.co.martinmcnulty.brainfuck.Instruction;
import uk.co.martinmcnulty.brainfuck.Context;

public class IncDataPointer implements Instruction {

    public void execute(Context c) {
	c.incrementDataPointer();
    }

}