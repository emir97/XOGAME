package ba.terawatt.xogame;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Emir on 1.11.2015.
 */
public class BazaPodataka {
    public static final String imeBaze = "XOGame_Emir";
    SharedPreferences podaci;
    SharedPreferences.Editor spEditor = podaci.edit();

    public void bazaPodataka(Context context){
        podaci = context.getSharedPreferences(imeBaze, 0);
    }

    public void setUserData(String nameOfData, String Data){
        spEditor.putString(nameOfData, Data);
        spEditor.apply();
    }

    public void setUserData(String nameOfData, int Data){
        spEditor.putInt(nameOfData, Data);
        spEditor.apply();
    }

    public String getUserData(String nameOfData){
         return podaci.getString(nameOfData, "");
    }

    public int getUserDataInt(String nameOfData){
        return podaci.getInt(nameOfData, 0);
    }

    public void clearUserData(){
        spEditor.clear().apply();
    }
}
