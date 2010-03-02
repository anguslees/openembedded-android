DESCRIPTION = "Android bionic C library (prebuilt)"
SECTION = "libs"
LICENSE = "BSD"  # ?
PV = 1.6

require android-ndk_${PV}.inc

PN = "android-ndk-bionic"

PACKAGES += "${PN}-thread-db"

SRC_URI += "file://header-fixups.patch;patch=1"

INHIBIT_DEFAULT_DEPS = "1"
LEAD_SONAME = "libc.so"

PROVIDES += "linux-libc-headers virtual/libc virtual/arm-android-eabi-libc-for-gcc virtual/arm-android-eabi-libc-initial"
RPROVIDES_${PN} += "bionic"
RPROVIDES_${PN}-dev += "bionic-dev"
RPROVIDES_${PN}-thread-db += "bionic-thread-db"

RPROVIDES_${PN} += "libc libstdc++ zlib bionic libgcc"
RPROVIDES_${PN}-dev += "libc-dev virtual-libc-dev libstdc++-dev zlib-dev \
    linux-libc-headers linux-libc-headers-dev"

FILES_${PN} = "${libdir}/lib*.so ${base_libdir}/lib*.so"
FILES_${PN}-dev = "${includedir} ${base_libdir}/crt*.o"
FILES_${PN}-thread-db = "${base_libdir}/libthread_db*"

#RDEPENDS_${PN}-dev += "linux-libc-headers-dev"

S_ARCH_DIR = "${S}/build/platforms/android-${ANDROID_PLATFORM}/arch-${TARGET_ARCH}"

do_compile() {
	:
}

do_install() {
	install -d ${D}${includedir} ${D}${base_libdir}
	cp -R -pf "${S_ARCH_DIR}"/usr/include/* ${D}${includedir}
	cp -R -pf "${S_ARCH_DIR}"/usr/lib/* ${D}${base_libdir}
}

do_stage() {
	install -d ${STAGING_INCDIR} ${STAGING_LIBDIR}
	cp -R -pf "${S_ARCH_DIR}"/usr/include/* ${STAGING_INCDIR}
	cp -R -pf "${S_ARCH_DIR}"/usr/lib/* ${STAGING_LIBDIR}
}
