PR = "${INC_PR}.1"

require gcc-${PV}.inc
require gcc-cross-sdk.inc

SRC_URI += 'file://sdk-libstdc++-includes.patch'

do_compile_prepend () {
	mkdir -p gcc
	ln -s ${CROSS_DIR}/bin/${TARGET_PREFIX}as gcc/as 
	ln -s ${CROSS_DIR}/bin/${TARGET_PREFIX}ld gcc/ld
}

