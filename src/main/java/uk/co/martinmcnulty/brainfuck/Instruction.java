package uk.co.martinmcnulty.brainfuck;

import java.io.IOException;

import uk.co.martinmcnulty.brainfuck.instruction.CondForwardJump;
import uk.co.martinmcnulty.brainfuck.instruction.CondBackwardJump;

public interface Instruction {

    public void execute(Context c) throws IOException;

    /**
     * Gets the 'bracket count:
     * <ul>
     *   <li>1, if this is a {@link CondForwardJump}</li>
     *   <li>-1, if this is a {@link CondBackwardJump}</li>
     *   <li>0 otherwise</li>
     * </ul>
     * @return the 'bracket count'
     */
    public int getJumpCount();
}