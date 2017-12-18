package introsde.assignment3.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import introsde.assignment3.soap.Activity;
import introsde.assignment3.soap.Person;

public class PrettyPrinter {
	
	public PrettyPrinter(String file) throws FileNotFoundException {
		PrintStream fileStream = new PrintStream(file);
		System.setOut(fileStream);
	}
	
	public void printStep(int num, String description) {
		System.out.printf("\n\n\n## Method %s ## %s\n\n", num, description);
	}
	
	public void print(String description) {
		System.out.printf("\n%s\n\n", description);
	}
	
	public void printPeople(List<Person> people) {
		System.out.println("People (#"+people.size()+")");
		for(Person p : people) {
			printPerson(p);
		}
	}
	
	public void printPerson(Person p) {
		if(p == null) {
			System.out.println("\t Person is null");
			return;
		}
		System.out.println("\n\t Person");
		System.out.printf("\t %-20s%s%n", "id:", p.getId());
		System.out.printf("\t %-20s%s%n", "firstname:", p.getFirstname());
		System.out.printf("\t %-20s%s%n", "lastname:", p.getLastname());
		System.out.printf("\t %-20s%s%n", "username:", p.getUsername());
		System.out.printf("\t %-20s%s%n", "birthday:", p.getBirthdate());
		System.out.printf("\t %-20s%s%n", "email:", p.getEmail());
		this.printActivities(p.getActivities().getActivity());
		System.out.println();
    }
	
	public void printActivities(List<Activity> activities) {
		System.out.println("\t activities:");
		for(Activity activity : activities) {
			this.printActivity(activity);
		}
	}
	
	public void printActivity(Activity a) {
		if(a == null) {
			System.out.println("\t Activity is null");
			return;
		}
		System.out.println("\n\t\t Activity");
		System.out.printf("\t\t %-20s%s%n", "id:", a.getId());
		System.out.printf("\t\t %-20s%s%n", "name:", a.getName());
		System.out.printf("\t\t %-20s%s%n", "description:", a.getDescription());
		System.out.printf("\t\t %-20s%s%n", "place:", a.getPlace());
		System.out.printf("\t\t %-20s%s%n", "rate:", a.getRate());
		System.out.printf("\t\t %-20s%s%n", "startdate:", a.getStartDate());
		this.printActivityType(a.getActivityType());
	}
	
	public void printActivityType(String at) {
		System.out.printf("\t\t %-20s%s%n", "activityType", at);
	}
}
