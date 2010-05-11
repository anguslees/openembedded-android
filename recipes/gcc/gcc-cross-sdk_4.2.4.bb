PR = "${INC_PR}.2"

inherit sdk

require gcc-${PV}.inc
require gcc-cross-sdk.inc

EXTRA_OECONF += "--disable-libunwind-exceptions --enable-libssp \
		--enable-libgomp --disable-libmudflap \
		--with-mpfr=${STAGING_DIR_NATIVE}${prefix_native}"

