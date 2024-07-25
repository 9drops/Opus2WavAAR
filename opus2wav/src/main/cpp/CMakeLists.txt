cmake_minimum_required(VERSION 3.22.1)

project("opus2wav")

add_library(opus2wav SHARED
        opus2wav.cpp)

# Include libraries needed for opus2wav lib
target_link_libraries(opus2wav
                      android
                      log)
