package com.example.android.bfit;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleFitPackage {



    public static String PACKAGE_NAME;

    public GoogleFitPackage(String PACKAGE_NAME) {
        this.PACKAGE_NAME = PACKAGE_NAME;
    }


    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new GoogleFitModule(reactContext));
        return modules;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }


    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return new ArrayList<>();
    }
}

