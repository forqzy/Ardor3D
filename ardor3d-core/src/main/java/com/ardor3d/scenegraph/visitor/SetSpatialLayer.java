/**
 * Copyright (c) 2008-2019 Bird Dog Games, Inc.
 *
 * This file is part of Ardor3D.
 *
 * Ardor3D is free software: you can redistribute it and/or modify it
 * under the terms of its license which may be found in the accompanying
 * LICENSE file or at <http://www.ardor3d.com/LICENSE>.
 */

package com.ardor3d.scenegraph.visitor;

import com.ardor3d.scenegraph.Spatial;

public class SetSpatialLayer implements Visitor {
    private final String _layer;

    public SetSpatialLayer(final String layer) {
        _layer = layer;
    }

    public void visit(final Spatial spatial) {
        spatial.setLayer(_layer);
    }
}