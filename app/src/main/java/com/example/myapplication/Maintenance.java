package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Maintenance extends AppCompatActivity {

    private CheckBox row1Ok;
    private CheckBox row1NotOk;


    private Button nextQuestion;
    private Button takePicture;

    private TextView checkBoxError;
    private TextView FieldText;
    private TextView title;
    private TextView section;

    private EditText comments;

    private Switch workOrderSwitch;


    private ArrayList<String> fields;
    private ArrayList<String> sectionNames;
    private ArrayList<String> Images2 = new ArrayList<String>();


    private ImageView test;


    private int id;
    private int questionNumber=0;
    private int wordOrderInputVar;

    private Bitmap image;
    private byte[] byteImage;
    private String encodedImage;
    private Bitmap decodebitmap;

    private String mail="office@patryinc.com";
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    private String message= currentUser.userName + " completed the inspection today at " + formatter.format(date) + "\n\n";
    private String message2="";
    private String subject;
    private String subject2;


    private String numberSubmit="^*";
    private String answerSubmit="^*";
    private String commentSubmit="^*";
    private String workOrderSubmit="^*";
    private String ImageSubmit="^*";

    private String currentSection="";
    private String previousSection="";
    private boolean first=true;

    private int lengthLine;
    private String lengthLineVar;

    Connection connect;
    String ConnectionResult = "";

    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maintenance);


        fields = new ArrayList<>();
        sectionNames = new ArrayList<>();


        FieldText=(TextView) findViewById(R.id.firstRowTextBuilding);
        title = (TextView) findViewById(R.id.inspectionNameMaintenance);
        section=(TextView) findViewById(R.id.sectionNameMaintenance);
        title.setText(errorStateHelper.currentInspection);

        workOrderSwitch = (Switch) findViewById(R.id.wordOrderSwitchBuildingInspection);

        test = (ImageView) findViewById(R.id.testImage);



        row1Ok=(CheckBox) findViewById(R.id.firstOkBuilding);
        row1NotOk=(CheckBox) findViewById(R.id.firstNotOkBuilding);

        comments=(EditText) findViewById(R.id.commentBuilding);
        getQuestions();
        getInspectionID();
        getSection();
        section.setText(sectionNames.get(0));
        takePicture = (Button) findViewById(R.id.takePictureMaint);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });




        nextQuestion = (Button) findViewById(R.id.BuildingNextMaint);
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(questionNumber<=fields.size()-1) {
                    checkError();

                }
                else{
                    if(!row1Ok.isChecked() && !row1NotOk.isChecked()){
                        checkBoxError.setText("One Box Must be Checked");
                        checkBoxError.setVisibility(View.VISIBLE);
                    }
                    else if(row1Ok.isChecked() && row1NotOk.isChecked()){
                        checkBoxError.setText("Cannot Check Both Boxes");
                        checkBoxError.setVisibility(View.VISIBLE);
                    }
                    else{
                        if(workOrderSwitch.isChecked()){
                            wordOrderInputVar=1;
                        }
                        else{
                            wordOrderInputVar=0;
                        }
                        if(row1Ok.isChecked()) {
                            submitAnswers(1);
                        }
                        else{
                            submitAnswers(0);
                        }
                        sendEmail();
                        Intent intent= new Intent(Maintenance.this, com.example.myapplication.homePage.class);
                        startActivity(intent);
                    }


                }

            }
        });

        FieldText.setText(fields.get(0));
        checkBoxError=(TextView) findViewById(R.id.checkBoxErrorBuilding);
        if(errorStateHelper.checkBuildingError){
            checkBoxError.setVisibility(View.VISIBLE);
        }
        else{
            checkBoxError.setVisibility(View.INVISIBLE);
        }


        displayQuestion();
    }

    public void sendEmail(){
        subject="App Building Inspection Report for "+errorStateHelper.building+" Inspection : "+errorStateHelper.currentInspection;
        subject2="IGNORE Images, " +errorStateHelper.building+"END";
        message += "\n\nImage(s) can be found at this link: https://patry.sharepoint.com/sites/PropertyManagementTrial/7%20Photo%20%20Video%20Archive/Forms/AllItems.aspx?viewid=dce4d0f3%2D40de%2D46fe%2Dad73%2D2b23d60471ae&id=%2Fsites%2FPropertyManagementTrial%2F7%20Photo%20%20Video%20Archive%2FApp%20Images";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
        Iterator<String> iter=Images2.iterator();
        while(iter.hasNext()){
            message=iter.next();
            subject="IGNORE Images, " +errorStateHelper.building+"END";
            javaMailAPI = new JavaMailAPI(this,mail,subject,message);
            javaMailAPI.execute();

        }
    }
    public void checkError(){
        //System.out.println("CHECKING ERRORS");
        if(!row1Ok.isChecked() && !row1NotOk.isChecked()){
            checkBoxError.setText("One Box Must be Checked");
            checkBoxError.setVisibility(View.VISIBLE);
        }
        else if(row1Ok.isChecked() && row1NotOk.isChecked()){
            checkBoxError.setText("Cannot Check Both Boxes");
            checkBoxError.setVisibility(View.VISIBLE);
        }
        else if(row1NotOk.isChecked() && comments.getText().toString().equals("")){
            checkBoxError.setText("If not ok, please comment");
            checkBoxError.setVisibility(View.VISIBLE);
        }
        else{
            if(workOrderSwitch.isChecked()){
                wordOrderInputVar=1;
            }
            else{
                wordOrderInputVar=0;
            }
            if(row1Ok.isChecked()) {
                submitAnswers(1);
                displayQuestion();
            }
            else{
                submitAnswers(0);
                displayQuestion();
            }

        }
    }
    public void displayQuestion(){
        FieldText.setText(fields.get(questionNumber));
        section.setText(sectionNames.get(questionNumber));
        if(questionNumber==fields.size()-1){
            nextQuestion.setText("Submit");
        }
        questionNumber++;
        row1Ok.setChecked(false);
        row1NotOk.setChecked(false);
        workOrderSwitch.setChecked(false);
        comments.setText("");
        checkBoxError.setVisibility(View.INVISIBLE);
    }

    public void getInspectionID() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if (connect != null) {
                Statement st = connect.createStatement();

                ResultSet rs = st.executeQuery("SELECT MAX(Inspection_ID) FROM BuildingInspectionAnswers");

                if (rs.next()) {
                    id = rs.getInt(1) + 1;
                    System.out.println("ID IS: " + id);
                }
                st.close();
            }
            else {
                ConnectionResult = "Check Connection";
            }
            connect.close();
        } catch (Exception ex) {
            System.out.println("Get id error");
            id = 1;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            System.out.println("PIC ERROR");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //test.setImageBitmap(image);
            image.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byteImage = byteArrayOutputStream.toByteArray();
            encodedImage = android.util.Base64.encodeToString(byteImage, android.util.Base64.DEFAULT);


            //decode to display
            byte[] decodeString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(
                    decodeString, 0, decodeString.length
            );
            //test.setImageBitmap(decodebitmap);
        }
    }

    public void getQuestions() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if (connect != null) {
                Statement st = connect.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM BuildingInspectionQuestions WHERE Inspection_Title = \'"
                        + errorStateHelper.currentInspection + "\' and Building_Name = \'" + errorStateHelper.currentBuilding + "\'");

                while (rs.next()) {
                    fields.add(rs.getString("Question"));
                }
                st.close();
            }
            else {
                ConnectionResult = "Check Connection";
            }
            connect.close();
        } catch (Exception ex) {
            System.out.println("Get questions error");
        }
    }
    public void getSection() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if (connect != null) {
                Statement st = connect.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM BuildingInspectionQuestions WHERE Inspection_Title = \'"
                        + errorStateHelper.currentInspection + "\' and Building_Name = \'" + errorStateHelper.currentBuilding + "\'");

                while (rs.next()) {
                    sectionNames.add(rs.getString("Section"));
                }
                st.close();
            }
            else {
                ConnectionResult = "Check Connection";
            }
            connect.close();
        } catch (Exception ex) {
            System.out.println("Get questions error");
        }
    }

    public void submitAnswers(int answer) {
        if(first){
            previousSection=section.getText().toString();
            currentSection=section.getText().toString();
            message+="Section: "+currentSection+"\n\n";
            first=false;
        }
        else{
            currentSection=section.getText().toString();
            if(!previousSection.equals(currentSection)){
                message+="Section: "+currentSection+"\n\n";
            }
            previousSection=currentSection;
        }
        if(wordOrderInputVar==1){
            workOrderSubmit=" Work Order Required";
        }
        else{
            workOrderSubmit=" Work Order not Required";
        }
        if(comments.getText().toString().equals("")){
            commentSubmit="No Comments Made";
        }
        else{
            commentSubmit=comments.getText().toString();
        }
        if(answer==0){

            lengthLineVar="Area: "+FieldText.getText().toString()+".  Comments: "+commentSubmit+","+workOrderSubmit;
            message+=lengthLineVar+"\n\n";
            if(encodedImage!=null){
                System.out.println("ADDED\n\nADDEDDDDD");
                Images2.add(encodedImage);
            }
        }



        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if (connect != null) {
                Statement st = connect.createStatement();

                st.executeUpdate("INSERT INTO BuildingInspectionAnswers VALUES (" + id + ", \'"
                        + currentUser.userName + "\', \'" + formatter.format(date) + "\', "
                        + questionNumber + ", " + answer + ", \'" + comments.getText().toString()
                        + "\', " + wordOrderInputVar + ", \'" + encodedImage + "\', \'"
                        + errorStateHelper.currentBuilding + "\', \'" + errorStateHelper.currentInspection + "\')");

                encodedImage = null;

                st.close();

            }
            else {
                ConnectionResult = "Check Connection";
            }
            connect.close();
        } catch (Exception ex) {
            System.out.println("submit answer error");
        }
    }


}