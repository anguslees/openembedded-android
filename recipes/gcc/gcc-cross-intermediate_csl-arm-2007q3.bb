require gcc-cross_${PV}.bb
require gcc-cross-intermediate.inc

S = "${WORKDIR}/gcc-4.2"

# Hack till we fix *libc properly
do_install_append() {
	ln -sf ${CROSS_DIR}/lib/gcc/${TARGET_SYS}/${BINV}/include-fixed/* ${CROSS_DIR}/lib/gcc/${TARGET_SYS}/${BINV}/include/
}
