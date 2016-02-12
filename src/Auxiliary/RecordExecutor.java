package Auxiliary;

/**
 * Base class for recorded actions execution.
 * @see Recorder
 */
public abstract class RecordExecutor implements Runnable, Cloneable{
    protected String[] args;

    public void setArgs(String[] args){
        this.args = args;
    }
}
