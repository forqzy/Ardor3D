/**
 * Copyright (c) 2008-2012 Bird Dog Games, Inc..
 *
 * This file is part of Ardor3D.
 *
 * Ardor3D is free software: you can redistribute it and/or modify it
 * under the terms of its license which may be found in the accompanying
 * LICENSE file or at <http://www.ardor3d.com/LICENSE>.
 */

package com.ardor3d.renderer.state.record;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import com.ardor3d.renderer.ContextCapabilities;
import com.ardor3d.renderer.state.ClipState;
import com.ardor3d.util.geom.BufferUtils;

public class ClipStateRecord extends StateRecord {

    public final boolean[] planeEnabled;
    public final DoubleBuffer buf;

    public ClipStateRecord(final ContextCapabilities caps) {
        planeEnabled = new boolean[ClipState.MAX_CLIP_PLANES];
        buf = BufferUtils.createDoubleBuffer(4);
    }

    @Override
    public void invalidate() {
        super.invalidate();

        Arrays.fill(planeEnabled, false);
    }
}
