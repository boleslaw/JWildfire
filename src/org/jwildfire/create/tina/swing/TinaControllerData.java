package org.jwildfire.create.tina.swing;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;

import org.jwildfire.create.tina.palette.RGBColor;
import org.jwildfire.swing.ImagePanel;

public class TinaControllerData {
  public JComboBox qualityProfileCmb;
  public JComboBox resolutionProfileCmb;
  public JComboBox batchQualityProfileCmb;
  public JComboBox batchResolutionProfileCmb;
  public JComboBox interactiveResolutionProfileCmb;
  public JComboBox swfAnimatorResolutionProfileCmb;
  public JWFNumberField cameraRollREd;
  public JSlider cameraRollSlider;
  public JWFNumberField cameraPitchREd;
  public JSlider cameraPitchSlider;
  public JWFNumberField cameraYawREd;
  public JSlider cameraYawSlider;
  public JWFNumberField cameraPerspectiveREd;
  public JSlider cameraPerspectiveSlider;
  public JWFNumberField camPosXREd;
  public JSlider camPosXSlider;
  public JWFNumberField camPosYREd;
  public JSlider camPosYSlider;
  public JWFNumberField camPosZREd;
  public JSlider camPosZSlider;
  public JWFNumberField cameraCentreXREd;
  public JSlider cameraCentreXSlider;
  public JWFNumberField cameraCentreYREd;
  public JSlider cameraCentreYSlider;
  public JWFNumberField cameraZoomREd;
  public JSlider cameraZoomSlider;
  public JWFNumberField focusXREd;
  public JSlider focusXSlider;
  public JWFNumberField focusYREd;
  public JSlider focusYSlider;
  public JWFNumberField focusZREd;
  public JSlider focusZSlider;
  public JWFNumberField dimishZREd;
  public JSlider dimishZSlider;
  public JWFNumberField camZREd;
  public JSlider camZSlider;
  public JWFNumberField cameraDOFREd;
  public JSlider cameraDOFSlider;
  public JWFNumberField cameraDOFAreaREd;
  public JSlider cameraDOFAreaSlider;
  public JWFNumberField cameraDOFExponentREd;
  public JSlider cameraDOFExponentSlider;
  public JCheckBox newDOFCBx;
  public JWFNumberField pixelsPerUnitREd;
  public JSlider pixelsPerUnitSlider;
  public JWFNumberField brightnessREd;
  public JSlider brightnessSlider;
  public JWFNumberField contrastREd;
  public JSlider contrastSlider;
  public JWFNumberField gammaREd;
  public JSlider gammaSlider;
  public JWFNumberField vibrancyREd;
  public JSlider vibrancySlider;
  public JWFNumberField saturationREd;
  public JSlider saturationSlider;
  public JWFNumberField filterRadiusREd;
  public JSlider filterRadiusSlider;
  public JComboBox filterKernelCmb;
  public JWFNumberField gammaThresholdREd;
  public JSlider gammaThresholdSlider;
  public JCheckBox bgTransparencyCBx;
  public JComboBox shadingCmb;
  public JWFNumberField shadingAmbientREd;
  public JSlider shadingAmbientSlider;
  public JWFNumberField shadingDiffuseREd;
  public JSlider shadingDiffuseSlider;
  public JWFNumberField shadingPhongREd;
  public JSlider shadingPhongSlider;
  public JWFNumberField shadingPhongSizeREd;
  public JSlider shadingPhongSizeSlider;
  public JComboBox shadingLightCmb;
  public JWFNumberField shadingLightXREd;
  public JSlider shadingLightXSlider;
  public JWFNumberField shadingLightYREd;
  public JSlider shadingLightYSlider;
  public JWFNumberField shadingLightZREd;
  public JSlider shadingLightZSlider;
  public JWFNumberField shadingLightRedREd;
  public JSlider shadingLightRedSlider;
  public JWFNumberField shadingLightGreenREd;
  public JSlider shadingLightGreenSlider;
  public JWFNumberField shadingLightBlueREd;
  public JSlider shadingLightBlueSlider;
  public JWFNumberField shadingBlurRadiusREd;
  public JSlider shadingBlurRadiusSlider;
  public JWFNumberField shadingBlurFadeREd;
  public JSlider shadingBlurFadeSlider;
  public JWFNumberField shadingBlurFallOffREd;
  public JSlider shadingBlurFallOffSlider;
  public JWFNumberField shadingDistanceColorRadiusREd;
  public JSlider shadingDistanceColorRadiusSlider;
  public JWFNumberField shadingDistanceColorScaleREd;
  public JSlider shadingDistanceColorScaleSlider;
  public JWFNumberField shadingDistanceColorExponentREd;
  public JSlider shadingDistanceColorExponentSlider;
  public JWFNumberField shadingDistanceColorOffsetXREd;
  public JSlider shadingDistanceColorOffsetXSlider;
  public JWFNumberField shadingDistanceColorOffsetYREd;
  public JSlider shadingDistanceColorOffsetYSlider;
  public JWFNumberField shadingDistanceColorOffsetZREd;
  public JSlider shadingDistanceColorOffsetZSlider;
  public JWFNumberField shadingDistanceColorStyleREd;
  public JSlider shadingDistanceColorStyleSlider;
  public JWFNumberField shadingDistanceColorCoordinateREd;
  public JSlider shadingDistanceColorCoordinateSlider;
  public JWFNumberField shadingDistanceColorShiftREd;
  public JSlider shadingDistanceColorShiftSlider;
  public JTextField paletteRandomPointsREd;
  public JComboBox paletteRandomGeneratorCmb;
  public JCheckBox paletteFadeColorsCBx;
  public JPanel paletteImgPanel;
  public JPanel colorChooserPaletteImgPanel;
  public ImagePanel palettePanel;
  public ImagePanel colorChooserPalettePanel;
  public JWFNumberField paletteShiftREd;
  public JSlider paletteShiftSlider;
  public JWFNumberField paletteRedREd;
  public JSlider paletteRedSlider;
  public JWFNumberField paletteGreenREd;
  public JSlider paletteGreenSlider;
  public JWFNumberField paletteBlueREd;
  public JSlider paletteBlueSlider;
  public JWFNumberField paletteHueREd;
  public JSlider paletteHueSlider;
  public JWFNumberField paletteSaturationREd;
  public JSlider paletteSaturationSlider;
  public JWFNumberField paletteContrastREd;
  public JSlider paletteContrastSlider;
  public JWFNumberField paletteGammaREd;
  public JSlider paletteGammaSlider;
  public JWFNumberField paletteBrightnessREd;
  public JSlider paletteBrightnessSlider;
  public JWFNumberField paletteSwapRGBREd;
  public JSlider paletteSwapRGBSlider;
  public JWFNumberField paletteFrequencyREd;
  public JSlider paletteFrequencySlider;
  public JWFNumberField paletteBlurREd;
  public JSlider paletteBlurSlider;
  public JButton paletteInvertBtn;
  public JButton paletteReverseBtn;
  public JTable renderBatchJobsTable;
  public JProgressBar batchRenderJobProgressBar;
  public JProgressBar batchRenderTotalProgressBar;
  public JPanel batchPreviewRootPanel;
  public JToggleButton affineScaleXButton;
  public JToggleButton affineScaleYButton;
  public JTable transformationsTable;
  public JButton affineResetTransformButton;
  public JWFNumberField affineC00REd;
  public JWFNumberField affineC01REd;
  public JWFNumberField affineC10REd;
  public JWFNumberField affineC11REd;
  public JWFNumberField affineC20REd;
  public JWFNumberField affineC21REd;
  public JWFNumberField affineRotateAmountREd;
  public JWFNumberField affineScaleAmountREd;
  public JWFNumberField affineMoveHorizAmountREd;
  public JWFNumberField affineMoveVertAmountREd;
  public JButton affineRotateLeftButton;
  public JButton affineRotateRightButton;
  public JButton affineEnlargeButton;
  public JButton affineShrinkButton;
  public JButton affineMoveUpButton;
  public JButton affineMoveLeftButton;
  public JButton affineMoveRightButton;
  public JButton affineMoveDownButton;
  public JButton affineFlipHorizontalButton;
  public JButton affineFlipVerticalButton;
  public JButton addTransformationButton;
  public JButton addLinkedTransformationButton;
  public JButton duplicateTransformationButton;
  public JButton deleteTransformationButton;
  public JButton addFinalTransformationButton;
  public JWFNumberField transformationWeightREd;
  public JButton affineRotateEditMotionCurveBtn;
  public JButton affineScaleEditMotionCurveBtn;
  public JToggleButton affineEditPostTransformButton;
  public JToggleButton affineEditPostTransformSmallButton;
  public JToggleButton affinePreserveZButton;
  public JButton randomizeButton;
  public JToggleButton toggleVariationsButton;
  public JToggleButton toggleTransparencyButton;
  public JPanel gradientLibraryPanel;
  public JPanel randomBatchPanel;
  public JScrollPane randomBatchScrollPane;
  public TinaNonlinearControlsRow[] TinaNonlinearControlsRows;
  public VariationControlsDelegate[] variationControlsDelegates;
  public JWFNumberField xFormColorREd;
  public JSlider xFormColorSlider;
  public JWFNumberField xFormSymmetryREd;
  public JSlider xFormSymmetrySlider;
  public JWFNumberField xFormModGammaREd;
  public JSlider xFormModGammaSlider;
  public JWFNumberField xFormModGammaSpeedREd;
  public JSlider xFormModGammaSpeedSlider;
  public JWFNumberField xFormModContrastREd;
  public JSlider xFormModContrastSlider;
  public JWFNumberField xFormModContrastSpeedREd;
  public JSlider xFormModContrastSpeedSlider;
  public JWFNumberField xFormModSaturationREd;
  public JSlider xFormModSaturationSlider;
  public JWFNumberField xFormModSaturationSpeedREd;
  public JSlider xFormModSaturationSpeedSlider;
  public JWFNumberField xFormOpacityREd;
  public JSlider xFormOpacitySlider;
  public JComboBox xFormDrawModeCmb;
  public JWFNumberField xFormAntialiasAmountREd;
  public JSlider xFormAntialiasAmountSlider;
  public JWFNumberField xFormAntialiasRadiusREd;
  public JSlider xFormAntialiasRadiusSlider;
  public JTable relWeightsTable;
  public JButton relWeightsZeroButton;
  public JButton relWeightsOneButton;
  public JWFNumberField relWeightREd;
  public JTable createPaletteColorsTable;
  public List<RGBColor> paletteKeyFrames;
  public JButton renderFlameButton;
  public JButton renderMainButton;
  public JButton appendToMovieButton;
  public JToggleButton mouseTransformMoveTrianglesButton;
  public JToggleButton mouseTransformRotateTrianglesButton;
  public JToggleButton mouseTransformScaleTrianglesButton;
  public JToggleButton mouseTransformEditFocusPointButton;
  public JToggleButton mouseTransformEditPointsButton;
  public JToggleButton mouseTransformEditGradientButton;
  public JToggleButton mouseTransformEditTriangleViewButton;
  public JToggleButton mouseTransformEditViewButton;
  public JToggleButton mouseTransformSlowButton;
  public JToggleButton toggleTriangleWithColorsButton;
  public JButton batchRenderAddFilesButton;
  public JButton batchRenderFilesMoveDownButton;
  public JButton batchRenderFilesMoveUpButton;
  public JButton batchRenderFilesRemoveButton;
  public JButton batchRenderFilesRemoveAllButton;
  public JButton batchRenderStartButton;
  public JTextPane helpPane;
  public JTextPane faqPane;
  public JButton undoButton;
  public JButton redoButton;
  public JButton editTransformCaptionButton;
  public JButton editFlameTileButton;
  public JButton snapShotButton;
  public JButton qSaveButton;
  public JButton quickMutationButton;
  public JButton dancingFlamesButton;
  public JButton movieButton;
  public JToggleButton transformSlowButton;
  public JToggleButton transparencyButton;
  public JTree scriptTree;
  public JTextArea scriptDescriptionTextArea;
  public JTextArea scriptTextArea;
  public JButton compileScriptButton;
  public JButton saveScriptBtn;
  public JButton revertScriptBtn;
  public JButton rescanScriptsBtn;
  public JButton newScriptBtn;
  public JButton newScriptFromFlameBtn;
  public JButton deleteScriptBtn;
  public JButton scriptRenameBtn;
  public JButton scriptDuplicateBtn;
  public JButton scriptRunBtn;
  public JTree gradientLibTree;
  public JButton backgroundColorIndicatorBtn;
  public JWFNumberField layerWeightEd;
  public JButton layerAddBtn;
  public JButton layerDuplicateBtn;
  public JButton layerDeleteBtn;
  public JTable layersTable;
  public JToggleButton layerVisibleBtn;
  public JToggleButton layerAppendBtn;
  public JToggleButton layerPreviewBtn;
  public JButton layerHideOthersBtn;
  public JButton layerShowAllBtn;
  public JWFNumberField motionBlurLengthField;
  public JSlider motionBlurLengthSlider;
  public JWFNumberField motionBlurTimeStepField;
  public JSlider motionBlurTimeStepSlider;
  public JWFNumberField motionBlurDecayField;
  public JSlider motionBlurDecaySlider;
  public JComboBox postSymmetryTypeCmb;
  public JWFNumberField postSymmetryDistanceREd;
  public JSlider postSymmetryDistanceSlider;
  public JWFNumberField postSymmetryRotationREd;
  public JSlider postSymmetryRotationSlider;
  public JWFNumberField postSymmetryOrderREd;
  public JSlider postSymmetryOrderSlider;
  public JWFNumberField postSymmetryCentreXREd;
  public JSlider postSymmetryCentreXSlider;
  public JWFNumberField postSymmetryCentreYREd;
  public JSlider postSymmetryCentreYSlider;
  public JComboBox stereo3dModeCmb;
  public JWFNumberField stereo3dAngleREd;
  public JSlider stereo3dAngleSlider;
  public JWFNumberField stereo3dEyeDistREd;
  public JSlider stereo3dEyeDistSlider;
  public JComboBox stereo3dLeftEyeColorCmb;
  public JComboBox stereo3dRightEyeColorCmb;
  public JWFNumberField stereo3dInterpolatedImageCountREd;
  public JSlider stereo3dInterpolatedImageCountSlider;
  public JComboBox stereo3dPreviewCmb;
  public JWFNumberField stereo3dFocalOffsetREd;
  public JSlider stereo3dFocalOffsetSlider;
  public JCheckBox stereo3dSwapSidesCBx;
  public JToggleButton toggleDrawGridButton;
  public JSlider editorFractalBrightnessSlider;
  public JComboBox triangleStyleCmb;
}