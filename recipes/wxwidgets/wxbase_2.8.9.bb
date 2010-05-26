require wxbase.inc

DEFAULT_PREFERENCE = "-1"

PR = "r0"

SRC_URI += "file://Makefile.in.patch"

EXTRA_OECONF = " --disable-gui \
                 --enable-largefile \
                 --enable-gpe \
                 --without-subdirs"

LEAD_SONAME = "libwx_base-2.8.so"

do_stage() {
       install -d ${STAGING_INCDIR}/wx-2.8/wx
       cp -pR include/wx ${STAGING_INCDIR}/wx-2.8
       cp -pR lib/libwx* ${STAGING_LIBDIR}
       cp -pR lib/wx     ${STAGING_LIBDIR}
       cp -pR wxwin.m4                           ${STAGING_DATADIR}/aclocal
       ln -sf ${STAGING_LIBDIR}/wx/config/${TARGET_PREFIX}base-ansi-release-2.8 ${STAGING_BINDIR_CROSS}/wx-config
       sed -e s,'wxconfdir=".*"','wxconfigdir="${STAGING_LIBDIR}/wx/config"', \
           -e s,'bindir=".*"','bindir="${STAGING_BINDIR}"', \
           -e s,'libdir=".*"','libdir="${STAGING_LIBDIR}"', \
           -e s,'includedir=".*"','includedir="${STAGING_INCDIR}"', \
           -i ${STAGING_LIBDIR}/wx/config/${TARGET_PREFIX}base-ansi-release-2.8
}

FILES_${PN} += "${libdir}/wx/config"
FILES_${PN}-dev += "${libdir}/wx/include"

do_install() {
       oe_runmake 'DESTDIR=${D}' install
       ln -sf ${libdir}/wx/config/${TARGET_PREFIX}base-ansi-release-2.8 ${D}${bindir}/wx-config
}

SRC_URI[md5sum] = "2f78233829bb8979ca31f86b6de87ee9"
SRC_URI[sha256sum] = "5bad521a93021ee7af10127b8c9b4235c16274ad06943585022bfc23a6f4b005"
