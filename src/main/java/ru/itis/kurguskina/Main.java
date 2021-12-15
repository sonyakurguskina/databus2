package ru.itis.kurguskina;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage)
    {

        AutoCompleteAddressField text = new AutoCompleteAddressField();

        TextField garagNumbField = new TextField();
        garagNumbField.setPromptText("GaragNumb");

        TextField azimuthField = new TextField();
        azimuthField.setPromptText("Azimuth");

        TextField graphField = new TextField();
        graphField.setPromptText("Graph");

        TextField latitudeField = new TextField();
        latitudeField.setPromptText("Latitude");

        TextField longitudeField = new TextField();
        longitudeField.setPromptText("Longitude");

        TextField marshField = new TextField();
        marshField.setPromptText("Marsh");

        TextField smenaField = new TextField();
        smenaField.setPromptText("Smena");

        TextField speedField = new TextField();
        speedField.setPromptText("Speed");

        TextField timeNavField = new TextField();
        timeNavField.setPromptText("TimeNav");


        Button clearButton = new Button("Clear");

        clearButton.setOnAction(e ->
        {
            text.clear();
            text.getEntries().clear();
            garagNumbField.clear();
            azimuthField.clear();
            graphField.clear();
            latitudeField.clear();
            longitudeField.clear();
            marshField.clear();
            smenaField.clear();
            speedField.clear();
            timeNavField.clear();

        });

        text.getEntryMenu().setOnAction((ActionEvent e) ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, (Event event) ->
            {
                if (text.getLastSelectedObject() != null)
                {
                    text.setText(text.getLastSelectedObject().toString());
                    PlaceDetails place = AutoCompleteAddressField.getPlace((AddressPrediction) text.getLastSelectedObject());
                    if (place != null)
                    {
                        garagNumbField.setText(
                                StringUtils.join(
                                        AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.GARAG_NUMB),
                                        " ",
                                        AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.ROUTE))
                        );

                        azimuthField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.AZIMUTH));
                        graphField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.GRAPH));
                        latitudeField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.LATITUDE));
                        longitudeField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.LONGITUDE));
                        marshField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.MARSH));
                        smenaField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.SMENA));
                        speedField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.SPEED));
                        timeNavField.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.TIME_NAV));
                    } else
                    {
                        garagNumbField.clear();
                        azimuthField.clear();
                        graphField.clear();
                        latitudeField.clear();
                        longitudeField.clear();
                        marshField.clear();
                        smenaField.clear();
                        speedField.clear();
                        timeNavField.clear();

                    }
                }
            });
        });

        VBox root = new VBox();
        root.getChildren().addAll(text, new Label(), garagNumbField, azimuthField, graphField, latitudeField, longitudeField, marshField, smenaField, speedField,
        timeNavField, clearButton);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Bus API");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

}