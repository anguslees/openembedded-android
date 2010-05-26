DESCRIPTION ?= "Android NDK"
SECTION ?= "devel"
HOMEPAGE = "http://developer.android.com/sdk/index.html"

ANDROID_PLATFORM ?= 8

# FIXME: generate based on BUILD_OS-BUILD_ARCH
ANDROID_BUILD_OS = ${BUILD_OS}
ANDROID_BUILD_ARCH = x86
ANDROID_BUILD_SYS = ${ANDROID_BUILD_OS}-${ANDROID_BUILD_ARCH}

SRC_URI = "http://dl.google.com/android/ndk/android-ndk-r${PV}-${ANDROID_BUILD_SYS}.zip;name=ndk"
S = "${WORKDIR}/android-ndk-r${PV}"

SRC_URI[ndk.md5sum] = "0892b0637d45d145e045cc68e163dee3"
SRC_URI[ndk.sha256sum] = "b906b9903f02bdb5b08ecc11d7bd8dc3be94c057f2705320e5e7fbe7796d3085"
