/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2011 Andreas Maschke

  This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser 
  General Public License as published by the Free Software Foundation; either version 2.1 of the 
  License, or (at your option) any later version.
 
  This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this software; 
  if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jwildfire.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.jwildfire.base.Prefs;
import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.Flame;
import org.jwildfire.create.tina.base.RandomFlameGenerator;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.io.Flam3Reader;
import org.jwildfire.create.tina.io.Flam3Writer;
import org.jwildfire.create.tina.palette.RGBPalette;
import org.jwildfire.create.tina.palette.RGBPaletteImporter;
import org.jwildfire.create.tina.palette.RGBPaletteRenderer;
import org.jwildfire.create.tina.palette.RandomRGBPaletteGenerator;
import org.jwildfire.create.tina.render.FlameRenderer;
import org.jwildfire.image.SimpleImage;
import org.jwildfire.io.ImageReader;
import org.jwildfire.io.ImageWriter;

public class TINAController {
  private static final double SLIDER_SCALE_PERSPECTIVE = 100.0;
  private static final double SLIDER_SCALE_CENTRE = 5.0;
  private static final double SLIDER_SCALE_ZOOM = 10.0;
  private static final double SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY = 100.0;
  private static final double SLIDER_SCALE_GAMMA = 100.0;
  private static final double SLIDER_SCALE_FILTER_RADIUS = 100.0;
  private static final double SLIDER_SCALE_GAMMA_TRESHOLD = 1000.0;

  private final JPanel centerPanel;
  private ImagePanel flamePanel;

  private final Prefs prefs;
  private final ErrorHandler errorHandler;

  // camera, coloring
  private final JTextField cameraRollREd;
  private final JSlider cameraRollSlider;
  private final JTextField cameraPitchREd;
  private final JSlider cameraPitchSlider;
  private final JTextField cameraYawREd;
  private final JSlider cameraYawSlider;
  private final JTextField cameraPerspectiveREd;
  private final JSlider cameraPerspectiveSlider;
  private final JTextField previewQualityREd;
  private final JTextField renderQualityREd;
  private final JTextField cameraCentreXREd;
  private final JSlider cameraCentreXSlider;
  private final JTextField cameraCentreYREd;
  private final JSlider cameraCentreYSlider;
  private final JTextField cameraZoomREd;
  private final JSlider cameraZoomSlider;
  private final JTextField pixelsPerUnitREd;
  private final JSlider pixelsPerUnitSlider;
  private final JTextField brightnessREd;
  private final JSlider brightnessSlider;
  private final JTextField contrastREd;
  private final JSlider contrastSlider;
  private final JTextField gammaREd;
  private final JSlider gammaSlider;
  private final JTextField vibrancyREd;
  private final JSlider vibrancySlider;
  private final JTextField filterRadiusREd;
  private final JSlider filterRadiusSlider;
  private final JTextField oversampleREd;
  private final JSlider oversampleSlider;
  private final JTextField gammaThresholdREd;
  private final JSlider gammaThresholdSlider;
  private final JTextField whiteLevelREd;
  private final JSlider whiteLevelSlider;
  private final JTextField bgColorRedREd;
  private final JSlider bgColorRedSlider;
  private final JTextField bgColorGreenREd;
  private final JSlider bgColorGreenSlider;
  private final JTextField bgColorBlueREd;
  private final JSlider bgColorBlueSlider;
  private final JTextField renderWidthREd;
  private final JTextField renderHeightREd;
  // palette -> create
  private final JTextField paletteRandomPointsREd;
  private final JPanel paletteImgPanel;
  private ImagePanel palettePanel;
  // palette -> transform
  private final JTextField paletteShiftREd;
  private final JSlider paletteShiftSlider;
  private final JTextField paletteRedREd;
  private final JSlider paletteRedSlider;
  private final JTextField paletteGreenREd;
  private final JSlider paletteGreenSlider;
  private final JTextField paletteBlueREd;
  private final JSlider paletteBlueSlider;
  private final JTextField paletteHueREd;
  private final JSlider paletteHueSlider;
  private final JTextField paletteSaturationREd;
  private final JSlider paletteSaturationSlider;
  private final JTextField paletteContrastREd;
  private final JSlider paletteContrastSlider;
  private final JTextField paletteGammaREd;
  private final JSlider paletteGammaSlider;
  private final JTextField paletteBrightnessREd;
  private final JSlider paletteBrightnessSlider;
  // Transformations
  private final JTable transformationsTable;
  private final JTextField affineC00REd;
  private final JTextField affineC01REd;
  private final JTextField affineC10REd;
  private final JTextField affineC11REd;
  private final JTextField affineC20REd;
  private final JTextField affineC21REd;
  private final JTextField affineRotateAmountREd;
  private final JTextField affineScaleAmountREd;
  private final JTextField affineMoveAmountREd;
  private final JButton affineRotateLeftButton;
  private final JButton affineRotateRightButton;
  private final JButton affineEnlargeButton;
  private final JButton affineShrinkButton;
  private final JButton affineMoveUpButton;
  private final JButton affineMoveLeftButton;
  private final JButton affineMoveRightButton;
  private final JButton affineMoveDownButton;
  private final JButton addTransformationButton;
  private final JButton duplicateTransformationButton;
  private final JButton deleteTransformationButton;
  private final JButton addFinalTransformationButton;

  // misc
  private Flame currFlame;
  private boolean noRefresh;

  public TINAController(ErrorHandler pErrorHandler, Prefs pPrefs, JPanel pCenterPanel, JTextField pCameraRollREd, JSlider pCameraRollSlider, JTextField pCameraPitchREd,
      JSlider pCameraPitchSlider, JTextField pCameraYawREd, JSlider pCameraYawSlider, JTextField pCameraPerspectiveREd, JSlider pCameraPerspectiveSlider,
      JTextField pPreviewQualityREd, JTextField pRenderQualityREd, JTextField pCameraCentreXREd, JSlider pCameraCentreXSlider, JTextField pCameraCentreYREd,
      JSlider pCameraCentreYSlider, JTextField pCameraZoomREd, JSlider pCameraZoomSlider, JTextField pPixelsPerUnitREd, JSlider pPixelsPerUnitSlider,
      JTextField pBrightnessREd, JSlider pBrightnessSlider, JTextField pContrastREd, JSlider pContrastSlider, JTextField pGammaREd, JSlider pGammaSlider,
      JTextField pVibrancyREd, JSlider pVibrancySlider, JTextField pFilterRadiusREd, JSlider pFilterRadiusSlider, JTextField pOversampleREd,
      JSlider pOversampleSlider, JTextField pGammaThresholdREd, JSlider pGammaThresholdSlider, JTextField pWhiteLevelREd, JSlider pWhiteLevelSlider,
      JTextField pBGColorRedREd, JSlider pBGColorRedSlider, JTextField pBGColorGreenREd, JSlider pBGColorGreenSlider, JTextField pBGColorBlueREd,
      JSlider pBGColorBlueSlider, JTextField pPaletteRandomPointsREd, JPanel pPaletteImgPanel, JTextField pPaletteShiftREd, JSlider pPaletteShiftSlider,
      JTextField pPaletteRedREd, JSlider pPaletteRedSlider, JTextField pPaletteGreenREd, JSlider pPaletteGreenSlider, JTextField pPaletteBlueREd,
      JSlider pPaletteBlueSlider, JTextField pPaletteHueREd, JSlider pPaletteHueSlider, JTextField pPaletteSaturationREd, JSlider pPaletteSaturationSlider,
      JTextField pPaletteContrastREd, JSlider pPaletteContrastSlider, JTextField pPaletteGammaREd, JSlider pPaletteGammaSlider, JTextField pPaletteBrightnessREd,
      JSlider pPaletteBrightnessSlider, JTextField pRenderWidthREd, JTextField pRenderHeightREd, JTable pTransformationsTable, JTextField pAffineC00REd,
      JTextField pAffineC01REd, JTextField pAffineC10REd, JTextField pAffineC11REd, JTextField pAffineC20REd, JTextField pAffineC21REd,
      JTextField pAffineRotateAmountREd, JTextField pAffineScaleAmountREd, JTextField pAffineMoveAmountREd, JButton pAffineRotateLeftButton,
      JButton pAffineRotateRightButton, JButton pAffineEnlargeButton, JButton pAffineShrinkButton, JButton pAffineMoveUpButton, JButton pAffineMoveLeftButton,
      JButton pAffineMoveRightButton, JButton pAffineMoveDownButton, JButton pAddTransformationButton, JButton pDuplicateTransformationButton,
      JButton pDeleteTransformationButton, JButton pAddFinalTransformationButton) {
    errorHandler = pErrorHandler;
    prefs = pPrefs;
    centerPanel = pCenterPanel;

    cameraRollREd = pCameraRollREd;
    cameraRollSlider = pCameraRollSlider;
    cameraPitchREd = pCameraPitchREd;
    cameraPitchSlider = pCameraPitchSlider;
    cameraYawREd = pCameraYawREd;
    cameraYawSlider = pCameraYawSlider;
    cameraPerspectiveREd = pCameraPerspectiveREd;
    cameraPerspectiveSlider = pCameraPerspectiveSlider;
    previewQualityREd = pPreviewQualityREd;
    renderQualityREd = pRenderQualityREd;
    cameraCentreXREd = pCameraCentreXREd;
    cameraCentreXSlider = pCameraCentreXSlider;
    cameraCentreYREd = pCameraCentreYREd;
    cameraCentreYSlider = pCameraCentreYSlider;
    cameraZoomREd = pCameraZoomREd;
    cameraZoomSlider = pCameraZoomSlider;
    pixelsPerUnitREd = pPixelsPerUnitREd;
    pixelsPerUnitSlider = pPixelsPerUnitSlider;
    brightnessREd = pBrightnessREd;
    brightnessSlider = pBrightnessSlider;
    contrastREd = pContrastREd;
    contrastSlider = pContrastSlider;
    gammaREd = pGammaREd;
    gammaSlider = pGammaSlider;
    vibrancyREd = pVibrancyREd;
    vibrancySlider = pVibrancySlider;
    filterRadiusREd = pFilterRadiusREd;
    filterRadiusSlider = pFilterRadiusSlider;
    oversampleREd = pOversampleREd;
    oversampleSlider = pOversampleSlider;
    gammaThresholdREd = pGammaThresholdREd;
    gammaThresholdSlider = pGammaThresholdSlider;
    whiteLevelREd = pWhiteLevelREd;
    whiteLevelSlider = pWhiteLevelSlider;
    bgColorRedREd = pBGColorRedREd;
    bgColorRedSlider = pBGColorRedSlider;
    bgColorGreenREd = pBGColorGreenREd;
    bgColorGreenSlider = pBGColorGreenSlider;
    bgColorBlueREd = pBGColorBlueREd;
    bgColorBlueSlider = pBGColorBlueSlider;
    paletteRandomPointsREd = pPaletteRandomPointsREd;
    paletteImgPanel = pPaletteImgPanel;
    renderWidthREd = pRenderWidthREd;
    renderHeightREd = pRenderHeightREd;

    paletteShiftREd = pPaletteShiftREd;
    paletteShiftSlider = pPaletteShiftSlider;
    paletteRedREd = pPaletteRedREd;
    paletteRedSlider = pPaletteRedSlider;
    paletteGreenREd = pPaletteGreenREd;
    paletteGreenSlider = pPaletteGreenSlider;
    paletteBlueREd = pPaletteBlueREd;
    paletteBlueSlider = pPaletteBlueSlider;
    paletteHueREd = pPaletteHueREd;
    paletteHueSlider = pPaletteHueSlider;
    paletteSaturationREd = pPaletteSaturationREd;
    paletteSaturationSlider = pPaletteSaturationSlider;
    paletteContrastREd = pPaletteContrastREd;
    paletteContrastSlider = pPaletteContrastSlider;
    paletteGammaREd = pPaletteGammaREd;
    paletteGammaSlider = pPaletteGammaSlider;
    paletteBrightnessREd = pPaletteBrightnessREd;
    paletteBrightnessSlider = pPaletteBrightnessSlider;

    transformationsTable = pTransformationsTable;
    affineC00REd = pAffineC00REd;
    affineC01REd = pAffineC01REd;
    affineC10REd = pAffineC10REd;
    affineC11REd = pAffineC11REd;
    affineC20REd = pAffineC20REd;
    affineC21REd = pAffineC21REd;
    affineRotateAmountREd = pAffineRotateAmountREd;
    affineScaleAmountREd = pAffineScaleAmountREd;
    affineMoveAmountREd = pAffineMoveAmountREd;
    affineRotateLeftButton = pAffineRotateLeftButton;
    affineRotateRightButton = pAffineRotateRightButton;
    affineEnlargeButton = pAffineEnlargeButton;
    affineShrinkButton = pAffineShrinkButton;
    affineMoveUpButton = pAffineMoveUpButton;
    affineMoveLeftButton = pAffineMoveLeftButton;
    affineMoveRightButton = pAffineMoveRightButton;
    affineMoveDownButton = pAffineMoveDownButton;
    addTransformationButton = pAddTransformationButton;
    duplicateTransformationButton = pDuplicateTransformationButton;
    deleteTransformationButton = pDeleteTransformationButton;
    addFinalTransformationButton = pAddFinalTransformationButton;
    enableControls(null);
  }

  private ImagePanel getFlamePanel() {
    if (flamePanel == null) {
      int width = centerPanel.getWidth();
      int height = centerPanel.getHeight();
      SimpleImage img = new SimpleImage(width, height);
      img.fillBackground(0, 0, 0);
      flamePanel = new ImagePanel(img, 0, 0, centerPanel.getWidth());
      centerPanel.add(flamePanel, BorderLayout.CENTER);
      centerPanel.repaint();
    }
    return flamePanel;
  }

  private ImagePanel getPalettePanel() {
    if (palettePanel == null) {
      int width = paletteImgPanel.getWidth();
      int height = paletteImgPanel.getHeight();
      SimpleImage img = new SimpleImage(width, height);
      img.fillBackground(0, 0, 0);
      palettePanel = new ImagePanel(img, 0, 0, paletteImgPanel.getWidth());
      paletteImgPanel.add(palettePanel, BorderLayout.CENTER);
      paletteImgPanel.repaint();
    }
    return palettePanel;
  }

  public void refreshFlameImage() {
    refreshFlameImage(Integer.parseInt(previewQualityREd.getText()));
  }

  public void refreshFlameImage(int pQuality) {
    ImagePanel imgPanel = getFlamePanel();
    int width = imgPanel.getWidth();
    int height = imgPanel.getHeight();
    if (width >= 16 && height >= 16) {
      SimpleImage img = new SimpleImage(width, height);
      if (currFlame != null) {
        Flame flame = currFlame;
        double wScl = (double) img.getImageWidth() / (double) flame.getWidth();
        double hScl = (double) img.getImageHeight() / (double) flame.getHeight();
        flame.setPixelsPerUnit((wScl + hScl) * 0.5 * flame.getPixelsPerUnit());
        flame.setWidth(img.getImageWidth());
        flame.setHeight(img.getImageHeight());
        flame.setSampleDensity(pQuality);

        FlameRenderer renderer = new FlameRenderer();
        renderer.renderFlame(flame, img);
      }
      imgPanel.setImage(img);
    }
    centerPanel.repaint();
  }

  private void refreshUI() {
    noRefresh = true;
    try {
      cameraRollREd.setText(Tools.doubleToString(currFlame.getCamRoll()));
      cameraRollSlider.setValue(Tools.FTOI(currFlame.getCamRoll()));

      cameraPitchREd.setText(Tools.doubleToString(currFlame.getCamPitch()));
      cameraPitchSlider.setValue(Tools.FTOI(currFlame.getCamPitch()));

      cameraYawREd.setText(Tools.doubleToString(currFlame.getCamYaw()));
      cameraYawSlider.setValue(Tools.FTOI(currFlame.getCamYaw()));

      cameraPerspectiveREd.setText(Tools.doubleToString(currFlame.getCamPerspective()));
      cameraPerspectiveSlider.setValue(Tools.FTOI(currFlame.getCamPerspective() * SLIDER_SCALE_PERSPECTIVE));

      cameraCentreXREd.setText(Tools.doubleToString(currFlame.getCentreX()));
      cameraCentreXSlider.setValue(Tools.FTOI(currFlame.getCentreX() * SLIDER_SCALE_CENTRE));

      cameraCentreYREd.setText(Tools.doubleToString(currFlame.getCentreY()));
      cameraCentreYSlider.setValue(Tools.FTOI(currFlame.getCentreY() * SLIDER_SCALE_CENTRE));

      cameraZoomREd.setText(Tools.doubleToString(currFlame.getCamZoom()));
      cameraZoomSlider.setValue(Tools.FTOI(currFlame.getCamZoom() * SLIDER_SCALE_ZOOM));

      cameraZoomREd.setText(Tools.doubleToString(currFlame.getCamZoom()));
      cameraZoomSlider.setValue(Tools.FTOI(currFlame.getCamZoom() * SLIDER_SCALE_ZOOM));

      pixelsPerUnitREd.setText(Tools.doubleToString(currFlame.getPixelsPerUnit()));
      pixelsPerUnitSlider.setValue(Tools.FTOI(currFlame.getPixelsPerUnit()));

      brightnessREd.setText(Tools.doubleToString(currFlame.getBrightness()));
      brightnessSlider.setValue(Tools.FTOI(currFlame.getBrightness() * SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY));

      contrastREd.setText(Tools.doubleToString(currFlame.getContrast()));
      contrastSlider.setValue(Tools.FTOI(currFlame.getContrast() * SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY));

      vibrancyREd.setText(Tools.doubleToString(currFlame.getVibrancy()));
      vibrancySlider.setValue(Tools.FTOI(currFlame.getVibrancy() * SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY));

      gammaREd.setText(Tools.doubleToString(currFlame.getGamma()));
      gammaSlider.setValue(Tools.FTOI(currFlame.getGamma() * SLIDER_SCALE_GAMMA));

      filterRadiusREd.setText(Tools.doubleToString(currFlame.getSpatialFilterRadius()));
      filterRadiusSlider.setValue(Tools.FTOI(currFlame.getSpatialFilterRadius() * SLIDER_SCALE_FILTER_RADIUS));

      gammaThresholdREd.setText(Tools.doubleToString(currFlame.getGammaThreshold()));
      gammaThresholdSlider.setValue(Tools.FTOI(currFlame.getGammaThreshold() * SLIDER_SCALE_GAMMA_TRESHOLD));

      oversampleREd.setText(String.valueOf(currFlame.getSpatialOversample()));
      oversampleSlider.setValue(currFlame.getSpatialOversample());

      whiteLevelREd.setText(String.valueOf(currFlame.getWhiteLevel()));
      whiteLevelSlider.setValue(currFlame.getWhiteLevel());

      bgColorRedREd.setText(String.valueOf(currFlame.getBGColorRed()));
      bgColorRedSlider.setValue(currFlame.getBGColorRed());

      bgColorGreenREd.setText(String.valueOf(currFlame.getBGColorGreen()));
      bgColorGreenSlider.setValue(currFlame.getBGColorGreen());

      bgColorBlueREd.setText(String.valueOf(currFlame.getBGColorBlue()));
      bgColorBlueSlider.setValue(currFlame.getBGColorBlue());

      refreshTransformationsTable();
      transformationTableClicked();

      refreshFlameImage();

      refreshPaletteUI(currFlame.getPalette());
    }
    finally {
      noRefresh = false;
    }
  }

  private void refreshTransformationsTable() {
    final int COL_TRANSFORM = 0;
    final int COL_VARIATIONS = 1;
    transformationsTable.setModel(new DefaultTableModel() {
      private static final long serialVersionUID = 1L;

      @Override
      public int getRowCount() {
        return currFlame != null ? currFlame.getXForms().size() + (currFlame.getFinalXForm() != null ? 1 : 0) : 0;
      }

      @Override
      public int getColumnCount() {
        return 2;
      }

      @Override
      public String getColumnName(int columnIndex) {
        switch (columnIndex) {
          case COL_TRANSFORM:
            return "Transform";
          case COL_VARIATIONS:
            return "Variations";
        }
        return null;
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
        if (currFlame != null) {
          XForm xForm = rowIndex < currFlame.getXForms().size() ? currFlame.getXForms().get(rowIndex) : currFlame.getFinalXForm();
          switch (columnIndex) {
            case COL_TRANSFORM:
              return rowIndex < currFlame.getXForms().size() ? String.valueOf(rowIndex + 1) : "Final";
            case COL_VARIATIONS:
              {
              String hs = "";
              for (int i = 0; i < xForm.getVariations().size() - 1; i++) {
                hs += xForm.getVariations().get(i).getFunc().getName() + ", ";
              }
              hs += xForm.getVariations().get(xForm.getVariations().size() - 1).getFunc().getName();
              return hs;
            }
          }
        }
        return null;
      }

    });

    transformationsTable.getColumnModel().getColumn(COL_TRANSFORM).setPreferredWidth(20);
  }

  private void refreshPaletteUI(RGBPalette pPalette) {
    paletteRedREd.setText(String.valueOf(pPalette.getModRed()));
    paletteRedSlider.setValue(pPalette.getModRed());
    paletteGreenREd.setText(String.valueOf(pPalette.getModGreen()));
    paletteGreenSlider.setValue(pPalette.getModGreen());
    paletteBlueREd.setText(String.valueOf(pPalette.getModBlue()));
    paletteBlueSlider.setValue(pPalette.getModBlue());
    paletteContrastREd.setText(String.valueOf(pPalette.getModContrast()));
    paletteContrastSlider.setValue(pPalette.getModContrast());
    paletteHueREd.setText(String.valueOf(pPalette.getModHue()));
    paletteHueSlider.setValue(pPalette.getModHue());
    paletteBrightnessREd.setText(String.valueOf(pPalette.getModBrightness()));
    paletteBrightnessSlider.setValue(pPalette.getModBrightness());
    paletteGammaREd.setText(String.valueOf(pPalette.getModGamma()));
    paletteGammaSlider.setValue(pPalette.getModGamma());
    paletteShiftREd.setText(String.valueOf(pPalette.getModShift()));
    paletteShiftSlider.setValue(pPalette.getModShift());
    paletteSaturationREd.setText(String.valueOf(pPalette.getModSaturation()));
    paletteSaturationSlider.setValue(pPalette.getModSaturation());
    refreshPaletteImg();
  }

  private void refreshPaletteImg() {
    if (currFlame != null) {
      ImagePanel imgPanel = getPalettePanel();
      int width = imgPanel.getWidth();
      int height = imgPanel.getHeight();
      if (width >= 16 && height >= 16) {
        SimpleImage img = new RGBPaletteRenderer().renderHorizPalette(currFlame.getPalette(), width, height);
        imgPanel.setImage(img);
      }
      palettePanel.repaint();
    }
  }

  private void flameSliderChanged(JSlider pSlider, JTextField pTextField, String pProperty, double pSliderScale) {
    if (noRefresh || currFlame == null)
      return;
    noRefresh = true;
    try {
      double propValue = pSlider.getValue() / pSliderScale;
      pTextField.setText(Tools.doubleToString(propValue));
      Class<?> cls = currFlame.getClass();
      Field field;
      try {
        field = cls.getDeclaredField(pProperty);
        field.setAccessible(true);
        Class<?> fieldCls = field.getType();
        if (fieldCls == double.class || fieldCls == Double.class) {
          field.setDouble(currFlame, propValue);
        }
        else if (fieldCls == int.class || fieldCls == Integer.class) {
          field.setInt(currFlame, Tools.FTOI(propValue));
        }
        else {
          throw new IllegalStateException();
        }
      }
      catch (Throwable ex) {
        ex.printStackTrace();
      }
      refreshFlameImage();
    }
    finally {
      noRefresh = false;
    }
  }

  private void paletteSliderChanged(JSlider pSlider, JTextField pTextField, String pProperty, double pSliderScale) {
    if (noRefresh || currFlame == null)
      return;
    noRefresh = true;
    try {
      double propValue = pSlider.getValue() / pSliderScale;
      pTextField.setText(Tools.doubleToString(propValue));
      Class<?> cls = currFlame.getPalette().getClass();
      Field field;
      try {
        field = cls.getDeclaredField(pProperty);
        field.setAccessible(true);
        Class<?> fieldCls = field.getType();
        if (fieldCls == double.class || fieldCls == Double.class) {
          field.setDouble(currFlame.getPalette(), propValue);
        }
        else if (fieldCls == int.class || fieldCls == Integer.class) {
          field.setInt(currFlame.getPalette(), Tools.FTOI(propValue));
        }
        else {
          throw new IllegalStateException();
        }
        field = cls.getDeclaredField("modified");
        field.setAccessible(true);
        field.setBoolean(currFlame.getPalette(), true);
      }
      catch (Throwable ex) {
        ex.printStackTrace();
      }
      refreshPaletteImg();
      refreshFlameImage();
    }
    finally {
      noRefresh = false;
    }
  }

  private void flameTextFieldChanged(JSlider pSlider, JTextField pTextField, String pProperty, double pSliderScale) {
    if (noRefresh || currFlame == null)
      return;
    noRefresh = true;
    try {
      double propValue = Tools.stringToDouble(pTextField.getText());
      pSlider.setValue(Tools.FTOI(propValue * pSliderScale));

      Class<?> cls = currFlame.getClass();
      Field field;
      try {
        field = cls.getDeclaredField(pProperty);
        field.setAccessible(true);
        Class<?> fieldCls = field.getType();
        if (fieldCls == double.class || fieldCls == Double.class) {
          field.setDouble(currFlame, propValue);
        }
        else if (fieldCls == int.class || fieldCls == Integer.class) {
          field.setInt(currFlame, Tools.FTOI(propValue));
        }
        else {
          throw new IllegalStateException();
        }
      }
      catch (Throwable ex) {
        ex.printStackTrace();
      }
      refreshFlameImage();
    }
    finally {
      noRefresh = false;
    }
  }

  private void paletteTextFieldChanged(JSlider pSlider, JTextField pTextField, String pProperty, double pSliderScale) {
    if (noRefresh || currFlame == null)
      return;
    noRefresh = true;
    try {
      double propValue = Tools.stringToDouble(pTextField.getText());
      pSlider.setValue(Tools.FTOI(propValue * pSliderScale));

      Class<?> cls = currFlame.getPalette().getClass();
      Field field;
      try {
        field = cls.getDeclaredField(pProperty);
        field.setAccessible(true);
        Class<?> fieldCls = field.getType();
        if (fieldCls == double.class || fieldCls == Double.class) {
          field.setDouble(currFlame.getPalette(), propValue);
        }
        else if (fieldCls == int.class || fieldCls == Integer.class) {
          field.setInt(currFlame.getPalette(), Tools.FTOI(propValue));
        }
        else {
          throw new IllegalStateException();
        }
        field = cls.getDeclaredField("modified");
        field.setAccessible(true);
        field.setBoolean(currFlame.getPalette(), true);
      }
      catch (Throwable ex) {
        ex.printStackTrace();
      }
      refreshPaletteImg();
      refreshFlameImage();
    }
    finally {
      noRefresh = false;
    }
  }

  public void cameraRollSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraRollSlider, cameraRollREd, "camRoll", 1.0);
  }

  public void cameraRollREd_changed() {
    flameTextFieldChanged(cameraRollSlider, cameraRollREd, "camRoll", 1.0);
  }

  public void cameraPitchREd_changed() {
    flameTextFieldChanged(cameraPitchSlider, cameraPitchREd, "camPitch", 1.0);
  }

  public void cameraPitchSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraPitchSlider, cameraPitchREd, "camPitch", 1.0);
  }

  public void cameraYawREd_changed() {
    flameTextFieldChanged(cameraYawSlider, cameraYawREd, "camYaw", 1.0);
  }

  public void cameraPerspectiveREd_changed() {
    flameTextFieldChanged(cameraPerspectiveSlider, cameraPerspectiveREd, "camPerspective", SLIDER_SCALE_PERSPECTIVE);
  }

  public void cameraYawSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraYawSlider, cameraYawREd, "camYaw", 1.0);
  }

  public void cameraPerspectiveSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraPerspectiveSlider, cameraPerspectiveREd, "camPerspective", SLIDER_SCALE_PERSPECTIVE);
  }

  public void renderFlameButton_actionPerformed(ActionEvent e) {
    refreshFlameImage(Integer.parseInt(renderQualityREd.getText()));
  }

  public class FlameFileFilter extends FileFilter {

    @Override
    public boolean accept(File pFile) {
      if (pFile.isDirectory())
        return true;
      String extension = getExtension(pFile);
      return (extension != null)
          && (extension.equals("flame") || extension.equals("xml"));
    }

    @Override
    public String getDescription() {
      return "Supported flame files";
    }

    private String getExtension(File pFile) {
      String name = pFile.getName();
      int idx = name.lastIndexOf('.');
      if (idx > 0 && idx < name.length() - 1) {
        return name.substring(idx + 1).toLowerCase();
      }
      return null;
    }

  }

  private JFileChooser getFlameJFileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.addChoosableFileFilter(new FlameFileFilter());
    fileChooser.setAcceptAllFileFilterUsed(false);
    return fileChooser;
  }

  public void loadFlameButton_actionPerformed(ActionEvent e) {
    JFileChooser chooser = getFlameJFileChooser();
    try {
      chooser.setCurrentDirectory(new File(prefs.getInputFlamePath()));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    if (chooser.showOpenDialog(centerPanel) == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      List<Flame> flames = new Flam3Reader().readFlames(file.getAbsolutePath());
      Flame flame = flames.get(0);
      prefs.setLastInputFlameFile(file);
      currFlame = flame;
      refreshUI();
    }
  }

  public void previewQualityREd_changed() {
    if (noRefresh)
      return;
    refreshFlameImage();
  }

  public void cameraCentreYSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraCentreYSlider, cameraCentreYREd, "centreY", SLIDER_SCALE_CENTRE);
  }

  public void cameraCentreYREd_changed() {
    flameTextFieldChanged(cameraCentreYSlider, cameraCentreYREd, "centreY", SLIDER_SCALE_CENTRE);
  }

  public void cameraCentreXSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraCentreXSlider, cameraCentreXREd, "centreX", SLIDER_SCALE_CENTRE);
  }

  public void cameraZoomSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(cameraZoomSlider, cameraZoomREd, "camZoom", SLIDER_SCALE_ZOOM);
  }

  public void cameraCentreXREd_changed() {
    flameTextFieldChanged(cameraCentreXSlider, cameraCentreXREd, "centreX", SLIDER_SCALE_CENTRE);
  }

  public void cameraZoomREd_changed() {
    flameTextFieldChanged(cameraZoomSlider, cameraZoomREd, "camZoom", SLIDER_SCALE_ZOOM);
  }

  public void brightnessSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(brightnessSlider, brightnessREd, "brightness", SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY);
  }

  public void filterRadiusREd_changed() {
    flameTextFieldChanged(filterRadiusSlider, filterRadiusREd, "spatialFilterRadius", SLIDER_SCALE_FILTER_RADIUS);
  }

  public void bgColorGreenSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(bgColorGreenSlider, bgColorGreenREd, "bgColorGreen", 1.0);
  }

  public void gammaREd_changed() {
    flameTextFieldChanged(gammaSlider, gammaREd, "gamma", SLIDER_SCALE_GAMMA);
  }

  public void gammaThresholdREd_changed() {
    flameTextFieldChanged(gammaThresholdSlider, gammaThresholdREd, "gammaThreshold", SLIDER_SCALE_GAMMA_TRESHOLD);
  }

  public void bgColorRedSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(bgColorRedSlider, bgColorRedREd, "bgColorRed", 1.0);
  }

  public void bgColorBlueSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(bgColorBlueSlider, bgColorBlueREd, "bgColorBlue", 1.0);
  }

  public void oversampleREd_changed() {
    flameTextFieldChanged(oversampleSlider, oversampleREd, "spatialOversample", 1.0);
  }

  public void contrastREd_changed() {
    flameTextFieldChanged(contrastSlider, contrastREd, "contrast", SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY);
  }

  public void vibrancySlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(vibrancySlider, vibrancyREd, "vibrancy", SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY);
  }

  public void filterRadiusSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(filterRadiusSlider, filterRadiusREd, "spatialFilterRadius", SLIDER_SCALE_FILTER_RADIUS);
  }

  public void oversampleSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(oversampleSlider, oversampleREd, "spatialOversample", 1.0);
  }

  public void gammaThresholdSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(gammaThresholdSlider, gammaThresholdREd, "gammaThreshold", SLIDER_SCALE_GAMMA_TRESHOLD);
  }

  public void whiteLevelSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(whiteLevelSlider, whiteLevelREd, "whiteLevel", 1.0);
  }

  public void vibrancyREd_changed() {
    flameTextFieldChanged(vibrancySlider, vibrancyREd, "vibrancy", SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY);
  }

  public void pixelsPerUnitSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(pixelsPerUnitSlider, pixelsPerUnitREd, "pixelsPerUnit", 1.0);
  }

  public void bgColorGreenREd_changed() {
    flameTextFieldChanged(bgColorGreenSlider, bgColorGreenREd, "bgColorGreen", 1.0);
  }

  public void gammaSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(gammaSlider, gammaREd, "gamma", SLIDER_SCALE_GAMMA);
  }

  public void bgColorRedREd_changed() {
    flameTextFieldChanged(bgColorRedSlider, bgColorRedREd, "bgColorRed", 1.0);
  }

  public void bgBGColorBlueREd_changed() {
    flameTextFieldChanged(bgColorBlueSlider, bgColorBlueREd, "bgColorBlue", 1.0);
  }

  public void pixelsPerUnitREd_changed() {
    flameTextFieldChanged(pixelsPerUnitSlider, pixelsPerUnitREd, "pixelsPerUnit", 1.0);
  }

  public void brightnessREd_changed() {
    flameTextFieldChanged(brightnessSlider, brightnessREd, "brightness", SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY);
  }

  public void contrastSlider_stateChanged(ChangeEvent e) {
    flameSliderChanged(contrastSlider, contrastREd, "contrast", SLIDER_SCALE_BRIGHTNESS_CONTRAST_VIBRANCY);
  }

  public void whiteLevelREd_changed() {
    flameTextFieldChanged(whiteLevelSlider, whiteLevelREd, "whiteLevel", 1.0);
  }

  public void randomPaletteButton_actionPerformed(ActionEvent e) {
    if (currFlame != null) {
      RGBPalette palette = new RandomRGBPaletteGenerator().generatePalette(Integer.parseInt(paletteRandomPointsREd.getText()));
      currFlame.setPalette(palette);
      refreshPaletteUI(palette);
      refreshFlameImage();
    }
  }

  private JFileChooser getImageJFileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.addChoosableFileFilter(new ImageFileFilter());
    fileChooser.setAcceptAllFileFilterUsed(false);
    return fileChooser;
  }

  public void grabPaletteFromImageButton_actionPerformed(ActionEvent e) {
    try {
      JFileChooser chooser = getImageJFileChooser();
      chooser.setCurrentDirectory(new File(prefs.getInputImagePath()));
      if (chooser.showOpenDialog(centerPanel) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        prefs.setLastInputImageFile(file);
        SimpleImage img = new ImageReader(centerPanel).loadImage(file.getAbsolutePath());
        RGBPalette palette = new RGBPaletteImporter().importFromImage(img);
        currFlame.setPalette(palette);
        refreshPaletteUI(palette);
        refreshFlameImage();
      }
    }
    catch (Throwable ex) {
      errorHandler.handleError(ex);
    }

  }

  public void paletteRedREd_changed() {
    paletteTextFieldChanged(paletteRedSlider, paletteRedREd, "modRed", 1.0);
  }

  public void paletteSaturationREd_changed() {
    paletteTextFieldChanged(paletteSaturationSlider, paletteSaturationREd, "modSaturation", 1.0);
  }

  public void paletteBlueREd_changed() {
    paletteTextFieldChanged(paletteBlueSlider, paletteBlueREd, "modBlue", 1.0);
  }

  public void paletteBlueSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteBlueSlider, paletteBlueREd, "modBlue", 1.0);
  }

  public void paletteBrightnessSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteBrightnessSlider, paletteBrightnessREd, "modBrightness", 1.0);
  }

  public void paletteBrightnessREd_changed() {
    paletteTextFieldChanged(paletteBrightnessSlider, paletteBrightnessREd, "modBrightness", 1.0);
  }

  public void paletteContrastREd_changed() {
    paletteTextFieldChanged(paletteContrastSlider, paletteContrastREd, "modContrast", 1.0);
  }

  public void paletteGreenREd_changed() {
    paletteTextFieldChanged(paletteGreenSlider, paletteGreenREd, "modGreen", 1.0);
  }

  public void paletteGreenSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteGreenSlider, paletteGreenREd, "modGreen", 1.0);
  }

  public void paletteHueREd_changed() {
    paletteTextFieldChanged(paletteHueSlider, paletteHueREd, "modHue", 1.0);
  }

  public void paletteGammaSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteGammaSlider, paletteGammaREd, "modGamma", 1.0);
  }

  public void paletteGammaREd_changed() {
    paletteTextFieldChanged(paletteGammaSlider, paletteGammaREd, "modGamma", 1.0);
  }

  public void paletteRedSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteRedSlider, paletteRedREd, "modRed", 1.0);
  }

  public void paletteContrastSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteContrastSlider, paletteContrastREd, "modContrast", 1.0);
  }

  public void paletteSaturationSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteSaturationSlider, paletteSaturationREd, "modSaturation", 1.0);
  }

  public void paletteShiftREd_changed() {
    paletteTextFieldChanged(paletteShiftSlider, paletteShiftREd, "modShift", 1.0);
  }

  public void paletteHueSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteHueSlider, paletteHueREd, "modHue", 1.0);
  }

  public void paletteShiftSlider_stateChanged(ChangeEvent e) {
    paletteSliderChanged(paletteShiftSlider, paletteShiftREd, "modShift", 1.0);
  }

  public void exportImageButton_actionPerformed(ActionEvent e) {
    if (currFlame != null) {
      try {
        JFileChooser chooser = getImageJFileChooser();
        try {
          chooser.setCurrentDirectory(new File(prefs.getOutputImagePath()));
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
        if (chooser.showSaveDialog(centerPanel) == JFileChooser.APPROVE_OPTION) {
          File file = chooser.getSelectedFile();
          prefs.setLastOutputImageFile(file);

          int width = Integer.parseInt(renderWidthREd.getText());
          int height = Integer.parseInt(renderHeightREd.getText());
          int quality = Integer.parseInt(renderQualityREd.getText());
          SimpleImage img = new SimpleImage(width, height);
          Flame flame = currFlame;
          double wScl = (double) img.getImageWidth() / (double) flame.getWidth();
          double hScl = (double) img.getImageHeight() / (double) flame.getHeight();
          flame.setPixelsPerUnit((wScl + hScl) * 0.5 * flame.getPixelsPerUnit());
          flame.setWidth(img.getImageWidth());
          flame.setHeight(img.getImageHeight());
          flame.setSampleDensity(quality);
          FlameRenderer renderer = new FlameRenderer();
          renderer.renderFlame(flame, img);
          new ImageWriter().saveImage(img, file.getAbsolutePath());
        }
      }
      catch (Throwable ex) {
        errorHandler.handleError(ex);
      }
    }
  }

  public void saveFlameButton_actionPerformed(ActionEvent e) {
    if (currFlame != null) {
      JFileChooser chooser = getFlameJFileChooser();
      try {
        chooser.setCurrentDirectory(new File(prefs.getOutputFlamePath()));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      if (chooser.showSaveDialog(centerPanel) == JFileChooser.APPROVE_OPTION) {
        try {
          File file = chooser.getSelectedFile();
          new Flam3Writer().writeFlame(currFlame, file.getAbsolutePath());
          prefs.setLastOutputFlameFile(file);
        }
        catch (Throwable ex) {
          errorHandler.handleError(ex);
        }
      }
    }
  }

  public void randomFlameButton_actionPerformed(ActionEvent e) {
    ImagePanel imgPanel = getFlamePanel();
    int width = imgPanel.getWidth();
    int height = imgPanel.getHeight();
    Flame flame = new RandomFlameGenerator().createFlame();
    flame.setWidth(width);
    flame.setHeight(height);
    RGBPalette palette = new RandomRGBPaletteGenerator().generatePalette(Integer.parseInt(paletteRandomPointsREd.getText()));
    flame.setPalette(palette);
    currFlame = flame;
    refreshUI();
  }

  public XForm getCurrXForm() {
    XForm xForm = null;
    if (currFlame != null) {
      int row = transformationsTable.getSelectedRow();
      if (row >= 0 && row < currFlame.getXForms().size()) {
        xForm = currFlame.getXForms().get(row);
      }
      else if (row == currFlame.getXForms().size()) {
        xForm = currFlame.getFinalXForm();
      }
    }
    return xForm;
  }

  public void transformationTableClicked() {
    XForm xForm = getCurrXForm();
    refreshXFormUI(xForm);
    enableControls(xForm);
  }

  private void enableControls(XForm xForm) {
    boolean enabled = xForm != null;
    affineRotateLeftButton.setEnabled(enabled);
    affineRotateRightButton.setEnabled(enabled);
    affineEnlargeButton.setEnabled(enabled);
    affineShrinkButton.setEnabled(enabled);
    affineMoveUpButton.setEnabled(enabled);
    affineMoveLeftButton.setEnabled(enabled);
    affineMoveRightButton.setEnabled(enabled);
    affineMoveDownButton.setEnabled(enabled);
    addTransformationButton.setEnabled(currFlame != null);
    duplicateTransformationButton.setEnabled(enabled);
    deleteTransformationButton.setEnabled(enabled);
    addFinalTransformationButton.setEnabled(currFlame != null && currFlame.getFinalXForm() == null);
  }

  private void refreshXFormUI(XForm pXForm) {
    if (pXForm != null) {
      affineC00REd.setText(Tools.doubleToString(pXForm.getCoeff00()));
      affineC01REd.setText(Tools.doubleToString(pXForm.getCoeff01()));
      affineC10REd.setText(Tools.doubleToString(pXForm.getCoeff10()));
      affineC11REd.setText(Tools.doubleToString(pXForm.getCoeff11()));
      affineC20REd.setText(Tools.doubleToString(pXForm.getCoeff20()));
      affineC21REd.setText(Tools.doubleToString(pXForm.getCoeff21()));
    }
    else {
      affineC00REd.setText(null);
      affineC01REd.setText(null);
      affineC10REd.setText(null);
      affineC11REd.setText(null);
      affineC20REd.setText(null);
      affineC21REd.setText(null);
    }
  }
}