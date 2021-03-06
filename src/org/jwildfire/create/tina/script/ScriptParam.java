/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2015 Andreas Maschke

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
package org.jwildfire.create.tina.script;

import java.io.Serializable;

import org.jwildfire.base.Tools;
import org.jwildfire.base.mathlib.MathLib;

public class ScriptParam implements Serializable {
  private static final long serialVersionUID = 1L;

  private final Object value;

  public ScriptParam(Object pValue) {
    value = pValue;
  }

  public Integer asInteger() {
    if (value == null) {
      return Integer.valueOf(0);
    }
    else if (value instanceof String) {
      String valStr = (String) value;
      return valStr.length() > 0 ? Integer.parseInt(valStr) : Integer.valueOf(0);
    }
    else if (value instanceof Boolean) {
      return ((Boolean) value).booleanValue() ? Integer.valueOf(1) : Integer.valueOf(0);
    }
    else if (value instanceof Double) {
      return Integer.valueOf(Tools.FTOI((Double) value));
    }
    else {
      return (Integer) value;
    }
  }

  public Double asDouble() {
    if (value == null) {
      return Double.valueOf(0.0);
    }
    else if (value instanceof String) {
      String valStr = (String) value;
      return valStr.length() > 0 ? Double.parseDouble(valStr) : Double.valueOf(0.0);
    }
    else if (value instanceof Integer) {
      return Double.valueOf((Integer) value);
    }
    else if (value instanceof Boolean) {
      return ((Boolean) value).booleanValue() ? Double.valueOf(1.0) : Double.valueOf(0);
    }
    else {
      return (Double) value;
    }
  }

  public String asString() {
    if (value == null) {
      return "";
    }
    else if (value instanceof Double) {
      return Tools.doubleToString((Double) value);
    }
    else if (value instanceof Integer) {
      return String.valueOf((Integer) value);
    }
    else if (value instanceof Boolean) {
      return String.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
    }
    else {
      return (String) value;
    }
  }

  public Boolean asBoolean() {
    if (value == null) {
      return Boolean.FALSE;
    }
    else if (value instanceof Double) {
      return MathLib.fabs(((Double) value).doubleValue() - 1.0) < MathLib.EPSILON ? Boolean.TRUE : Boolean.FALSE;
    }
    else if (value instanceof Integer) {
      return ((Integer) value).intValue() == 1 ? Boolean.TRUE : Boolean.FALSE;
    }
    else if (value instanceof String) {
      String valStr = (String) value;
      return valStr.length() > 0 && MathLib.fabs((Tools.stringToDouble(valStr)) - 1.0) < MathLib.EPSILON ? Boolean.TRUE : Boolean.FALSE;
    }
    else {
      return (Boolean) value;
    }
  }
}
