DESCRIPTION = "C library for Android"
LICENSE = "BSD"
SECTION = "libs"
PRIORITY = "required"

PROVIDES += "virtual/libc virtual/${TARGET_PREFIX}libc-for-gcc"

DEPENDS = "\
    virtual/${TARGET_PREFIX}binutils \
    virtual/${TARGET_PREFIX}gcc-intermediate \
    linux-libc-headers \
    "
RDEPENDS_${PN}-dev = "linux-libc-headers-dev"

INHIBIT_DEFAULT_DEPS = "1"

PACKAGES += "bionic libstdc++ bionic-thread-db"
LEAD_SONAME = "libc.so"

FILES_${PN} = "${sysconfdir} ${libexecdir} \
    ${base_libdir}/libc*.so* ${base_libdir}/libdl*.so* \
    ${base_libdir}/libm*.so* ${base_bindir}/linker"

FILES_bionic-thread-db = "${base_libdir}/libthread_db*"
RPROVIDES_bionic-dev += "libc-dev virtual-libc-dev"

SRC_URI = "\
    git://android.git.kernel.org/platform/bionic.git;branch=master;protocol=git \
    file://bionic-fixes.patch;patch=1 \
    "
S = "${WORKDIR}/git"
