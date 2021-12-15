package ru.itis.kurguskina;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AutoCompleteTextField<S> extends TextField
{

    private final ObjectProperty<S> lastSelectedItem = new SimpleObjectProperty<>();

    private final SortedSet<S> entries;


    private ObservableList<S> filteredEntries
            = FXCollections.observableArrayList();

    private ContextMenu entriesPopup;


    private boolean caseSensitive = false;


    private boolean popupHidden = false;


    private String textOccurenceStyle = "-fx-font-weight: bold; "
            + "-fx-fill: rgb(66,139,202);";


    private int maxEntries = 10;


    public AutoCompleteTextField(SortedSet<S> entrySet)
    {
        super();
        this.entries = (entrySet == null ? new TreeSet<>() : entrySet);
        this.filteredEntries.addAll(entries);

        entriesPopup = new ContextMenu();

        textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) ->
        {

            if (getText() == null || getText().length() == 0)
            {
                filteredEntries.clear();
                filteredEntries.addAll(entries);
                entriesPopup.hide();
            } else
            {
                LinkedList<S> searchResult = new LinkedList<>();
                //Check if the entered Text is part of some entry
                String text1 = getText();
                Pattern pattern;
                if (isCaseSensitive())
                {
                    pattern = Pattern.compile(".*" + text1 + ".*");
                } else
                {
                    pattern = Pattern.compile(".*" + text1 + ".*", Pattern.CASE_INSENSITIVE);
                }
                for (S entry : entries)
                {
                    Matcher matcher = pattern.matcher(entry.toString());
                    if (matcher.matches())
                    {
                        searchResult.add(entry);
                    }
                }
                if (!entries.isEmpty())
                {
                    filteredEntries.clear();
                    filteredEntries.addAll(searchResult);
                    //Only show popup if not in filter mode
                    if (!isPopupHidden())
                    {
                        populatePopup(searchResult, text1);
                        if (!entriesPopup.isShowing())
                        {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    }
                } else
                {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) ->
        {
            entriesPopup.hide();
        });

    }


    public SortedSet<S> getEntries()
    {
        return entries;
    }


    private void populatePopup(List<S> searchResult, String text)
    {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int count = Math.min(searchResult.size(), getMaxEntries());
        for (int i = 0; i < count; i++)
        {
            final String result = searchResult.get(i).toString();
            final S itemObject = searchResult.get(i);
            int occurence;

            if (isCaseSensitive())
            {
                occurence = result.indexOf(text);
            } else
            {
                occurence = result.toLowerCase().indexOf(text.toLowerCase());
            }
            if (occurence < 0)
            {
                continue;
            }
            //Part before occurence (might be empty)
            Text pre = new Text(result.substring(0, occurence));
            //Part of (first) occurence
            Text in = new Text(result.substring(occurence, occurence + text.length()));
            in.setStyle(getTextOccurenceStyle());
            //Part after occurence
            Text post = new Text(result.substring(occurence + text.length(), result.length()));

            TextFlow entryFlow = new TextFlow(pre, in, post);

            CustomMenuItem item = new CustomMenuItem(entryFlow, true);
            item.setOnAction((ActionEvent actionEvent) ->
            {
                lastSelectedItem.set(itemObject);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    public S getLastSelectedObject()
    {
        return lastSelectedItem.get();
    }

    public ContextMenu getEntryMenu()
    {
        return entriesPopup;
    }

    public boolean isCaseSensitive()
    {
        return caseSensitive;
    }

    public String getTextOccurenceStyle()
    {
        return textOccurenceStyle;
    }

    public void setCaseSensitive(boolean caseSensitive)
    {
        this.caseSensitive = caseSensitive;
    }

    public void setTextOccurenceStyle(String textOccurenceStyle)
    {
        this.textOccurenceStyle = textOccurenceStyle;
    }

    public boolean isPopupHidden()
    {
        return popupHidden;
    }

    public void setPopupHidden(boolean popupHidden)
    {
        this.popupHidden = popupHidden;
    }

    public ObservableList<S> getFilteredEntries()
    {
        return filteredEntries;
    }

    public int getMaxEntries()
    {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries)
    {
        this.maxEntries = maxEntries;
    }

}
