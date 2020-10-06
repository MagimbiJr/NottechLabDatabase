package com.tana.nottechlab;

public class Members {
    private String mId;
    private String mName;
    private long mDob;
    private String mDesignation;

    public Members(String name, String designation) {
        mName = name;
        mDesignation = designation;
    }

    public Members(String id, String name, long dob, String designation) {
        mId = id;
        mName = name;
        mDob = dob;
        mDesignation = designation;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getDob() {
        return mDob;
    }

    public void setDob(long dob) {
        mDob = dob;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mDob='" + mDob + '\'' +
                ", mDesignation='" + mDesignation + '\'' +
                '}';
    }
}
