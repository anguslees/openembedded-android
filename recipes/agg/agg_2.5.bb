DESCRIPTION = "Anti Grain Geometry - A High Quality Rendering Engine for C++"
AUTHOR = "mcseem@antigrain.com"
HOMEPAGE = "http://www.antigrain.com"
SECTION = "libs"
LICENSE = "AGG License"
DEPENDS = "virtual/libx11 virtual/libsdl freetype"

PR = "r2"

SRC_URI = "http://www.antigrain.com/${P}.tar.gz;name=archive"
S = "${WORKDIR}/${P}"

inherit autotools pkgconfig

export SDL_CONFIG=${STAGING_BINDIR_CROSS}/sdl-config

CFLAGS += " -I${STAGING_INCDIR} "

PACKAGES =+ "${PN}-sdl ${PN}-x11"

FILES_${PN}-sdl = "${libdir}/libaggplatformsdl.so.*"
FILES_${PN}-x11 += "${libdir}/libaggplatformX11.so.*"
FILES_${PN} = "${libdir}/libagg.so.* \
               ${libdir}/libaggfontfreetype.so.*"

LEAD_SONAME = "libagg.so"

EXTRA_OECONF = "--x-includes=${STAGING_INCDIR}/usr/include --x-libraries=${STAGING_INCDIR}/usr/lib"

do_stage() {
    oe_libinstall -a -so libagg ${STAGING_LIBDIR}
    oe_libinstall -a -so libaggfontfreetype ${STAGING_LIBDIR}
    oe_libinstall -a -so libaggplatformX11 ${STAGING_LIBDIR}
    oe_libinstall -a -so libaggplatformsdl ${STAGING_LIBDIR}
    install -m 0644 libagg.m4 ${STAGING_DATADIR}/aclocal/
    install -d ${STAGING_INCDIR}/agg2

    for f in font_freetype font_win32_tt gpc
    do
            install -m 0644 ${S}/$f/*.h ${STAGING_INCDIR}/agg2/
    done

    cd include
    headers=`find . -name "*.h"`
    for f in $headers
    do
            install -D -m 0644 $f ${STAGING_INCDIR}/agg2/$f
    done
}

SRC_URI[archive.md5sum] = "0229a488bc47be10a2fee6cf0b2febd6"
SRC_URI[archive.sha256sum] = "ab1edc54cc32ba51a62ff120d501eecd55fceeedf869b9354e7e13812289911f"
