DESCRIPTION = "Android bionic C library (prebuilt)"
SECTION = "libs"
LICENSE = "BSD"  # ?
PV = 4

require android-ndk_${PV}.inc

PN = "android-ndk-bionic"

PACKAGES_prepend = "${PN}-thread-db "

SRC_URI += "file://header-fixups-${ANDROID_PLATFORM}.patch"

INHIBIT_DEFAULT_DEPS = "1"
LEAD_SONAME = "libc.so"

PROVIDES += "linux-libc-headers virtual/libc virtual/${TARGET_PREFIX}libc-for-gcc virtual/${TARGET_PREFIX}libc-initial"
PROVIDES += "virtual/egl"
RPROVIDES_${PN} += "bionic"
RPROVIDES_${PN}-dev += "bionic-dev"
RPROVIDES_${PN}-thread-db += "bionic-thread-db"

RPROVIDES_${PN} += "libc libstdc++ zlib bionic libgcc"
RPROVIDES_${PN}-dev += "libc-dev virtual-libc-dev libstdc++-dev zlib-dev \
    linux-libc-headers linux-libc-headers-dev"

FILES_${PN} = "${libdir}/lib*.so ${base_libdir}/lib*.so"
FILES_${PN}-dev = "${includedir} ${includedir}/c++ ${base_libdir}/crt*.o"
FILES_${PN}-thread-db = "${libdir}/libthread_db* ${base_libdir}/libthread_db*"

#RDEPENDS_${PN}-dev += "linux-libc-headers-dev"

S_ARCH_DIR = "${S}/build/platforms/android-${ANDROID_PLATFORM}/arch-${TARGET_ARCH}"

do_compile() {
	:
}

do_install() {
	install -d ${D}${includedir} ${D}${base_libdir}
	cp -R -pf ${S_ARCH_DIR}/usr/include/* ${D}${includedir}
	cp -R -pf ${S_ARCH_DIR}/usr/lib/* ${D}${base_libdir}
	chmod a+rX -R ${D}${includedir} ${D}${base_libdir}

	# meta-toolchain:do_populate_sdk wants include/c++ to exist.
	install -d ${D}${includedir}/c++
	touch ${D}${includedir}/c++/.dummyfile
}
