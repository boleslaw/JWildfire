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
package org.jwildfire.create.tina.variation;

import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

public class Disc2Func extends VariationFunc {

  private static final String PARAM_ROT = "rot";
  private static final String PARAM_TWIST = "twist";

  private static final String[] paramNames = { PARAM_ROT, PARAM_TWIST };

  private double rot = 2.0;
  private double twist = 0.50;
  // precalculated
  private double timespi = 0.0;
  private double sinadd = 0.0;
  private double cosadd = 0.0;

  private void precalc() {
    double add = twist;
    timespi = rot * Math.PI;
    sinadd = Math.sin(add);
    cosadd = Math.cos(add);
    cosadd -= 1;
    double k;
    if (add > 2.0 * Math.PI) {
      k = (1 + add - 2.0 * Math.PI);
      cosadd *= k;
      sinadd *= k;
    }
    if (add < -2.0 * Math.PI) {
      k = (1 + add + 2.0 * Math.PI);
      cosadd *= k;
      sinadd *= k;
    }
  }

  @Override
  public void transform(XFormTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
    /* Z+ variation Jan 07 */
    precalc();
    double t = timespi * (pAffineTP.x + pAffineTP.y);
    double sinr = Math.sin(t);
    double cosr = Math.cos(t);
    double r = pAmount * pAffineTP.getPrecalcAtan() / Math.PI;

    pVarTP.x += (sinr + cosadd) * r;
    pVarTP.y += (cosr + sinadd) * r;
  }

  @Override
  public String[] getParameterNames() {
    return paramNames;
  }

  @Override
  public Object[] getParameterValues() {
    return new Object[] { rot, twist };
  }

  @Override
  public void setParameter(String pName, double pValue) {
    if (PARAM_ROT.equalsIgnoreCase(pName))
      rot = pValue;
    else if (PARAM_TWIST.equalsIgnoreCase(pName))
      twist = pValue;
    else
      throw new IllegalArgumentException(pName);
  }

  @Override
  public String getName() {
    return "disc2";
  }

}