package com.example.myapplication;
public class errorStateHelper {
    public static boolean passwordErrorRegister = false;
    public static boolean usernameExistsRegister = false;
    public static boolean emailExistsRegister = false;
    public static boolean phoneExistsRegister = false;
    public static boolean blankFieldsRegister = false;
    public static String duplicateMessageRegister = "";

    public static boolean passwordErrorChangeLogin = false;
    public static boolean usernameExistsChangeLogin = false;
    public static boolean emailExistsChangeLogin = false;
    public static boolean phoneExistsChangeLogin = false;
    public static boolean blankFieldsChangeLogin = false;
    public static String duplicateMessageChangeLogin = "";
    public static boolean selectedAdressChangeLogin =false;


    public static boolean usernameEmailErrorLogin = false;
    public static boolean incorrectPasswordLogin = false;
    public static boolean selectedAddress=false;
    public static boolean checkBuildingError=false;

    public static boolean passwordsDontMatchSecurity=false;
    public static boolean emailDoesntExistSecurity=false;
    public static boolean incorrectAnswerSecurity=false;


    public static boolean buildingOrUnit=false;
    public static String building = "";

    public static int stageSecurity=1;

    public static boolean selectedBuildingInspectionError=false;
    public static boolean selectedTypeInspectionError=false;

    public static String currentInspection = "";
    public static String currentBuilding = "";

    public static boolean startedCreation=false;
    public static boolean incorrectInputCreation=true;
    public static boolean blankQuestionCreation=true;
    public static String buildingName="";

    public static void reset() {
        buildingName="";

        passwordErrorRegister = false;
        usernameExistsRegister = false;
        emailExistsRegister = false;
        phoneExistsRegister = false;
        blankFieldsRegister = false;
        duplicateMessageRegister = "";

        usernameEmailErrorLogin = false;
        incorrectPasswordLogin = false;
        selectedAddress=false;
        checkBuildingError=false;

        passwordsDontMatchSecurity=false;
        emailDoesntExistSecurity=false;
        incorrectAnswerSecurity=false;

        stageSecurity=1;

        startedCreation=false;
        incorrectInputCreation=true;
        blankQuestionCreation=true;

    }
}
