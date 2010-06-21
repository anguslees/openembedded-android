DESCRIPTION = "Super Nintendo Emulator based on SDL, QtE Palmtop Environments Edition"
SECTION = "opie/games"
PRIORITY = "optional"
DEPENDS = "libsdl-qpe"
LICENSE = "snes9x"
PR = "r4"

SRC_URI = "http://www.vanille.de/mirror/snes9x-sdl-${PV}.tar.bz2 \
	   file://compile.patch"
S = "${WORKDIR}/snes9x-sdl-${PV}"

FILESPATHPKG .= ":snes9x-sdl"

inherit qmake_base

QT_LIBRARY = '${@base_conditional("PALMTOP_USE_MULTITHREADED_QT", "yes", "qte-mt", "qte",d)}'

do_compile() {
	oe_runmake CC="${CC}" CCC="${CXX}" \
	           INCLUDES="-I${STAGING_INCDIR} `sdl-config --cflags`" \
	           LDLIBS="`sdl-config --libs` -L${OE_QMAKE_LIBDIR_QT} -Wl,-rpath-link,${STAGING_LIBDIR} -lqpe -l${QT_LIBRARY}"
}

do_install() {
	install -d ${D}${palmtopdir}/bin/
	install -m 0755 snes9x ${D}${palmtopdir}/bin/snes9x
}

FILES_${PN} = "${palmtopdir}/bin/snes9x"

SRC_URI[md5sum] = "a7836a9b6eaae433079c1c9d19f2635a"
SRC_URI[sha256sum] = "489bb2f9fb69922e9befc27ae1b8d19d31c83c586f55b7ecc503cb4f5e767da4"
