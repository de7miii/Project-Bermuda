package com.example.freelanceproject.BusinessModel;

import java.util.ArrayList;
import java.util.List;

public class SkillsCertificates {

    private String mUserId;

    private ArrayList<String> mSkills;
    private ArrayList<String> mCertificates;

    public SkillsCertificates(String userId) {
        mUserId = userId;
        mSkills = new ArrayList<>();
        mCertificates = new ArrayList<>();
    }

    public void addSkill(String skill){
        mSkills.add(skill);
    }

    public void addCertificate(String certificate){
        mCertificates.add(certificate);
    }

    public Boolean removeSkill(String skill){
        return mSkills.remove(skill);
    }
    public Boolean removeCertificate(String certificate){
        return mCertificates.remove(certificate);
    }

    public ArrayList<String> getSkills() {
        return mSkills;
    }

    public ArrayList<String> getCertificates() {
        return mCertificates;
    }

    public String getOwner() {
        return mUserId;
    }
}
