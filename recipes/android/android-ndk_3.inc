DESCRIPTION ?= "Android NDK"
SECTION ?= "devel"
HOMEPAGE = "http://developer.android.com/sdk/index.html"

ANDROID_PLATFORM ?= 5

# FIXME: generate based on BUILD_OS-BUILD_ARCH
ANDROID_BUILD_OS = ${BUILD_OS}
ANDROID_BUILD_ARCH = x86
ANDROID_BUILD_SYS = ${ANDROID_BUILD_OS}-${ANDROID_BUILD_ARCH}

SRC_URI = "http://dl.google.com/android/ndk/android-ndk-r${PV}-${ANDROID_BUILD_SYS}.zip;name=ndk"
S = "${WORKDIR}/android-ndk-r${PV}"

SRC_URI[ndk.md5sum] = "f3b1700a195aae3a6e9b5637e5c49359"
SRC_URI[ndk.sha256sum] = "906b1a911be5f15bfe82901d600e6437fa363f7e24b3cdd9f291f4e7ec2aa396"
