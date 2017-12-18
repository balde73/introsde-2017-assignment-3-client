package introsde.assignment3.client;
 
import introsde.assignment3.soap.Activity;
import introsde.assignment3.soap.People;
import introsde.assignment3.soap.Person;
import introsde.assignment3.utils.PeopleGenerator;
import introsde.assignment3.utils.PrettyPrinter;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
 
public class Client{
	public static void main(String[] args) throws Exception {
		URL url = new URL("https://introsde-3.herokuapp.com/people?wsdl");
        //1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soap.assignment3.introsde/", "PeopleImplService");
        Service service = Service.create(url, qname);
        People peopleService = service.getPort(People.class);
        
        PrettyPrinter printer = new PrettyPrinter("client-server.log");
        PeopleGenerator generator = new PeopleGenerator();
        
        printer.printStep(0, "Creating 3 new random person and activities if database is empty");
        List<Person> people = peopleService.readPersonList();
        if(people.size() == 0) {
	        Person p1 = peopleService.createPerson(generator.createPerson());
	        Person p2 = peopleService.createPerson(generator.createPerson());
	        Person p3 = peopleService.createPerson(generator.createPerson());
	        
	        peopleService.savePersonPreferences(p1.getId(), generator.createActivity("Sport"));
	        peopleService.savePersonPreferences(p2.getId(), generator.createActivity("Education"));
	        peopleService.savePersonPreferences(p2.getId(), generator.createActivity("Sport"));
	        peopleService.savePersonPreferences(p3.getId(), generator.createActivity("Sport"));
        }
        
        // 1
        printer.printStep(1, "List all people in database");
        people = peopleService.readPersonList();
        Person firstPerson = people.get(0);
        printer.printPeople(people);
        
        Long firstPersonId = firstPerson.getId();
        
        // 2
        printer.printStep(2, "Read person with id -> readPerson("+firstPersonId+")");
        firstPerson = peopleService.readPerson(firstPersonId);
        printer.printPerson(firstPerson);
        
        // 3
        String new_firstname = "rnd_"+(int)(Math.random()*100);
        printer.printStep(3, "Changing firstname with "+new_firstname+" -> updatePerson("+firstPerson+")");
        firstPerson.setFirstname(new_firstname);
        Person updateFirstPerson = peopleService.updatePerson(firstPerson);
        printer.printPerson(updateFirstPerson);
        
        printer.print("Checking if update was good. -> readPerson("+firstPersonId+")");
        firstPerson = peopleService.readPerson(firstPersonId);
        printer.printPerson(firstPerson);
        
        // 4
        printer.printStep(4, "Create new person -> createPerson");
        Person personToCreate = generator.createPerson();
        Person createdPerson = peopleService.createPerson(personToCreate);
        printer.printPerson(createdPerson);
        
        Long createdPersonId = createdPerson.getId();
        
        printer.print("Checking if update was good: -> readPerson("+createdPersonId+")");
        Person p4 = peopleService.readPerson(createdPersonId);
        printer.printPerson(p4);
        
        // 5
        printer.printStep(5, "Delete person created -> deletePerson("+createdPersonId+")");
        boolean bool = peopleService.deletePerson(createdPersonId);
        System.out.println("response: "+bool);
        
        printer.print("Checking if update was good: -> readPerson("+createdPersonId+")");
        p4 = peopleService.readPerson(createdPersonId);
        printer.printPerson(p4);
        
        // 6
        printer.printStep(6, "Read all activity with activity_type='Sport' for Person id:"+firstPersonId+" -> readPersonPreferences("+firstPersonId+", \"Sport\")");
        List<Activity> activities = peopleService.readPersonPreferences(firstPersonId, "Sport");
        printer.printActivities(activities);
        
        // 7
        printer.printStep(7, "Read ALL Persons preferences -> readPreferences()");
        activities = peopleService.readPreferences();
        printer.printActivities(activities);
        
        // 9
        printer.printStep(9, "Create new activity with activity_type='Sport' for Person id:"+firstPersonId+" -> savePersonPreference("+firstPersonId+", Activity)");
        Activity newActivity = generator.createActivity("Sport");
        Activity createdActivity = peopleService.savePersonPreferences(firstPersonId, newActivity);
        printer.printActivity(createdActivity);
        
        Long createdActivityId = createdActivity.getId();
        
        // 8
        printer.printStep(8, "Read Person preference created -> readPersonPreference("+firstPersonId+", "+createdActivityId+")");
        Activity activity = peopleService.readPersonPreference(firstPersonId, createdActivityId);
        printer.printActivity(activity);
        
        // 10
        String activityNewName = "rnd_"+(int)(Math.random()*100);
        activity.setName(activityNewName);
        printer.printStep(10, "Update Person preference created with name \""+activityNewName+"\" -> updatePersonPreferences("+firstPersonId+", Activity)");
        activity = peopleService.updatePersonPreferences(firstPersonId, activity);
        printer.printActivity(activity);
        
        // 11
        int rate = (int)(Math.random()*4)+1;
        printer.printStep(11, "Evaluate Person preference created with rate "+rate+" -> evalutatePersonPreferences("+firstPersonId+", Activity, "+rate+")");
        activity = peopleService.evaluatePersonPreferences(firstPersonId, activity, rate);
        printer.printActivity(activity);
        
        printer.print("Read person preference id:"+createdActivityId+" to evalutate #10 and #11 step. Looking for changes");
        activity = peopleService.readPersonPreference(firstPersonId, createdActivityId);
        printer.printActivity(activity);
        
        // 12
        printer.printStep(12, "Get best preference of person -> getBestPersonPreference("+firstPersonId+")");
        activities = peopleService.getBestPersonPreference(firstPersonId);
        printer.printActivities(activities);
        
        // end
        printer.printStep(1, "List all people in database again to evaluate every step made");
        List<Person> people_final = peopleService.readPersonList();
        printer.printPeople(people_final);
        
    }
}