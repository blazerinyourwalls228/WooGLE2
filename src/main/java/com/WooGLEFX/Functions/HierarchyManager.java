package com.WooGLEFX.Functions;

import com.WooGLEFX.EditorObjects.EditorAttribute;
import com.WooGLEFX.EditorObjects.EditorObject;
import com.WooGLEFX.EditorObjects.InputField;
import com.WooGLEFX.EditorObjects.ObjectCreators.ObjectAdder;
import com.WooGLEFX.EditorObjects.ObjectCreators.ObjectCreator;
import com.WooGLEFX.Engine.FX.FXPropertiesView;
import com.WooGLEFX.Engine.SelectionManager;
import com.WooGLEFX.File.FileManager;
import com.WooGLEFX.Functions.UndoHandling.UndoManager;
import com.WooGLEFX.Functions.UndoHandling.UserActions.HierarchyDragAction;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class HierarchyManager {

    public static Image getObjectIcon(EditorObject editorObject) {
        try {
            return FileManager.getIcon("ObjectIcons\\" + editorObject.getIcon() + ".png");
        } catch (FileNotFoundException e) {
            return null;
        }
    }


    private static EditorObject objectBeingDragged;
    public static EditorObject getObjectBeingDragged() {
        return objectBeingDragged;
    }


    private static int oldDropIndex;
    public static int getOldDropIndex() {
        return oldDropIndex;
    }


    public static void updateNameCell(TreeTableCell<EditorObject, String> cell, String item, boolean empty) {

        if (empty) {
            // If this is an empty cell, set its text and graphic to empty.
            // This prevents the cell from retaining other cells' information.
            cell.setText(null);
            cell.setGraphic(null);
        } else {
            // setTextFill(Paint.valueOf("FFFFFFFF"));
            // Update this cell's text.
            cell.setText(item);
            // Override the default padding that ruins the text.
            cell.setPadding(new Insets(0, 0, 0, 0));
        }

    }


    public static void updateElementCell(TreeTableCell<EditorObject, String> cell, String item, boolean empty) {

        if (empty) {
            // If this is an empty cell, set its text and graphic to empty.
            // This prevents the cell from retaining other cells' information.
            cell.setText(null);
            cell.setGraphic(null);
        } else {
            // Update this cell's text.
            cell.setText(item);
            // Override the default padding that ruins the text.
            cell.setPadding(new Insets(0, 0, 0, 0));

            if (cell.getTableRow().getItem() != null) {
                ImageView imageView;

                imageView = new ImageView(getObjectIcon(cell.getTableRow().getItem()));

                // If the cell's EditorObject is invalid, display its graphic with a warning
                // symbol.
                // Otherwise, just display its graphic.

                boolean valid = true;

                EditorObject editorObject = cell.getTableRow().getItem();

                for (EditorAttribute attribute : editorObject.getAttributes()) {
                    if (attribute.stringValue().isEmpty()) {
                        if (!InputField.verify(editorObject, attribute.getType(), attribute.getDefaultValue()) && attribute.getRequiredInFile()) return;
                    } else if (!InputField.verify(editorObject, attribute.getType(), attribute.actualValue())) {
                        valid = false;
                    }
                }

                if (!valid) {
                    ImageView failedImg = new ImageView(FileManager.getFailedImage());
                    cell.setGraphic(new StackPane(imageView, failedImg));
                } else {
                    cell.setGraphic(imageView);
                }
            }
        }

    }


    public static boolean handleDragDrop(TreeTableView<EditorObject> hierarchy, TreeTableRow<EditorObject> row) {

        if (row.isEmpty()) return false;

        int fromIndex = oldDropIndex;
        int toIndex = row.getIndex();

        int direction = (int)Math.signum(toIndex - fromIndex);
        if (direction == 0) return false;

        EditorObject toItem = hierarchy.getTreeItem(toIndex).getValue();
        EditorObject fromItem = hierarchy.getTreeItem(fromIndex).getValue();

        int indexOfToItem = toItem.getParent().getChildren().indexOf(toItem);
        int indexOfToTreeItem = toItem.getParent().getTreeItem().getChildren().indexOf(toItem.getTreeItem());

        fromItem.getParent().getChildren().remove(fromItem);
        fromItem.getParent().getTreeItem().getChildren().remove(fromItem.getTreeItem());

        fromItem.setParent(toItem.getParent(), indexOfToItem);

        hierarchy.getSelectionModel().select(indexOfToTreeItem);

        UndoManager.registerChange(new HierarchyDragAction(HierarchyManager.getObjectBeingDragged(), HierarchyManager.getOldDropIndex(), toIndex));
        UndoManager.clearRedoActions();

        return true;

    }


    public static TreeTableRow<EditorObject> createRow(TreeTableView<EditorObject> hierarchy) {

        final TreeTableRow<EditorObject> row = new TreeTableRow<>();

        row.setOnMousePressed(event -> {
            if (hierarchy.getTreeItem(row.getIndex()) != null) {
                SelectionManager.setSelected(hierarchy.getTreeItem(row.getIndex()).getValue());
                FXPropertiesView.changeTableView(hierarchy.getTreeItem(row.getIndex()).getValue());
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    row.setContextMenu(contextMenuForEditorObject(row.getTreeItem().getValue()));
                }
            }
        });

        row.setOnDragDetected(event -> {
            TreeItem<EditorObject> selected2 = hierarchy.getSelectionModel().getSelectedItem();
            if (selected2 != null) {
                Dragboard db = hierarchy.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(selected2.getValue().getClass().getName());
                db.setContent(content);
                objectBeingDragged = row.getItem();
                oldDropIndex = row.getIndex();
                event.consume();
            }
        });

        row.setOnDragExited(event -> {
            // row.setStyle("");
        });

        row.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
                // row.setStyle("-fx-font-size: 12pt, -fx-background-color: #D0F0FFFF");
            }
            event.consume();
        });

        row.setOnDragDropped(event -> {
            event.setDropCompleted(event.getDragboard().hasString() &&
                    HierarchyManager.handleDragDrop(hierarchy, row));
            event.consume();
        });

        return row;

    }


    public static ContextMenu contextMenuForEditorObject(EditorObject object) {

        // Create the content menu.
        ContextMenu menu = new ContextMenu();

        // For every object that can be created as a child of this object:
        for (String childToAdd : object.getPossibleChildren()) {

            // Create a menu item representing creating this child.
            MenuItem addItemItem = new MenuItem("Add " + childToAdd);

            // Attempt to set graphics for this menu item.
            addItemItem.setGraphic(new ImageView(HierarchyManager.getObjectIcon(ObjectCreator.create(childToAdd, null))));

            // Set the item's action to creating the child, with the object as its parent.
            addItemItem.setOnAction(event -> ObjectAdder.addObject(childToAdd, object));

            menu.getItems().add(addItemItem);
        }

        return menu;
    }

}
