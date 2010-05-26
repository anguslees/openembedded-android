DESCRIPTION = "Driver for the Ati W100 found on the Sharp Zaurus C[87]x0"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "CLOSED"
PR = "r4"

SRC_URI = "http://mirror1.pdaxrom.org/source/src/AtiCore-1.0.1.tar.bz2 \
		file://fixstretchblit.patch \
		file://aticore-2.6.patch \
		file://make381.patch"
S = "${WORKDIR}/AtiCore-1.0.1"

EXTRA_OEMAKE="CC='${CC}' AS='${AS}' AR='${AR}' LD='${LD}' FPU='${TARGET_FPU}'"

PARALLEL_MAKE = ""
COMPATIBLE_MACHINE = 'c7x0'

do_install() {
	install -d ${D}${bindir}
	install -m 0755 testcore ${D}${bindir}/testcore
	install -m 0755 atitest ${D}${bindir}/atitest

	install -d ${D}${libdir}
	install -m 0755 libaticore.so.1.0.1 ${D}${libdir}/libaticore.so.1.0.1

	install -d ${D}${includedir}
	install -m 0644 aticore.h ${D}${includedir}/aticore.h

	cd ${D}${libdir}/
	ln -s libaticore.so.1.0.1 libaticore.so.1
        ln -s libaticore.so.1.0.1 libaticore.so.1.0
        ln -s libaticore.so.1.0.1 libaticore.so
}

# Currently incompatible with the way sharp-aticore.bb stages.
do_stage() {
	install -m 0644 aticore.h ${STAGING_INCDIR}
	oe_libinstall -so libaticore ${STAGING_LIBDIR}
}

PACKAGES =+ "sharp-aticore-oss-examples"
FILES_sharp-aticore-oss-examples = "${bindir}"


SRC_URI[md5sum] = "b3d00a755e3be599b714f1f552db2752"
SRC_URI[sha256sum] = "8f9124bd76c378ae2a25b6b421ebb8f0a88069da697ac6c96943571d93af8b15"
