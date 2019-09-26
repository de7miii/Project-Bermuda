package com.example.freelanceproject.BusinessModel;

import java.util.ArrayList;

public class Client extends User {

    public Client(String username, String email, String password){
        setId(User.generateUniqieId());
        setUserName(username);
        setEmail(email);
        setPassword(password);
        setAccType(User.CLIENT_ACCOUNT);
        setName(username);
        initNewUser();
    }

    public Client(int userId, String name, String userName, String email){
        setId(User.generateUniqieId());
        setName(name);
        setUserName(userName);
        setEmail(email);
        setAccType(User.CLIENT_ACCOUNT);
        setAge(0);
        setRatesMin(0);
        setRatesMax(0);
        createNewPortfolio(new Portfolio(getId()));
    }

    private void initNewUser(){
        setAge(0);
        setRatesMin(0);
        setRatesMax(0);
        createNewPortfolio(new Portfolio(getId()));
    }

    public void createProject(String name, String description, int duration, int budget){
        Project project = new Project(name, description, duration, budget);
        if (project == null){
            return;
        }
        getPortfolio().addProject(project);
    }

    public void addEmployeeToProject(String projectName, int freelancerId){
        ArrayList<Project> clientProjects = getPortfolio().getProjects();
        for (Project project :
                clientProjects) {
            if (project.getName().equals(projectName)){
                if (!project.getEmployees().contains(freelancerId)){
                    project.addEmployee(freelancerId);
                }
            }
        }
    }

    public ArrayList<Project> getProjects(){
        return getPortfolio().getProjects();
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

    public void createNewPortfolio(Portfolio portfolio){
        setPortfolio(portfolio);
    }

    public Boolean isClient(){
        return getAccType() == 2;
    }

}