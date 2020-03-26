package tsp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tsp_narzedzia.Narzedzia;

public class TSP extends Application{
	
	Stage window;
	Scene scene1;
	Scene scene2;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		window = arg0;
		VBox layout = new VBox();
		
		GridPane layout1 = new GridPane();
		layout1.setAlignment(Pos.CENTER);
		layout1.setPadding(new Insets(20,20,20,20));
		layout1.setVgap(8);
		layout1.setHgap(6);
		
		Label etykieta_rp = new Label("Rozmiar populacji: ");
		TextField rozmiarPopulacji = new TextField();
		rozmiarPopulacji.setMaxWidth(80);
		rozmiarPopulacji.setText("100");
		layout1.setConstraints(etykieta_rp, 0, 0);
		layout1.setConstraints(rozmiarPopulacji, 1, 0);
		
		Label etykieta_lg = new Label("Liczba generacji: ");
		TextField liczbaGeneracji = new TextField();
		liczbaGeneracji.setMaxWidth(80);
		liczbaGeneracji.setText("500");
		layout1.setConstraints(etykieta_lg, 0, 1);
		layout1.setConstraints(liczbaGeneracji, 1, 1);
		
		Label etykieta_px = new Label("Prawdopodobienstwo krzyzowania: ");
		TextField pX = new TextField();
		pX.setMaxWidth(80);
		pX.setText("0.7");
		layout1.setConstraints(etykieta_px, 0, 2);
		layout1.setConstraints(pX, 1, 2);
		
		Label etykieta_pm = new Label("Prawdopodobienstwo mutacji: ");
		TextField pM = new TextField();
		pM.setMaxWidth(80);
		pM.setText("0.1");
		layout1.setConstraints(etykieta_pm, 0, 3);
		layout1.setConstraints(pM, 1, 3);
		
		Label etykieta_nt = new Label("Rozmiar turnieju: ");
		TextField nTurnieju = new TextField();
		nTurnieju.setMaxWidth(80);
		nTurnieju.setText("5");
		layout1.setConstraints(etykieta_nt, 0, 4);
		layout1.setConstraints(nTurnieju, 1, 4);
		
		Label etykieta_plik = new Label("Plik: ");
		ChoiceBox<String> wyborPliku = new ChoiceBox();
		wyborPliku.setMaxWidth(80);
		wyborPliku.getItems().addAll("berlin11_modified", "berlin52", "kroA100", "kroA150", "kroA200", "fl417", "ali535", "gr666");
		wyborPliku.setValue("berlin52");
		layout1.setConstraints(etykieta_plik, 0, 5);
		layout1.setConstraints(wyborPliku, 1, 5);
		
		Button przyciskParametry = new Button("Rozpocznij");
		przyciskParametry.setOnAction(e -> {
			przycisk1(rozmiarPopulacji.getText(), liczbaGeneracji.getText(), pX.getText(), pM.getText(), nTurnieju.getText(), wyborPliku.getValue());
		});
		
		layout1.getChildren().addAll(etykieta_rp, rozmiarPopulacji, etykieta_lg, liczbaGeneracji, etykieta_px, pX, etykieta_pm, pM, etykieta_nt, nTurnieju, etykieta_plik, wyborPliku);
		layout.getChildren().addAll(layout1, przyciskParametry);
		layout.setAlignment(Pos.CENTER);
		scene1 = new Scene(layout, 500, 400);
		
		window.setScene(scene1);
		window.setTitle("Algorytm Genetyczny");
		window.show();
	}
	
	private void przycisk1(String rP, String lG, String prX, String prM, String n, String p) {
		try
	    {
	      AlgorytmGenetyczny algGen = new AlgorytmGenetyczny(Integer.parseInt(rP), Integer.parseInt(lG), Double.parseDouble(prX), Double.parseDouble(prM), Integer.parseInt(n), p, new SelekcjaTurniej(Integer.parseInt(n)), new KrzyzowanieOX(), new MutacjaInwersja());
	      algGen.wykonaj();
	      stworzSceneWyniki(algGen.getWyniki(), algGen.getNajlepszyDystans(), rP, lG, prX, prM, n, p);
	    }
	    catch (NumberFormatException nfe)
	    {
	      System.out.println("NumberFormatException: " + nfe.getMessage());
	    }
		
	}
	
	private void stworzSceneWyniki(ArrayList<Wynik> wyniki, double naj, String rP, String lG, String prX, String prM, String n, String p) {
		
		window.setTitle("Wykres (" + p + ")");
		VBox layout = new VBox(10);
		
		NumberAxis osX = new NumberAxis();
		osX.setLabel("Nr generacji");
        NumberAxis osY = new NumberAxis();
        osY.setLabel("Dystans");
		LineChart<Number,Number> wykres = new LineChart<Number,Number>(osX,osY);
		wykres.setTitle("Wykres dzialania EA (" + rP + "x" + lG + "; " + prX + ", " + prM + ", " + n + ")");
		
		XYChart.Series seria_najlepsze = new XYChart.Series();
		seria_najlepsze.setName("najlepsze");
        
        XYChart.Series seria_srednie = new XYChart.Series();
        seria_srednie.setName("srednie");
        
        XYChart.Series seria_najgorsze = new XYChart.Series();
        seria_najgorsze.setName("najgorsze");
        
        for(int i=0; i<wyniki.size(); i++) {
        	seria_najlepsze.getData().add(new XYChart.Data(i, wyniki.get(i).getNajlepszy()));
        	seria_srednie.getData().add(new XYChart.Data(i, wyniki.get(i).getSredni()));
        	seria_najgorsze.getData().add(new XYChart.Data(i, wyniki.get(i).getNajgorszy()));
        }
		
		wykres.getData().add(seria_najlepsze);
		wykres.getData().add(seria_srednie);
		wykres.getData().add(seria_najgorsze);
		
		wykres.setCreateSymbols(false);
		
		Label etykieta_naj = new Label("Najlepszy dystans: " + naj);
		etykieta_naj.setStyle("-fx-font-size: 1.5em; -fx-font-weight:bold;");
		
		Button przycisk_cofnij = new Button("Powrot");
		przycisk_cofnij.setOnAction(e -> window.setScene(scene1));
		
		Button przycisk_zapisz_png = new Button("Zapisz PNG");
		
		TextInputDialog dialog = new TextInputDialog("wykres1");
		dialog.setTitle("Zapisywanie");
		dialog.setHeaderText("Zapisywanie wykresu");
		dialog.setContentText("Podaj nazwe pliku:");
		
		przycisk_zapisz_png.setOnAction(e ->{
			Optional<String> wejscie = dialog.showAndWait();
			wejscie.ifPresent(nazwa -> {
				WritableImage png = wykres.snapshot(new SnapshotParameters(), null);
				File zapis = new File(nazwa + ".png");
				try {
			        ImageIO.write(SwingFXUtils.fromFXImage(png, null), "png", zapis);
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }
			});
		});
		
		Button przycisk_zapisz_excel = new Button("Zapisz EXCEL");
		
		TextInputDialog dialog_excel1 = new TextInputDialog("excel1");
		dialog_excel1.setTitle("Zapisywanie");
		dialog_excel1.setHeaderText("Zapisywanie w EXCEL");
		dialog_excel1.setContentText("Podaj nazwe pliku:");
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Czy chcesz dopisaæ do pliku?");
		alert.setTitle("Nadpis/Dopis");
		
		ButtonType okButton = new ButtonType("Tak", ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType("Nie", ButtonBar.ButtonData.NO);
		
		alert.getButtonTypes().setAll(okButton, noButton);
		
		przycisk_zapisz_excel.setOnAction(e ->{
			alert.showAndWait().ifPresent(type -> {
		        if (type == okButton) {
		        	Optional<String> wejscie = dialog_excel1.showAndWait();
					wejscie.ifPresent(nazwa -> {
						Narzedzia.zapiszExcelCSV(wyniki, nazwa, true, p, rP, lG, prX, prM, n);
					});
		        } else if (type == noButton) {
		        	Optional<String> wejscie = dialog_excel1.showAndWait();
					wejscie.ifPresent(nazwa -> {
						Narzedzia.zapiszExcelCSV(wyniki, nazwa, false, p, rP, lG, prX, prM, n);
					});
		        }
			});
		});
		
		layout.getChildren().addAll(przycisk_cofnij, wykres, etykieta_naj, przycisk_zapisz_png, przycisk_zapisz_excel);
		layout.setAlignment(Pos.CENTER);
		
		scene2 = new Scene(layout, 800, 600);
		
		window.setScene(scene2);
	}
}
