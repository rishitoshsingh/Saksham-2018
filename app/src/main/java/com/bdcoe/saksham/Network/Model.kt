package com.bdcoe.saksham.Network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class Model(mName: String, mStudentNo: String, mBranch: String, mYear: String, mContactNumber: String, mSportsInterested: String, mHosteler: String, mGender: String) {

    @SerializedName("Name")
    @Expose
    private lateinit var name: String
    @SerializedName("StudentNo")
    @Expose
    private lateinit var studentno: String
    @SerializedName("Branch")
    @Expose
    private lateinit var branch: String
    @SerializedName("Year")
    @Expose
    private lateinit var year: String
    @SerializedName("ContactNumber")
    @Expose
    private lateinit var contactnumber: String
    @SerializedName("SportsInterested")
    @Expose
    private lateinit var sportsinterested: String
    @SerializedName("Hosteler")
    @Expose
    private lateinit var hosteler: String
    @SerializedName("Gender")
    @Expose
    private lateinit var gender: String

    fun getName(): String {
        return name;
    }

    fun setName(name: String) {
        this.name = name;
    }

    fun getStudentNo(): String {
        return studentno;
    }

    fun setStudentNo(studentno: String) {
        this.studentno = studentno;
    }

    fun getBranch(): String {
        return branch;
    }

    fun setBranch(branch: String) {
        this.branch = branch;
    }

    fun getYear(): String {
        return year;
    }

    fun setYear(year: String) {
        this.year = year;
    }

    fun getContactNumber(): String {
        return contactnumber
    }

    fun setContactNo(contactnumber: String) {
        this.contactnumber = contactnumber;
    }

    fun getSportsInterested(): String {
        return sportsinterested;
    }

    fun setSportsInterested(sportsinterested: String) {
        this.sportsinterested = sportsinterested;
    }

    fun getHosteler(): String {
        return hosteler;
    }

    fun setHosteler(hosteler: String) {
        this.hosteler = hosteler;
    }

    fun getGender(): String {
        return gender;
    }

    fun setGender(gender: String) {
        this.gender = gender;
    }

}