package app;

import userinterfaces.SystemStartUI;
import util.*;

public class Main{
    private static AccountFileHandler ACCOUNTFILEHANDLER = AccountFileHandler.getInstance();
    private static StudentFileHandler STUDENTFILEHANDLER = StudentFileHandler.getInstance();
    public static void main (String[]args){
        ACCOUNTFILEHANDLER.createAccountsFile();
        STUDENTFILEHANDLER.createStudentsFile();
        SystemStartUI systemStartUI = new SystemStartUI();
        systemStartUI.start();
    }
}