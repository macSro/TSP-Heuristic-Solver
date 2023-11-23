package tsp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.chart.XYChart.Series;
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
import utils.Utils;

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
		
		Label lGenerations = new Label("Generations: ");
		TextField tfGenerations = new TextField();
		tfGenerations.setMaxWidth(100);
		tfGenerations.setText("500");
		GridPane.setConstraints(lGenerations, 0, 0);
		GridPane.setConstraints(tfGenerations, 1, 0);
		
		Label lPopulationSize = new Label("Population size: ");
		TextField tfPopulationSize = new TextField();
		tfPopulationSize.setMaxWidth(100);
		tfPopulationSize.setText("100");
		GridPane.setConstraints(lPopulationSize, 0, 1);
		GridPane.setConstraints(tfPopulationSize, 1, 1);
		
		Label lSelection = new Label("Selection: ");
		ChoiceBox<String> choiceSelection = new ChoiceBox<>();
		choiceSelection.setMaxWidth(100);
		choiceSelection.getItems().addAll("Tournament", "Roulette");
		choiceSelection.setValue("Tournament");
		GridPane.setConstraints(lSelection, 0, 2);
		GridPane.setConstraints(choiceSelection, 1, 2);
		
		Label lTournamentSize = new Label("Tournament size (ignored when Roulette): ");
		TextField tfTournamentSize = new TextField();
		tfTournamentSize.setMaxWidth(100);
		tfTournamentSize.setText("5");
		GridPane.setConstraints(lTournamentSize, 0, 3);
		GridPane.setConstraints(tfTournamentSize, 1, 3);
		
		Label lMutation = new Label("Mutation: ");
		ChoiceBox<String> choiceMutation = new ChoiceBox<>();
		choiceMutation.setMaxWidth(100);
		choiceMutation.getItems().addAll("Inversion", "Swap");
		choiceMutation.setValue("Inversion");
		GridPane.setConstraints(lMutation, 0, 4);
		GridPane.setConstraints(choiceMutation, 1, 4);
		
		Label lProbabilityCrossover = new Label("Crossover probability: ");
		TextField tfProbabilityCrossover = new TextField();
		tfProbabilityCrossover.setMaxWidth(100);
		tfProbabilityCrossover.setText("0.7");
		GridPane.setConstraints(lProbabilityCrossover, 0, 5);
		GridPane.setConstraints(tfProbabilityCrossover, 1, 5);
		
		Label lProbabilityMutation = new Label("Mutation probability: ");
		TextField tfProbabilityMutation = new TextField();
		tfProbabilityMutation.setMaxWidth(100);
		tfProbabilityMutation.setText("0.1");
		GridPane.setConstraints(lProbabilityMutation, 0, 6);
		GridPane.setConstraints(tfProbabilityMutation, 1, 6);
		
		Label lInputFileName = new Label("Input file: ");
		ChoiceBox<String> choiceInputFileName = new ChoiceBox<>();
		choiceInputFileName.setMaxWidth(100);
		choiceInputFileName.getItems().addAll("berlin11_modified", "berlin52", "kroA100", "kroA150", "kroA200", "fl417", "ali535", "gr666");
		choiceInputFileName.setValue("berlin52");
		GridPane.setConstraints(lInputFileName, 0, 7);
		GridPane.setConstraints(choiceInputFileName, 1, 7);
		
		Button bRun = new Button("Run");
		bRun.setOnAction(e -> {
			run(tfGenerations.getText(), tfPopulationSize.getText(), choiceSelection.getValue(), tfTournamentSize.getText(), choiceMutation.getValue(), tfProbabilityCrossover.getText(), tfProbabilityMutation.getText(), choiceInputFileName.getValue());
		});
		
		layout1.getChildren().addAll(lGenerations, tfGenerations, lPopulationSize, tfPopulationSize, lSelection, choiceSelection, lTournamentSize, tfTournamentSize, lMutation, choiceMutation, lProbabilityCrossover, tfProbabilityCrossover, lProbabilityMutation, tfProbabilityMutation, lInputFileName, choiceInputFileName);
		layout.getChildren().addAll(layout1, bRun);
		layout.setAlignment(Pos.CENTER);
		scene1 = new Scene(layout, 500, 400);
		
		window.setScene(scene1);
		window.setTitle("Traveling Salesman Problem Solver");
		window.show();
	}
	
	private void run(String generations, String populationSize, String selection, String tournamentSize, String mutation, String probabilityCrossover, String probabilityMutation, String inputDataFileName) {
		try {
			GeneticAlgorithm algorithm = new GeneticAlgorithm(
				Integer.parseInt(generations),
				Integer.parseInt(populationSize),
				selection.equals("Tournament") ? new SelectionTournament(Integer.parseInt(tournamentSize)) : new SelectionRoulette(),
				new CrossoverOx(),
				mutation.equals("Inversion") ? new MutationInversion() : new MutationSwap(),
				Double.parseDouble(probabilityCrossover),
				Double.parseDouble(probabilityMutation),
				inputDataFileName
			);
			algorithm.execute();
			showResults(algorithm.getResults(), algorithm.getBestDistance(), generations, populationSize, probabilityCrossover, probabilityMutation, tournamentSize, inputDataFileName);
	    }
	    catch (NumberFormatException nfe)
	    {
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Wrong input!");
            alert.setContentText("Please enter correctly formatted numbers. Remeber to use '.' as the decimal point.");
            alert.showAndWait();
	    }
		
	}
	
	private void showResults(ArrayList<Result> results, double best, String generations, String populationSize, String probabilityCrossover, String probabilityMutation, String tournamentSize, String inputDataFileName) {
		
		window.setTitle("Results (" + inputDataFileName + ")");
		VBox layout = new VBox(10);
		
		NumberAxis axisX = new NumberAxis();
		axisX.setLabel("Generation");
        NumberAxis axisY = new NumberAxis();
        axisY.setLabel("Distance");
		LineChart<Number,Number> chart = new LineChart<Number,Number>(axisX,axisY);
		chart.setTitle("Calculated distances (" + generations + "x" + populationSize + "; " + probabilityCrossover + ", " + probabilityMutation);
		
		XYChart.Series<Number, Number> seriesWorst = new XYChart.Series<>();
        seriesWorst.setName("Worst");
		
        XYChart.Series<Number, Number> seriesAverage = new XYChart.Series<>();
        seriesAverage.setName("Average");
        
        Series<Number, Number> seriesBest = new XYChart.Series<>();
		seriesBest.setName("Best");
        
        for(int i=0; i<results.size(); i++) {
        	seriesWorst.getData().add(new XYChart.Data<Number, Number>(i, results.get(i).getWorst()));
        	seriesAverage.getData().add(new XYChart.Data<Number, Number>(i, results.get(i).getAverage()));
        	seriesBest.getData().add(new XYChart.Data<Number, Number>(i, results.get(i).getBest()));
        }
		
		chart.getData().add(seriesWorst);
		chart.getData().add(seriesAverage);
		chart.getData().add(seriesBest);
		
		chart.setCreateSymbols(false);
		
		Label lBest = new Label("Best distance: " + best);
		lBest.setStyle("-fx-font-size: 1.5em; -fx-font-weight:bold;");
		
		Button bBack = new Button("Back");
		bBack.setOnAction(e -> window.setScene(scene1));
		
		Button bSavePng = new Button("Save chart as image");
		
		TextInputDialog dialog1 = new TextInputDialog("chart1");
		dialog1.setTitle("Save chart as image");
		dialog1.setHeaderText("Saving the image");
		dialog1.setContentText("Enter file name:");
		
		bSavePng.setOnAction(e ->{
			dialog1.showAndWait().ifPresent(fileName -> {
				WritableImage png = chart.snapshot(new SnapshotParameters(), null);
				File file = new File("results/" + fileName + ".png");
				try {
			        ImageIO.write(SwingFXUtils.fromFXImage(png, null), "png", file);
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }
			});
		});
		
		Button bSaveCsv = new Button("Save result data");
		
		TextInputDialog dialog2 = new TextInputDialog("result1");
		dialog2.setTitle("Save result data");
		dialog2.setHeaderText("Saving result data");
		dialog2.setContentText("Enter file name:");
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Append");
		alert.setHeaderText("Do you want to append to the file?");
		
		ButtonType bYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType bNo = new ButtonType("No", ButtonBar.ButtonData.NO);
		
		alert.getButtonTypes().setAll(bYes, bNo);
		
		bSaveCsv.setOnAction(e ->{
			alert.showAndWait().ifPresent(type -> {
		        if (type == bYes) {
					dialog2.showAndWait().ifPresent(fileName -> {
						Utils.saveCsv(results, fileName, true, inputDataFileName, generations, populationSize, probabilityCrossover, probabilityMutation, tournamentSize);
					});
		        } else if (type == bNo) {
		        	dialog2.showAndWait().ifPresent(fileName -> {
						Utils.saveCsv(results, fileName, false, inputDataFileName, generations, populationSize, probabilityCrossover, probabilityMutation, tournamentSize);
					});
		        }
			});
		});
		
		layout.getChildren().addAll(bBack, chart, lBest, bSavePng, bSaveCsv);
		layout.setAlignment(Pos.CENTER);
		
		scene2 = new Scene(layout, 800, 600);
		
		window.setScene(scene2);
	}
}
