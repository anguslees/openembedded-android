PR = "r0"
require gcc-${PV}.inc
require gcc-cross4.inc

DEPENDS += "libmpc-native"

SRC_URI_append_fail-fast = " file://zecke-no-host-includes.patch "

EXTRA_OECONF += " --disable-libunwind-exceptions --with-mpfr=${STAGING_DIR_NATIVE}${prefix_native} --with-system-zlib"

ARCH_FLAGS_FOR_TARGET += "-isystem${STAGING_DIR_TARGET}${target_includedir}"
