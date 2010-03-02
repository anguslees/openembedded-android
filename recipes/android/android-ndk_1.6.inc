DESCRIPTION ?= "Android NDK"
SECTION ?= "devel"
HOMEPAGE ?= "http://developer.android.com/sdk/index.html"
PV = 1.6_r1

ANDROID_PLATFORM ?= 4

# FIXME: generate based on BUILD_OS-BUILD_ARCH
ANDROID_BUILD_OS = ${BUILD_OS}
ANDROID_BUILD_ARCH = x86
ANDROID_BUILD_SYS = ${ANDROID_BUILD_OS}-${ANDROID_BUILD_ARCH}

SRC_URI = "http://dl.google.com/android/ndk/android-ndk-${PV}-${ANDROID_BUILD_SYS}.zip"
S = "${WORKDIR}/android-ndk-${PV}"
