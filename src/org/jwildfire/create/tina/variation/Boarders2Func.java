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

import static org.jwildfire.base.mathlib.MathLib.EPSILON;
import static org.jwildfire.base.mathlib.MathLib.fabs;
import static org.jwildfire.base.mathlib.MathLib.rint;
import static org.jwildfire.create.tina.base.Constants.AVAILABILITY_CUDA;
import static org.jwildfire.create.tina.base.Constants.AVAILABILITY_JWILDFIRE;

import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

public class Boarders2Func extends VariationFunc {
  private static final long serialVersionUID = 1L;

  private static final String PARAM_C = "c";
  private static final String PARAM_LEFT = "left";
  private static final String PARAM_RIGHT = "right";
  private static final String[] paramNames = { PARAM_C, PARAM_LEFT, PARAM_RIGHT };

  private double c = 0.4;
  private double left = 0.65;
  private double right = 0.35;

  @Override
  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
    // boarders2 by Xyrus02, http://xyrus02.deviantart.com/art/Boarders2-plugin-for-Apophysis-173427128
    double roundX = rint(pAffineTP.x);
    double roundY = rint(pAffineTP.y);
    double offsetX = pAffineTP.x - roundX;
    double offsetY = pAffineTP.y - roundY;
    if (pContext.random() >= _cr) {
      pVarTP.x += pAmount * (offsetX * _c + roundX);
      pVarTP.y += pAmount * (offsetY * _c + roundY);
    }
    else {
      if (fabs(offsetX) >= fabs(offsetY)) {
        if (offsetX >= 0.0) {
          pVarTP.x += pAmount * (offsetX * _c + roundX + _cl);
          pVarTP.y += pAmount * (offsetY * _c + roundY + _cl * offsetY / offsetX);
        }
        else {
          pVarTP.x += pAmount * (offsetX * _c + roundX - _cl);
          pVarTP.y += pAmount * (offsetY * _c + roundY - _cl * offsetY / offsetX);
        }
      }
      else {
        if (offsetY >= 0.0) {
          pVarTP.y += pAmount * (offsetY * _c + roundY + _cl);
          pVarTP.x += pAmount * (offsetX * _c + roundX + offsetX / offsetY * _cl);
        }
        else {
          pVarTP.y += pAmount * (offsetY * _c + roundY - _cl);
          pVarTP.x += pAmount * (offsetX * _c + roundX - offsetX / offsetY * _cl);
        }
      }
    }
    if (pContext.isPreserveZCoordinate()) {
      pVarTP.z += pAmount * pAffineTP.z;
    }
  }

  @Override
  public String[] getParameterNames() {
    return paramNames;
  }

  @Override
  public Object[] getParameterValues() {
    return new Object[] { c, left, right };
  }

  @Override
  public void setParameter(String pName, double pValue) {
    if (PARAM_C.equalsIgnoreCase(pName))
      c = pValue;
    else if (PARAM_LEFT.equalsIgnoreCase(pName))
      left = pValue;
    else if (PARAM_RIGHT.equalsIgnoreCase(pName))
      right = pValue;
    else
      throw new IllegalArgumentException(pName);
  }

  @Override
  public String getName() {
    return "boarders2";
  }

  private double _c, _cl, _cr;

  @Override
  public void init(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) {
    _c = fabs(c);
    _cl = fabs(left);
    _cr = fabs(right);
    _c = _c == 0 ? EPSILON : _c;
    _cl = _cl == 0 ? EPSILON : _cl;
    _cr = _cr == 0 ? EPSILON : _cr;
    _cl = _c * _cl;
    _cr = _c + (_c * _cr);
  }

  @Override
  public int getAvailability() {
    return AVAILABILITY_JWILDFIRE | AVAILABILITY_CUDA;
  }
}
