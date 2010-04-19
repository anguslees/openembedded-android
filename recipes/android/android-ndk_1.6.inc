DESCRIPTION ?= "Android NDK"
SECTION ?= "devel"
HOMEPAGE ?= "http://developer.android.com/sdk/index.html"
PV = 1.6_r1

ANDROID_PLATFORM ?= 4

# FIXME: generate based on BUILD_OS-BUILD_ARCH
ANDROID_BUILD_OS = ${BUILD_OS}
ANDROID_BUILD_ARCH = x86
ANDROID_BUILD_SYS = ${ANDROID_BUILD_OS}-${ANDROID_BUILD_ARCH}

SRC_URI = "http://dl.google.com/android/ndk/android-ndk-${PV}-${ANDROID_BUILD_SYS}.zip;name=ndk"
S = "${WORKDIR}/android-ndk-${PV}"

SRC_URI[ndk.md5sum] = "464d3dc02739b82917e6f0591e17edd1"
SRC_URI[ndk.sha256sum] = "4418ea2a7f45277fad837136e6e4c1bafb52d3c5ac815225c5343556770a8265"
