package com.example.freelanceproject.BusinessModel;

import java.util.ArrayList;
import java.util.List;

public class Freelancer extends User {


    private SkillsCertificates mSkills_Certificates;

    public Freelancer(String username, String email, String password){
        setId(User.generateUniqieId());
        setUserName(username);
        setEmail(email);
        setPassword(password);
        setAccType(User.FREELANCER_ACCOUNT);
        setName(username);
        initNewUser();
    }

    private void initNewUser(){
        setAge(0);
        setRatesMin(0);
        setRatesMax(0);
        createNewPortfolio(new Portfolio(getId()));
        mSkills_Certificates = new SkillsCertificates(getId());
        setCredScore(0);
    }

    public void createProject(String name, String description, int duration, int budget){
        Project project = new Project(name, description, duration, budget);
        if (project == null){
            return;
        }
        getPortfolio().addProject(project);
    }

    public Project getProject(String projectName){
        return getPortfolio().getProject(projectName);
    }

    public void setUserAge(int age){
        setAge(age);
    }

    public void setUserDescription(String description){
        setDescription(description);
    }

    public void setMinRate(int minRate){
        setRatesMin(minRate);
    }

    public void setMaxRate(int maxRate){
        setRatesMax(maxRate);
    }

    public void createNewPortfolio(Portfolio portfolio){
        setPortfolio(portfolio);
    }


    //all basic information are implemented in User class
    //protfolio to be implemented as a generic class


    public void addSkill(String skill){
        mSkills_Certificates.addSkill(skill);
    }

    public void addCertificate(String certificate){
        mSkills_Certificates.addCertificate(certificate);
    }

    public Boolean removeSkill(String skill){
        return mSkills_Certificates.removeSkill(skill);
    }

    public Boolean removeCertificate(String certificate){
        return mSkills_Certificates.removeCertificate(certificate);
    }

    public ArrayList<String> getSkills() {
        return mSkills_Certificates.getSkills();
    }

    public List<String> getCertificates() {
        return mSkills_Certificates.getCertificates();
    }

    public Boolean isFreelancer(){
        return getAccType() == 1;
    }

}
