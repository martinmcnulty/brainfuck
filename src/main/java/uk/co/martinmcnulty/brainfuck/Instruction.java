package uk.co.martinmcnulty.brainfuck;

import java.io.IOException;

public interface Instruction {
    public void execute(Context c) throws IOException;
}