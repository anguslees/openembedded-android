DESCRIPTION = " Webkit browser engine, EFL edition"
LICENSE = "GPL"
DEPENDS = "icu flex gst-plugins-base gstreamer jpeg libpng libxml2 pango \
           libsoup-2.4 eina ecore evas edje cairo fontconfig freetype curl \
           sqlite3 libxslt gperf-native libxt"

SRCREV = "3a5ee77664c898ed51a2b2d5759822f8c0a06472"
PV = "1.1.11+gitr${SRCPV}"
PR = "r5"

SRC_URI = "git://gitorious.org/webkit-efl/webkit-efl.git;protocol=git;branch=master \
           file://fix-build-with-newer-evas.patch \
           file://fix-build-with-newer-ecore.patch"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig

EXTRA_OECONF = "--disable-video --host=${TARGET_SYS} --with-port=efl --enable-web-workers=no"

PACKAGES =+ "${PN}-webinspector"

FILES_${PN} += "${datadir}/webkit-1.0/theme/default.edj"
FILES_${PN}-webinspector = "${datadir}/webkit-1.0/webinspector/"
