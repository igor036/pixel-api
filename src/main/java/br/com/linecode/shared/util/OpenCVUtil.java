package br.com.linecode.shared.util;

import org.opencv.core.Core;

public abstract class OpenCVUtil {
    
    public static void initOpenCV() {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}