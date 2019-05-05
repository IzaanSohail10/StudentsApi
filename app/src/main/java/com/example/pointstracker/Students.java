package com.example.pointstracker;

public class Students{


    private String studentName;
    private String pointnumber;
    private String studentID;
    private String GaurdianCNIC;
    private String email;

    public Students(){
    }
    public Students(String studentName, String studentID,String GaurdianCNIC,String email, String pointnumber){
        this.studentName = studentName;
        this.studentID = studentID;
        this.GaurdianCNIC = GaurdianCNIC;
        this.email=email;
        this.pointnumber = pointnumber;
    }


    public String getStudentName() { return studentName; }
    public  String getEmail(){return email;}
    public String getStudentID() { return studentID; }
    public String getGaurdianCNIC() {return GaurdianCNIC; }
    public String getPointnumber() {return pointnumber; }
}
