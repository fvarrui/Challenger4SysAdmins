package fvarrui.sysadmin.challenger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class Challenge {

	private String name;

	private String description;

	private List<Goal> goals;

	public Challenge() {
		this(null);
	}
	
	public Challenge(String name) {
		this.name = name;
		this.goals = new ArrayList<>();
	}

	@XmlID
	@XmlAttribute	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "goal")
	@XmlElementWrapper(name = "goals")
	public List<Goal> getGoals() {
		return goals;
	}

	public boolean isAchieved() {
		return goals.stream().allMatch(g -> g.isAchieved());
	}

	public void start() throws InterruptedException {
		System.out.println("Iniciando reto '" + getName() + "'");
		while (!isAchieved()) {
			System.out.println(this);
			Thread.sleep(2000L);
		}
		System.out.println("Reto alcanzado");
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "--- Comprobando objetivos:\n" + goals.stream().map(g -> g.toString()).collect(Collectors.joining("\n"));
	}

	public void save(File file) throws Exception {
		JAXBContext context = JAXBContext.newInstance(getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(this, file);
	}
	
	public static Challenge load(File file) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Challenge.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (Challenge) unmarshaller.unmarshal(file);
	}
	
}
