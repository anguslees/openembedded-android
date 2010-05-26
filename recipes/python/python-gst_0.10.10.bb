DESCRIPTION = "Python Gstreamer bindings"
SECTION = "devel/python"
LICENSE = "LGPL"
DEPENDS = "gstreamer gst-plugins-base python-pygobject"
PR = "ml4"

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
FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/gst-0.10/gst/.debug/"
FILES_${PN}-examples = "${datadir}/gst-python/0.10/examples"

SRC_URI[md5sum] = "6183d61e434b5d34f232248a35571627"
SRC_URI[sha256sum] = "c875753a6cccd40a79f5824ea5ec51ee4615a4f22103e4adfc6a0221bf615d8e"
