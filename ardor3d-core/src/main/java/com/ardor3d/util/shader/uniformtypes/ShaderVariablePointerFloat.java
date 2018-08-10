/**
 * Copyright (c) 2008-2012 Bird Dog Games, Inc..
 *
 * This file is part of Ardor3D.
 *
 * Ardor3D is free software: you can redistribute it and/or modify it 
 * under the terms of its license which may be found in the accompanying
 * LICENSE file or at <http://www.ardor3d.com/LICENSE>.
 */

package com.ardor3d.util.shader.uniformtypes;

import java.io.IOException;
import java.nio.FloatBuffer;

import com.ardor3d.scenegraph.FloatBufferData;
import com.ardor3d.util.export.InputCapsule;
import com.ardor3d.util.export.OutputCapsule;
import com.ardor3d.util.shader.ShaderVariable;

/** ShaderVariablePointerFloat */
public class ShaderVariablePointerFloat extends ShaderVariable {
    /**
     * Specifies the number of values for each element of the generic vertex attribute array. Must be 1, 2, 3, or 4.
     */
    public int size;
    /**
     * Specifies the byte offset between consecutive attribute values. If stride is 0 (the initial value), the attribute
     * values are understood to be tightly packed in the array.
     */
    public int stride;
    /**
     * Specifies whether fixed-point data values should be normalized (true) or converted directly as fixed-point values
     * (false) when they are accessed.
     */
    public boolean normalized;
    /** The data for the attribute value */
    public FloatBufferData data;

    @Override
    public boolean hasData() {
        return data != null && data.getBuffer() != null;
    }

    @Override
    public void write(final OutputCapsule capsule) throws IOException {
        super.write(capsule);
        capsule.write(size, "size", 0);
        capsule.write(stride, "stride", 0);
        capsule.write(normalized, "normalized", false);
        capsule.write(data, "data", null);
    }

    @Override
    public void read(final InputCapsule capsule) throws IOException {
        super.read(capsule);
        size = capsule.readInt("size", 0);
        stride = capsule.readInt("stride", 0);
        normalized = capsule.readBoolean("normalized", false);
        data = (FloatBufferData) capsule.readSavable("data", null);
        // XXX: transitional
        if (data == null) {
            final FloatBuffer buff = capsule.readFloatBuffer("data", null);
            if (buff != null) {
                data = new FloatBufferData(buff, size);
            }
        }
    }
}
