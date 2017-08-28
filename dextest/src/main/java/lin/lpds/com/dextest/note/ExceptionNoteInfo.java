package lin.lpds.com.dextest.note;

import javax.sql.StatementEvent;

/**
 * Created by linhui on 2017/8/28.
 */
public class ExceptionNoteInfo extends NoteInfo {



    private ExceptionNoteInfo(String action) {
        super(action);
    }


    public ExceptionNoteInfo(Throwable error){
        createT(error);
    }

    private void createT(Throwable error) {
        StringBuilder s = new StringBuilder();
        s.append(error.toString());
        s.append("\n");
        StackTraceElement[] tatementEvents = error.getStackTrace();
        for(StackTraceElement se : tatementEvents){
            s.append(se.toString());
            s.append("\n");
        }
        setAction(s.toString());
    }


}
