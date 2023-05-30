package com.example.compmath.interpolation;

import com.example.compmath.model.Polynomial;
import com.example.compmath.model.TableOfValues;

public interface InterpolationFunction {
    Polynomial interpolate(TableOfValues values, double value);
}
