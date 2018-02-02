package fvarrui.sysadmin.challenger;

import java.io.File;
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

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
@XmlType
public class ChallengeFX {

	private StringProperty name;

	private StringProperty description;

	private ListProperty<GoalFX> goals;

	public ChallengeFX() {
		this(null);
	}

	public ChallengeFX(String name) {
		this.name = new SimpleStringProperty(this, "name", name);
		this.description = new SimpleStringProperty(this, "description");
		this.goals = new SimpleListProperty<>(this, "goals", FXCollections.observableArrayList());
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	@XmlID
	@XmlAttribute	
	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	@XmlElement
	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public final ListProperty<GoalFX> goalsProperty() {
		return this.goals;
	}

	@XmlElement(name = "goal")
	@XmlElementWrapper(name = "goals")
	public final ObservableList<GoalFX> getGoals() {
		return this.goalsProperty().get();
	}

	public final void setGoals(final ObservableList<GoalFX> goals) {
		this.goalsProperty().set(goals);
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

	public static ChallengeFX load(File file) throws Exception {
		JAXBContext context = JAXBContext.newInstance(ChallengeFX.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (ChallengeFX) unmarshaller.unmarshal(file);
	}
}
