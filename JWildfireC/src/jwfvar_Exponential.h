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

#ifndef JWFVAR_EXPONENTIAL_H_
#define JWFVAR_EXPONENTIAL_H_

#include "jwf_Variation.h"

class ExponentialFunc: public Variation {
public:
	ExponentialFunc() {
	}

	const char* getName() const {
		return "exponential";
	}

	void transform(FlameTransformationContext *pContext, XForm *pXForm, XYZPoint *pAffineTP, XYZPoint *pVarTP, float pAmount) {
    float r = M_PI * pAffineTP->y;
    float sinr = sinf(r);
    float cosr = cosf(r);
    float d = pAmount * expf(pAffineTP->x - 1.0f);
    pVarTP->x += cosr * d;
    pVarTP->y += sinr * d;
		if (pContext->isPreserveZCoordinate) {
      pVarTP->z += pAmount * pAffineTP->z;
    }
	}

	ExponentialFunc* makeCopy() {
		return new ExponentialFunc(*this);
	}

};

#endif // JWFVAR_EXPONENTIAL_H_