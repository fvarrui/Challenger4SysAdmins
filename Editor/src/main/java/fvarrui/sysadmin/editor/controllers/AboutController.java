package fvarrui.sysadmin.editor.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

public class AboutController implements Initializable {
	
	private ResourceBundle bundle = ResourceBundle.getBundle("fvarrui.sysadmin.editor.application.config");
	
	// model
	
	private StringProperty title = new SimpleStringProperty(this, "title");
	private StringProperty version = new SimpleStringProperty(this, "version");
	private ObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<>(this, "releaseDate");
	private StringProperty javaVersion = new SimpleStringProperty(this, "javaVersion"); 
	private MapProperty<String, URL> authors = new SimpleMapProperty<>(this, "authors", FXCollections.observableHashMap()); 
	
	// view

    @FXML
    private Label titleLabel;

    @FXML
    private Label versionLabel;

    @FXML
    private Label releaseDateLabel;

    @FXML
    private Label javaVersionLabel;

    @FXML
    private VBox authorsPane;
	
	@FXML
	private GridPane view;
	
	public AboutController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/AboutView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		titleLabel.textProperty().bind(title);
		versionLabel.textProperty().bind(version);
		releaseDateLabel.textProperty().bindBidirectional(releaseDate, new LocalDateStringConverter(FormatStyle.FULL));
		javaVersionLabel.textProperty().bind(javaVersion);
		
		title.set(bundle.getString("app.title"));
		version.set(bundle.getString("app.version"));
		releaseDate.set(LocalDate.parse(bundle.getString("app.releaseDate")));
		javaVersion.set(System.getProperty("java.vendor") + " " + System.getProperty("java.version"));
		 
		String [] names = bundle.getString("app.authors").split(",");
		String [] links = bundle.getString("app.links").split(",");
		for (int i = 0; i < names.length; i++) {
			try {
				authors.put(names[i], new URL(links[i]));
			} catch (MalformedURLException e) {
				authors.put(names[i], null);
				e.printStackTrace();
			}
		}
		
		for (String name : authors.keySet()) {
			URL link = authors.get(name);  
			Hyperlink hyperlink = new Hyperlink(name);
			hyperlink.setOnAction(e -> {
				try {
					Desktop.getDesktop().browse(link.toURI());
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			});
			authorsPane.getChildren().add(hyperlink);
		}

	}
	
	public GridPane getView() {
		return view;
	}

}
