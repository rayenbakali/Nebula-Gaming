/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.views;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.text.Document;
import tn.nebulagaming.models.JeuVideo;
import tn.nebulagaming.models.Commentaire;
import tn.nebulagaming.models.Publication;
import org.controlsfx.control.Notifications;
import tn.nebulagaming.services.ServiceCommentaire;
import tn.nebulagaming.services.ServiceJeuVideo;
import tn.nebulagaming.services.ServicePublication;
import tn.nebulagaming.utils.GlobalConfig;
import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.cell.TextFieldTableCell;
import jxl.write.WritableCell;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AfficherPubController implements Initializable {

    private Statement ste;
    private Connection con;

    @FXML
    private TextField descriptiontf;
    @FXML
    private ComboBox<String> ComboJV;
    ObservableList<String> listnom = FXCollections.observableArrayList();
    @FXML
    private TableView<Publication> tablePublication;
    @FXML
    private TableColumn<Publication, String> Descriptiont;

    @FXML
    private Button btnajouterP;
    @FXML
    private TableColumn<Publication, Integer> idP;
    @FXML
    private TableColumn<Publication, Date> Date;

    private final ObservableList<Publication> data = FXCollections.observableArrayList();
    private final ObservableList<Commentaire> dataC = FXCollections.observableArrayList();
    @FXML
    private TableView<Commentaire> TableCommentaire;
    @FXML
    private TableColumn<Commentaire, Date> DateCom;
    @FXML
    private TableColumn<Commentaire, String> DescriptionC;
    @FXML
    private Button btncommenter;
    @FXML
    private TextField descriptiontfC;
    @FXML
    private Button ModifPub;
    @FXML
    private TextField recherchePub;
    @FXML
    private TableColumn<Commentaire, Integer> IDcom;
    @FXML
    private Button supprimerPub;
    @FXML
    private Button btnLIKE;

    ServicePublication sp = new ServicePublication();
    @FXML
    private TableColumn<Publication, Float> like;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	AfficherPub();
	rechercherPub();
	try {
	    // TODO
	    fillcombo();
	} catch (SQLException ex) {
	    Logger.getLogger(AfficherPubController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void fillcombo() throws SQLException {

	ServiceJeuVideo ser = new ServiceJeuVideo();
	List<JeuVideo> list = ser.afficher();
	for (JeuVideo aux : list) {
	    listnom.add(aux.getNameVg());
	}
	ComboJV.setItems(listnom);
    }

    @FXML
    private void Ajouter(ActionEvent event) {

	if (descriptiontf.getText().isEmpty()) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setContentText("Verifiez les champs!");
	    alert.show();
	} else {
	    ServiceJeuVideo ser = new ServiceJeuVideo();

	    JeuVideo j = ser.getByName(ComboJV.getValue());
	    //System.out.println(j.getId());

	    Publication p = new Publication(j.getId(), descriptiontf.getText());

	    sp.ajouter(p);
	    AfficherPub();
	    rechercherPub();

	    Notifications notificationBuilder = Notifications.create()
		    .title("NOTIFICATION FORUM").text("NOUVELLE PUBLICATION AJOUTéE")
		    .graphic(null).hideAfter(javafx.util.Duration.seconds(5))
		    .position(Pos.BASELINE_RIGHT)
		    .onAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
			    System.out.println("clicked ON");
			}
		    }
		    );
	    notificationBuilder.darkStyle();
	    notificationBuilder.show();

	}
    }

    private void AfficherPub() {

	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    String req = "SELECT * FROM tbl_publication ";
	    PreparedStatement stat = con.prepareStatement(req);
	    ResultSet rs = stat.executeQuery();
	    while (rs.next()) {
		data.add(new Publication(rs.getInt(1), rs.getDate(3), rs.getString(5), rs.getFloat(7)));
	    }
	    // System.out.println(data);

	} catch (SQLException ex) {
	}

	idP.setCellValueFactory(new PropertyValueFactory<>("idPub"));
	Date.setCellValueFactory(new PropertyValueFactory<>("DatePub"));
	Descriptiont.setCellValueFactory(new PropertyValueFactory<>("descriptionPub"));
	like.setCellValueFactory(new PropertyValueFactory<>("nbrjaime"));
	tablePublication.setItems(data);

    }

    @FXML
    private void AjouterCom(ActionEvent event) {

	if (descriptiontfC.getText().isEmpty()) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setContentText("Verifiez les champs!");
	    alert.show();
	} else {
	    tablePublication.setItems(data);

	    ObservableList<Publication> allpub, Singlepub;
	    allpub = tablePublication.getItems();
	    Singlepub = tablePublication.getSelectionModel().getSelectedItems();
	    Publication A = Singlepub.get(0);

	    System.out.println(A.getIdPub());

	    Commentaire c = new Commentaire(descriptiontfC.getText(), A.getIdPub());

	    ServiceCommentaire sc = new ServiceCommentaire();
	    sc.ajouter(c);

	    Notifications notificationBuilder = Notifications.create()
		    .title("NOTIFICATION FORUM").text("NOUVELLE COMMENTAIRE AJOUTé")
		    .graphic(null).hideAfter(javafx.util.Duration.seconds(5))
		    .position(Pos.BASELINE_RIGHT)
		    .onAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
			    System.out.println("clicked ON");
			}
		    }
		    );
	    notificationBuilder.darkStyle();
	    notificationBuilder.show();
	}
    }

    @FXML
    private void AffCommentaires(MouseEvent event) {

	tablePublication.setItems(data);

	ObservableList<Publication> allpub, Singlepub;
	allpub = tablePublication.getItems();
	Singlepub = tablePublication.getSelectionModel().getSelectedItems();
	Publication A = Singlepub.get(0);

	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    String req = "SELECT * FROM tbl_Commentaire where idpub=" + A.getIdPub();
	    PreparedStatement stat = con.prepareStatement(req);
	    ResultSet rs = stat.executeQuery();
	    TableCommentaire.getItems().clear();

	    while (rs.next()) {
		dataC.add(new Commentaire(rs.getInt(1), rs.getDate(2), rs.getString(3)));

	    }
	    System.out.println(data);

	} catch (SQLException ex) {
	}

	DateCom.setCellValueFactory(new PropertyValueFactory<>("dateCom"));
	DescriptionC.setCellValueFactory(new PropertyValueFactory<>("descriptionCom"));
	IDcom.setCellValueFactory(new PropertyValueFactory<>("idCom"));
	TableCommentaire.setItems(dataC);
	TableCommentaire.setEditable(true);
	DescriptionC.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    @FXML
    private void PressedGetDesc(KeyEvent event) {

	TablePosition tablePosition = tablePublication.getSelectionModel().getSelectedCells().get(0);
	int row = tablePosition.getRow();
	Publication item = tablePublication.getItems().get(row);
	TableColumn tableColumn = tablePosition.getTableColumn();
	String data = (String) tableColumn.getCellObservableValue(item).getValue();
	System.out.println(data);

	descriptiontf.setText(data);
    }

    @FXML
    private void ModifierPub(ActionEvent event) throws SQLException {
	if (descriptiontf.getText().isEmpty()) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setContentText("Verifiez les champs!");
	    alert.show();
	} else {
	    String ref = descriptiontf.getText();
	    Publication PU = new Publication(descriptiontf.getText());

	    sp.modifier2(PU, ref);

	    AfficherPub();
	    rechercherPub();
	}

    }

    private void rechercherPub() {

	// Wrap the ObservableList in a FilteredList (initially display all data).
	FilteredList<Publication> filteredData = new FilteredList<>(data, b -> true);

	// 2. Set the filter Predicate whenever the filter changes.
	recherchePub.textProperty().addListener((observable, oldValue, newValue) -> {
	    filteredData.setPredicate(evenement -> {
		// If filter text is empty, display all persons.

		if (newValue == null || newValue.isEmpty()) {
		    return true;
		}

		// Compare first name and last name of every person with filter text.
		String lowerCaseFilter = newValue.toLowerCase();

		if (evenement.getDescriptionPub().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		    return true; // Filter matches first name.
		} else {
		    return false; // Does not match.
		}
	    });
	});

	// 3. Wrap the FilteredList in a SortedList. 
	SortedList<Publication> sortedData = new SortedList<>(filteredData);

	// 4. Bind the SortedList comparator to the TableView comparator.
	// 	  Otherwise, sorting the TableView would have no effect.
	sortedData.comparatorProperty().bind(tablePublication.comparatorProperty());

	// 5. Add sorted (and filtered) data to the table.
	tablePublication.setItems(sortedData);
    }

    @FXML
    private void ChangeCom(TableColumn.CellEditEvent event) {

	ServiceCommentaire SC = new ServiceCommentaire();

	Commentaire tab_comSelected = TableCommentaire.getSelectionModel().getSelectedItem();
	tab_comSelected.setDescriptionCom(event.getNewValue().toString());
	SC.modifier(tab_comSelected);
    }

    @FXML
    private void supprimerPub(ActionEvent event) {

	tablePublication.setItems(data);

	ObservableList<Publication> alldomains, Singledomain;
	alldomains = tablePublication.getItems();
	Singledomain = tablePublication.getSelectionModel().getSelectedItems();
	Publication A = Singledomain.get(0);
	sp.supprimer(A);
	Singledomain.forEach(alldomains::remove);
	AfficherPub();
    }

    @FXML
    private void JaimePub(ActionEvent event) throws SQLException {

	tablePublication.setItems(data);

	ObservableList<Publication> allJV, SingleJV;
	allJV = tablePublication.getItems();
	SingleJV = tablePublication.getSelectionModel().getSelectedItems();
	Publication A = SingleJV.get(0);

	A.setLike(A.getLike() + 1);

	sp.modifier2(A, descriptiontf.getText());
	AfficherPub();
	rechercherPub();

    }

}
