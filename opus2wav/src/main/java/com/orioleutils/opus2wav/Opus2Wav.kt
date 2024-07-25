package com.orioleutils.opus2wav

class Opus2Wav {
    companion object {
        init {
            System.loadLibrary("opus2wav")
        }

        /*
     * A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
        @JvmStatic
        external fun stringFromJNI(): String
    }
}