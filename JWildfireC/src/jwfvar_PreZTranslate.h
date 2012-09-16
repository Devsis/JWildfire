/*
 JWildfireC - an external C-based fractal-flame-renderer for JWildfire
 Copyright (C) 2012 Andreas Maschke

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

#ifndef JWFVAR_PRE_ZTRANSLATE_H_
#define JWFVAR_PRE_ZTRANSLATE_H_

#include "jwf_Variation.h"

class PreZTranslateFunc: public Variation {
public:
	PreZTranslateFunc() {
	}

	const char* getName() const {
		return "pre_ztranslate";
	}

	void transform(FlameTransformationContext *pContext, XForm *pXForm, XYZPoint *pAffineTP, XYZPoint *pVarTP, float pAmount) {
    pAffineTP->z += pAmount;
	}

	PreZTranslateFunc* makeCopy() {
		return new PreZTranslateFunc(*this);
	}

	int const getPriority() {
		return -1;
	}

};

#endif // JWFVAR_PRE_ZTRANSLATE_H_