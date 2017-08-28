package lin.lpds.com.dextest.note;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linhui on 2017/8/24.
 */
public class NoteInfo {

    String time = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());

    public NoteInfo(String action) {
        this.action = action;
    }

    protected NoteInfo(){

    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    String action;



}
