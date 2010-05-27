DESCRIPTION = "C library for Android"
LICENSE = "BSD"  # ?
SECTION = "libs"
PRIORITY = "required"

PROVIDES += "virtual/libc virtual/${TARGET_PREFIX}libc-for-gcc virtual/${TARGET_PREFIX}libc-initial"

DEPENDS = "\
    virtual/${TARGET_PREFIX}binutils \
    virtual/${TARGET_PREFIX}gcc-intermediate \
    linux-libc-headers \
    "
RDEPENDS_${PN}-dev = "linux-libc-headers-dev"

INHIBIT_DEFAULT_DEPS = "1"

PACKAGES += "libstdc++ ${PN}-thread-db"
LEAD_SONAME = "libc.so"

FILES_${PN} = "${sysconfdir} ${libexecdir} \
    ${base_libdir}/libc*.so* ${base_libdir}/libdl*.so* \
    ${base_libdir}/libm*.so* ${base_bindir}/linker"
RPROVIDES_${PN} += "libc libstdc++"

FILES_${PN}-thread-db = "${base_libdir}/libthread_db*"
RPROVIDES_${PN}-dev += "libc-dev virtual-libc-dev libstdc++-dev"

GIT_TAG = "android-${PV}"

SRC_URI = "\
    git://android.git.kernel.org/platform/bionic.git;tag=${GIT_TAG};protocol=git \
    file://bionic-fixes.patch \
    "
S = "${WORKDIR}/git"

do_compile() {
}
