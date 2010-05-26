DESCRIPTION = "Python Gstreamer bindings"
SECTION = "devel/python"
LICENSE = "LGPL"
DEPENDS = "gstreamer gst-plugins-base python-pygobject"

PR = "r1"

SRC_URI = "\
  http://gstreamer.freedesktop.org/src/gst-python/gst-python-${PV}.tar.bz2 \
  file://python-path.patch \
  file://import-gobject-instead-of-pygtk.patch \
"
S = "${WORKDIR}/gst-python-${PV}"

inherit autotools pkgconfig  distutils-base

require fix-path.inc

EXTRA_OECONF += "--with-python-includes=${STAGING_INCDIR}/../"

do_configure_prepend() {
    #install all except libtool
    rm -f ${S}/common/m4/libtool.m4
    rm -f ${S}/common/m4/lt*.m4
    install -d ${S}/m4
    install -m 0644 ${S}/common/m4/*.m4 ${S}/m4/
}

do_stage() {
	autotools_stage_all
}

PACKAGES =+ "${PN}-examples"

FILES_${PN} += "${datadir}/gst-python"
FILES_${PN}-dev += "\
  ${datadir}/gst-python/0.10/defs \
  ${libdir}/${PYTHON_DIR}/site-packages/gst-0.10/gst/*.la \
"
FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/gst-0.10/gst/.debug/ ${libdir}/gstreamer-0.10/.debug/"
FILES_${PN}-examples = "${datadir}/gst-python/0.10/examples"

SRC_URI[md5sum] = "3998753de6500ee8c18ce1456df43df6"
SRC_URI[sha256sum] = "70cef839b429dcea0cae342d24bc2e0e5deae01e902a8858e2c7dd76a724cde9"
