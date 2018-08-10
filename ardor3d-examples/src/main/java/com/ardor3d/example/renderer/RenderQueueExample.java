/** * Copyright (c) 2008-2012 Bird Dog Games, Inc.. * * This file is part of Ardor3D. * * Ardor3D is free software: you can redistribute it and/or modify it  * under the terms of its license which may be found in the accompanying * LICENSE file or at <http://www.ardor3d.com/LICENSE>. */package com.ardor3d.example.renderer;import com.ardor3d.bounding.BoundingBox;import com.ardor3d.example.ExampleBase;import com.ardor3d.example.Purpose;import com.ardor3d.framework.Canvas;import com.ardor3d.image.Texture.MinificationFilter;import com.ardor3d.input.Key;import com.ardor3d.input.logical.InputTrigger;import com.ardor3d.input.logical.KeyPressedCondition;import com.ardor3d.input.logical.TriggerAction;import com.ardor3d.input.logical.TwoInputStates;import com.ardor3d.light.DirectionalLight;import com.ardor3d.math.ColorRGBA;import com.ardor3d.math.Vector2;import com.ardor3d.math.Vector3;import com.ardor3d.renderer.Renderer;import com.ardor3d.renderer.queue.RenderBucketType;import com.ardor3d.renderer.state.BlendState;import com.ardor3d.renderer.state.BlendState.DestinationFunction;import com.ardor3d.renderer.state.BlendState.SourceFunction;import com.ardor3d.renderer.state.LightState;import com.ardor3d.renderer.state.MaterialState;import com.ardor3d.renderer.state.MaterialState.MaterialFace;import com.ardor3d.renderer.state.TextureState;import com.ardor3d.renderer.state.ZBufferState;import com.ardor3d.scenegraph.Node;import com.ardor3d.scenegraph.hint.CullHint;import com.ardor3d.scenegraph.hint.LightCombineMode;import com.ardor3d.scenegraph.hint.TransparencyType;import com.ardor3d.scenegraph.shape.Box;import com.ardor3d.scenegraph.shape.Quad;import com.ardor3d.scenegraph.shape.Torus;import com.ardor3d.util.TextureManager;/** * Illustrates the Render Queue, which controls how Nodes are drawn when overlapping occurs. */@Purpose(htmlDescriptionKey = "com.ardor3d.example.renderer.RenderQueueExample", //thumbnailPath = "com/ardor3d/example/media/thumbnails/renderer_RenderQueueExample.jpg", //maxHeapMemory = 64)public class RenderQueueExample extends ExampleBase {    private boolean useQueue = false;    private boolean twoPass = false;    protected Node opaques, transps, orthos;    private boolean _updateTitle;    public static void main(final String[] args) {        start(RenderQueueExample.class);    }    @Override    protected void renderExample(final Renderer renderer) {        transps.getSceneHints().setTransparencyType(twoPass ? TransparencyType.TwoPass : TransparencyType.OnePass);        if (_updateTitle) {            _canvas.setTitle("Test Render Queue - " + useQueue + " - hit 'M' to toggle Queue mode - 'R' Two Pass: - "                    + twoPass);            _updateTitle = false;        }        if (!useQueue) {            renderer.setOrtho();            renderer.draw(orthos);            renderer.unsetOrtho();        } else {            renderer.draw(orthos);        }        transps.draw(renderer);        opaques.draw(renderer);    }    @Override    protected void initExample() {        _canvas.setTitle("Test Render Queue - false - hit 'M' to toggle Queue mode - 'R' Two Pass: - false");        _canvas.getCanvasRenderer().getCamera().setLocation(new Vector3(10, 0, 50));        final Vector3 max = new Vector3(5, 5, 5);        final Vector3 min = new Vector3(-5, -5, -5);        opaques = new Node("Opaques");        transps = new Node("Transps");        orthos = new Node("Orthos");        transps.getSceneHints().setRenderBucketType(RenderBucketType.Skip);        opaques.getSceneHints().setRenderBucketType(RenderBucketType.Skip);        orthos.getSceneHints().setRenderBucketType(RenderBucketType.Skip);        _root.attachChild(orthos);        _root.attachChild(transps);        _root.attachChild(opaques);        final Box b1 = new Box("Box", min, max);        b1.setModelBound(new BoundingBox());        b1.setTranslation(new Vector3(0, 0, -15));        opaques.attachChild(b1);        final Box b2 = new Box("Box", min, max);        b2.setModelBound(new BoundingBox());        b2.setTranslation(new Vector3(0, 0, -30));        opaques.attachChild(b2);        final Box b3 = new Box("Box", min, max);        b3.setModelBound(new BoundingBox());        b3.setTranslation(new Vector3(0, -15, -15));        opaques.attachChild(b3);        final TextureState ts = new TextureState();        ts.setEnabled(true);        ts.setTexture(TextureManager.load("images/ardor3d_white_256.jpg", MinificationFilter.Trilinear, true));        opaques.setRenderState(ts);        final LightState ls = new LightState();        ls.setEnabled(true);        final DirectionalLight dLight = new DirectionalLight();        dLight.setEnabled(true);        dLight.setDiffuse(new ColorRGBA(1, 1, 1, 1));        dLight.setDirection(new Vector3(1, 1, 1));        ls.attach(dLight);        final DirectionalLight dLight2 = new DirectionalLight();        dLight2.setEnabled(true);        dLight2.setDiffuse(new ColorRGBA(1, 1, 1, 1));        dLight2.setDirection(new Vector3(-1, -1, -1));        ls.attach(dLight2);        ls.setTwoSidedLighting(false);        transps.setRenderState(ls);        transps.getSceneHints().setLightCombineMode(LightCombineMode.Replace);        final Box tb1 = new Box("TBox Blue", min, max);        tb1.setModelBound(new BoundingBox());        tb1.setTranslation(new Vector3(0, 15, 15));        transps.attachChild(tb1);        final MaterialState ms1 = new MaterialState();        ms1.setEnabled(true);        ms1.setDiffuse(MaterialFace.FrontAndBack, new ColorRGBA(0, 0, 1, .75f));        ms1.setShininess(MaterialFace.FrontAndBack, 128);        tb1.setRenderState(ms1);        final Torus tb2 = new Torus("TBox Green", 20, 20, 3, 6);        tb2.setModelBound(new BoundingBox());        tb2.setTranslation(new Vector3(0, 0, 30));        transps.attachChild(tb2);        final MaterialState ms2 = new MaterialState();        ms2.setEnabled(true);        ms2.setDiffuse(MaterialFace.FrontAndBack, new ColorRGBA(0, 1, 0, .5f));        ms2.setShininess(MaterialFace.FrontAndBack, 128);        tb2.setRenderState(ms2);        final Box tb3 = new Box("TBox Red", min, max);        tb3.setModelBound(new BoundingBox());        tb3.setTranslation(new Vector3(0, 0, 15));        transps.attachChild(tb3);        final MaterialState ms3 = new MaterialState();        ms3.setEnabled(true);        ms3.setDiffuse(MaterialFace.FrontAndBack, new ColorRGBA(1, 0, 0, .5f));        ms3.setShininess(MaterialFace.FrontAndBack, 128);        tb3.setRenderState(ms3);        final BlendState as = new BlendState();        as.setEnabled(true);        as.setBlendEnabled(true);        as.setSourceFunction(SourceFunction.SourceAlpha);        as.setDestinationFunction(DestinationFunction.OneMinusSourceAlpha);        transps.setRenderState(as);        final Vector2 center = new Vector2(_canvas.getCanvasRenderer().getCamera().getWidth() >> 1, _canvas                .getCanvasRenderer().getCamera().getWidth() >> 1);        final Quad q1 = new Quad("Ortho Q1", 40, 40);        q1.setTranslation(new Vector3(100 + center.getX(), 100 + center.getY(), 0));        q1.getSceneHints().setOrthoOrder(1);        q1.setDefaultColor(ColorRGBA.WHITE);        q1.getSceneHints().setLightCombineMode(LightCombineMode.Off);        orthos.attachChild(q1);        final Quad q2 = new Quad("Ortho Q2", 100, 100);        q2.setTranslation(new Vector3(60 + center.getX(), 60 + center.getY(), 0));        q2.getSceneHints().setOrthoOrder(5);        q2.setDefaultColor(ColorRGBA.RED);        q2.getSceneHints().setLightCombineMode(LightCombineMode.Off);        orthos.attachChild(q2);        final Quad q3 = new Quad("Ortho Q3", 120, 60);        q3.setTranslation(new Vector3(-20 + center.getX(), -150 + center.getY(), 0));        q3.getSceneHints().setOrthoOrder(2);        q3.setDefaultColor(ColorRGBA.BLUE);        q3.getSceneHints().setLightCombineMode(LightCombineMode.Off);        orthos.attachChild(q3);        final ZBufferState zstate = new ZBufferState();        zstate.setWritable(false);        zstate.setEnabled(false);        orthos.setRenderState(zstate);        orthos.setRenderState(new LightState());        _root.getSceneHints().setCullHint(CullHint.Always);        opaques.getSceneHints().setCullHint(CullHint.Dynamic);        transps.getSceneHints().setCullHint(CullHint.Dynamic);        orthos.getSceneHints().setCullHint(CullHint.Never);    }    @Override    protected void registerInputTriggers() {        super.registerInputTriggers();        _logicalLayer.registerTrigger(new InputTrigger(new KeyPressedCondition(Key.M), new TriggerAction() {            public void perform(final Canvas source, final TwoInputStates inputStates, final double tpf) {                if (useQueue) {                    transps.getSceneHints().setRenderBucketType(RenderBucketType.Skip);                    opaques.getSceneHints().setRenderBucketType(RenderBucketType.Skip);                    orthos.getSceneHints().setRenderBucketType(RenderBucketType.Skip);                } else {                    transps.getSceneHints().setRenderBucketType(RenderBucketType.Transparent);                    opaques.getSceneHints().setRenderBucketType(RenderBucketType.Opaque);                    orthos.getSceneHints().setRenderBucketType(RenderBucketType.Ortho);                }                useQueue = !useQueue;                _updateTitle = true;            }        }));        _logicalLayer.registerTrigger(new InputTrigger(new KeyPressedCondition(Key.R), new TriggerAction() {            public void perform(final Canvas source, final TwoInputStates inputStates, final double tpf) {                twoPass = !twoPass;                _updateTitle = true;            }        }));    }}