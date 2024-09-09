package com.woogleFX.editorObjects;

import com.woogleFX.engine.SelectionManager;
import com.woogleFX.engine.fx.AssetTab;
import com.woogleFX.engine.fx.hierarchy.FXHierarchy;
import com.woogleFX.engine.undoHandling.userActions.UserAction;
import com.woogleFX.gameData.level.GameVersion;
import com.woogleFX.gameData.level.VisibilitySettings;

import java.util.Arrays;
import java.util.Stack;

public abstract class Asset {

    public final Stack<UserAction[]> redoActions = new Stack<>();
    public final Stack<UserAction[]> undoActions = new Stack<>();


    private double offsetX = 0;
    public double getOffsetX() {
        return offsetX;
    }
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }


    private double offsetY = 0;
    public double getOffsetY() {
        return offsetY;
    }
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }


    private double zoom = 1;
    public double getZoom() {
        return zoom;
    }
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }


    private final VisibilitySettings visibilitySettings = new VisibilitySettings();
    public VisibilitySettings getVisibilitySettings() {
        return visibilitySettings;
    }


    private String levelName;
    public String getLevelName() {
        return levelName;
    }
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }


    private int editingStatus;
    public int getEditingStatus() {
        return editingStatus;
    }
    public void setEditingStatus(int editingStatus, boolean shouldSelect) {
        this.editingStatus = editingStatus;
        getAssetTab().update(editingStatus, shouldSelect);
    }


    private EditorObject[] selected = new EditorObject[]{};
    public EditorObject[] getSelected() {
        return selected;
    }
    public void setSelected(EditorObject[] selected) {
        this.selected = selected;
        SelectionManager.goToSelectedInHierarchy();
    }
    public void clearSelection() {
        selected = new EditorObject[]{};
        FXHierarchy.getHierarchy().getSelectionModel().clearSelection();
    }
    public boolean isSelected(EditorObject EditorObject) {
        return Arrays.stream(selected).anyMatch(e -> e == EditorObject);
    }


    private int lastSavedUndoPosition = 0;
    public int getLastSavedUndoPosition() {
        return lastSavedUndoPosition;
    }
    public void setLastSavedUndoPosition(int position) {
        this.lastSavedUndoPosition = position;
    }

    private AssetTab assetTab;
    public AssetTab getAssetTab() {
        return assetTab;
    }
    public void setAssetTab(AssetTab assetTab) {
        this.assetTab = assetTab;
    }


    private final GameVersion version;
    public GameVersion getVersion() {
        return version;
    }

    public Asset(GameVersion version) {
        this.version = version;
    }


    public abstract void resetCamera();

}
