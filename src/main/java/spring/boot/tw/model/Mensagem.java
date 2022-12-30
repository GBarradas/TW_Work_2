
package spring.boot.tw.model;

import java.sql.Time;
import java.util.Date;

public class Mensagem implements java.io.Serializable
{
    private long aid;
    private String remetente;
    private String msg;
    private Date data;
    private Time tempo;
    public Mensagem(long aid, String remetente, String msg,Date data){
        this.aid = aid;
        this.remetente = remetente;
        this.msg = msg;
        this.data = data;
    }
    public Mensagem(){
        this.aid = -1;
        this.remetente = null;
        this.msg = null;
        this.data = null;
        this.tempo = null;
    }
    public void setAid (long aid ){
        this.aid = aid;
    }
    public void setRemetente (String remetente){
        this.remetente = remetente;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public void setDate(Date d){
        this.data = d;
    }
    public void setTempo(Time tempo){
        this.tempo = tempo;
    }
    public long getAid (){
        return this.aid;
    }
    public String getRemetente(){
        return this.remetente;
    }
    public String getMsg (){
        return this.msg;
    }
    public Date getData(){
        return this.data;
    }
    public Time getTempo(){
        return this.tempo;
    }
    public String toStringTime(){
        String str = "";
        int hours = this.tempo.getHours();
        int minutes = this.tempo.getMinutes();
        if ((hours < 10)) {
            str += "0" + hours + ":";
        } else {
            str += hours + ":";
        }
        if(minutes< 10){
            str+= "0"+minutes;
        }
        else if(minutes == 0){
            str += "00";
        }
        else{
            str += minutes;
        }
        return str;
    }
    @Override
    public String toString(){
        return "-------------------------------------------------------------------------------\n     "
                +remetente+"("+data+"): " +msg+";";
    }
}