package uk.co.martinmcnulty.brainfuck;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.instruction.CondJump;

public interface Instruction {

    public void execute(Context c) throws IOException;

    /**
     * Gets the 'bracket count:
     * <ul>
     *   <li>1, if this is a forward {@link CondJump}</li>
     *   <li>-1, if this is a backward {@link CondJump}</li>
     *   <li>0 otherwise</li>
     * </ul>
     * @return the 'bracket count'
     */
    public int getJumpCount();
}