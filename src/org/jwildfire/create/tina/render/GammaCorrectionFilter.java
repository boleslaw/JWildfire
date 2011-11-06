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
package org.jwildfire.create.tina.render;

import org.jwildfire.create.tina.base.Flame;

public class GammaCorrectionFilter {
  private final Flame flame;
  private int vibInt;
  private int inverseVibInt;
  private double gamma;
  private double sclGamma;
  private int bgRed, bgGreen, bgBlue;

  public GammaCorrectionFilter(Flame pFlame) {
    flame = pFlame;
    initFilter();
  }

  private void initFilter() {
    gamma = (flame.getGamma() == 0.0) ? flame.getGamma() : 1.0 / flame.getGamma();

    vibInt = (int) (flame.getVibrancy() * 256.0 + 0.5);
    inverseVibInt = 256 - vibInt;

    sclGamma = 0.0;
    if (flame.getGammaThreshold() != 0) {
      sclGamma = Math.pow(flame.getGammaThreshold(), gamma - 1);
    }

    bgRed = flame.getBGColorRed();
    bgGreen = flame.getBGColorGreen();
    bgBlue = flame.getBGColorBlue();
  }

  public void transformPoint(LogDensityPoint logDensityPnt, GammaCorrectedRGBPoint pRGBPoint) {
    double logScl;
    int inverseAlphaInt;
    if (logDensityPnt.intensity > 0.0) {
      // gamma linearization
      double alpha;
      if (logDensityPnt.intensity <= flame.getGammaThreshold()) {
        double frac = logDensityPnt.intensity / flame.getGammaThreshold();
        alpha = (1.0 - frac) * logDensityPnt.intensity * sclGamma + frac * Math.pow(logDensityPnt.intensity, gamma);
      }
      else {
        alpha = Math.pow(logDensityPnt.intensity, gamma);
      }
      logScl = vibInt * alpha / logDensityPnt.intensity;
      int alphaInt = (int) (alpha * 256 + 0.5);
      if (alphaInt < 0)
        alphaInt = 0;
      else if (alphaInt > 255)
        alphaInt = 255;
      inverseAlphaInt = 255 - alphaInt;
    }
    else {
      pRGBPoint.red = bgRed;
      pRGBPoint.green = bgGreen;
      pRGBPoint.blue = bgBlue;
      return;
    }

    int red, green, blue;
    if (inverseVibInt > 0) {
      red = (int) (logScl * logDensityPnt.red + inverseVibInt * Math.pow(logDensityPnt.red, gamma) + 0.5);
      green = (int) (logScl * logDensityPnt.green + inverseVibInt * Math.pow(logDensityPnt.green, gamma) + 0.5);
      blue = (int) (logScl * logDensityPnt.blue + inverseVibInt * Math.pow(logDensityPnt.blue, gamma) + 0.5);
    }
    else {
      red = (int) (logScl * logDensityPnt.red + 0.5);
      green = (int) (logScl * logDensityPnt.green + 0.5);
      blue = (int) (logScl * logDensityPnt.blue + 0.5);
    }

    red = red + ((inverseAlphaInt * bgRed) >> 8);
    if (red < 0)
      red = 0;
    else if (red > 255)
      red = 255;

    green = green + ((inverseAlphaInt * bgGreen) >> 8);
    if (green < 0)
      green = 0;
    else if (green > 255)
      green = 255;

    blue = blue + ((inverseAlphaInt * bgBlue) >> 8);
    if (blue < 0)
      blue = 0;

    pRGBPoint.red = red;
    pRGBPoint.green = green;
    pRGBPoint.blue = blue;
  }
}