package ru.technoserv.audit;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import ru.technoserv.services.CurrentTime;
import ru.technoserv.services.DataTimeService;


@Aspect
public class Visitor {

    @Pointcut("execution(* ru.technoserv.services.EmployeeService.addEmployee(String, String)) && args(firstName, lastName)")
    public void addUser(String firstName, String lastName){}
    @Autowired
    private VisitStory visitStory;

    @After("addUser(firstName, lastName)")
    public void recordVisit(String firstName, String lastName){
        DataTimeService date = new CurrentTime();
        visitStory.setVisitor("Default");
        visitStory.setServerActions("addEmployee with lastName: " + lastName);
        System.out.println("User was added "+lastName +" time "+ date.getCurrentTime());
    }
}