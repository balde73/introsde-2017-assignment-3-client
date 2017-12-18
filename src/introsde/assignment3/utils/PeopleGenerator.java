package introsde.assignment3.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import introsde.assignment3.soap.Activity;
import introsde.assignment3.soap.Person;

public class PeopleGenerator {
	Random randnum;
	Long activityID;
	
	public PeopleGenerator() {
		randnum = new Random();
        randnum.setSeed(123456789);
        
        this.activityID = (long) (Math.random() * 10000) + 1000;
	}
	
	public Person createPerson() {
		Person p = new Person();
		p.setLastname(this.randomName());
		p.setFirstname(this.randomName());
		p.setUsername("le_"+p.getFirstname()+randnum.nextInt(1000));
		p.setEmail(p.getLastname()+p.getFirstname()+"@mail.com");
		p.setActivities(new Person.Activities());
		p.setBirthdate(this.randomDate(1993));
		return p;
	}
	
	public Activity createActivity(String activityType) {
		String[] places = {"Rome", "Milan", "New York", "Paris"};
		Activity a = new Activity();
		int num = randnum.nextInt(1000);
		a.setId(this.activityID);
		a.setName("activity name #"+num);
		a.setDescription("activity description #"+num);
		a.setActivityType(activityType);
		a.setPlace(places[randnum.nextInt(4)]);
		a.setStartDate(this.randomDate(2017));
		
		this.activityID++;
		return a;
	}
	
	private String randomName() {
		String[] pref = {"g", "m", "l", "f", "s"};
		String[] names = {"ao", "ia", "iova", "ian", "ari", "i", "ar", "iovan"};
		String[] suff = {"lo", "ro", "ni", "no", "na", "sa"};
		
		return 	pref[randnum.nextInt(pref.length-1)]
				+ names[randnum.nextInt(names.length-1)]
				+ suff[randnum.nextInt(suff.length-1)];
	}
	
	private XMLGregorianCalendar randomDate(int year) {
		GregorianCalendar c = new GregorianCalendar();
		c.set(c.YEAR, year);
		c.set(c.DAY_OF_YEAR, randnum.nextInt(360)+1);
		XMLGregorianCalendar date;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			date = null;
		}
		return date;
	}
	
}
