DESCRIPTION = "Qanava is a graph library based on the Qt4 Arthur framework."
SECTION = "x11/libs"
HOMEPAGE = "http://www.libqanava.org/"
# LGPL after 0.0.7
LICENSE = "GPL"
PR = "r0"

SRC_URI = "http://www.libqanava.org/dl/qanava-${PV}.tar.gz"
S = "${WORKDIR}/qanava-${PV}"

inherit qt4x11

PARALLEL_MAKE = ""

EXTRA_QMAKEVARS_POST = "CONFIG+=thread CONFIG-=staticlib CONFIG-=debug"

do_configure_prepend() {
	find . -name "Makefile"|xargs rm -f
}

LIBS = "can la utl"
BINS = "basic layout logo timetree"

do_stage() {
	for lib in ${LIBS}
	do
		oe_libinstall -so -C build libqanava_$lib ${D}${libdir}
	done
}

do_install() {
	for lib in ${LIBS}
	do
		oe_libinstall -so -C build libqanava_$lib ${D}${libdir}
	done

	install -d ${D}${bindir}
	for bin in ${BINS}
	do
		install -m 0755 tests/$bin/test_$bin ${D}${bindir}
	done
}

PACKAGES = "${PN}-dbg libqanava-can libqanava-la libqanava-utl qanava-examples ${PN}"
FILES_libqanava-can = "${libdir}/libqanava_can*.so*"
FILES_libqanava-la = "${libdir}/libqanava_la*.so*"
FILES_libqanava-utl = "${libdir}/libqanava_utl*.so*"
FILES_qanava-examples = "${bindir}"


SRC_URI[md5sum] = "ff9b1bef0be97e81691697fa670461fc"
SRC_URI[sha256sum] = "a779934f67b8de54ab075834b7b1024b13f832a4299699a6fcf7c5d5cfc74972"
